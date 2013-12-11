package com.ankit.carorder;

import java.util.List;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.Toast;

import com.ankit.carorder.adapter.OrderAdapter;
import com.ankit.carorder.commons.Constants;
import com.ankit.carorder.dbmanager.datasource.CarColorDatasource;
import com.ankit.carorder.dbmanager.datasource.CarModelDatasource;
import com.ankit.carorder.dbmanager.datasource.ManufacturerDatasource;
import com.ankit.carorder.dbmanager.datasource.OrderDatasource;
import com.ankit.carorder.dbmanager.model.Order;
import com.ankit.carorder.service.CarOrderService;

public class MainActivity extends Activity {
	private static final String CAR_PREFS = "car_preference";
	private static final String PREFS_FIRST_TIME = "first_time";

	private OrderAdapter pendingOrderAdapter;
	private OrderAdapter placedOrderAdapter;
	private CarColorDatasource colorDatasource;
	private OrderDatasource orderDatasource;
	private ManufacturerDatasource manuDatasource;
	private CarModelDatasource modelDatasource;
	// private ArrayAdapter<Order> placedAdapter;
	private List<Order> placedOrderValues;
	private List<Order> pendingOrderValues;
	private ListView pendingList;
	private ListView placedList;
	private BroadcastReceiver receiver = new BroadcastReceiver() {

		@Override
		public void onReceive(Context context, Intent intent) {
			Log.d("CarService", "onReceive:start");
			if (intent != null && intent.hasExtra(Constants.RESULT)) {
				updateOrder(
						intent.getExtras().getLong(Constants.intentOrderNo),
						intent.getExtras().getBoolean(Constants.RESULT));
				fetchData();
				refreshOrderLists();
				if (intent.getExtras().getBoolean(Constants.RESULT)) {
					Toast.makeText(MainActivity.this,
							"Order has been placed successfully.",
							Toast.LENGTH_SHORT).show();
				} else {
					Toast.makeText(MainActivity.this,
							"Order has failed. Please refresh.",
							Toast.LENGTH_SHORT).show();
				}
			}
			Log.d("CarService", "onReceive:end");
		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		Log.d("Click", "onCreate:start");
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		colorDatasource = new CarColorDatasource(this);
		orderDatasource = new OrderDatasource(this);
		manuDatasource = new ManufacturerDatasource(this);
		modelDatasource = new CarModelDatasource(this);
		loadInitialData();
		// refreshOrderLists();
		fetchData();
		pendingOrderAdapter = new OrderAdapter(pendingOrderValues,
				Constants.PENDING_ORDER, this);
		placedOrderAdapter = new OrderAdapter(placedOrderValues,
				Constants.PLACED_ORDER, this);
		pendingList = (ListView) findViewById(R.id.pending_list);
		placedList = (ListView) findViewById(R.id.placed_list);
		pendingList.setAdapter(pendingOrderAdapter);
		placedList.setAdapter(placedOrderAdapter);
		Log.d("Click", "onCreate:end");
	}

	@Override
	protected void onResume() {
		Log.d("Click", "Onresume:start");
		fetchData();
		refreshOrderLists();

		super.onResume();
		registerReceiver(receiver, new IntentFilter(
				Constants.SERVICE_NOTIFICATION));
		startService();
		Log.d("Click", "Onresume:end");
	}

	@Override
	protected void onPause() {
		super.onPause();
		unregisterReceiver(receiver);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);

		return true;
	}

	// @Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle item selection
		switch (item.getItemId()) {
		case R.id.action_order:
			Intent newOrderIntent = new Intent(this, NewOrder.class);
			startActivity(newOrderIntent);
			return true;
		case R.id.action_refresh:
			startService();
			return true;
		default:
			return super.onOptionsItemSelected(item);

		}

	}

	void fetchData() {
		orderDatasource.open();
		pendingOrderValues = orderDatasource
				.getAllOrdersByStatus(Constants.PENDING_ORDER);
		placedOrderValues = orderDatasource
				.getAllOrdersByStatus(Constants.PLACED_ORDER);
		orderDatasource.close();

	}

	void refreshOrderLists() {
		Log.d("Click", "refreshOrderLists:start");
		if (pendingList != null) {
			pendingOrderAdapter.refreshData(pendingOrderValues);
			pendingOrderAdapter.notifyDataSetChanged();
		}
		if (placedList != null) {
			placedOrderAdapter.refreshData(placedOrderValues);
			placedOrderAdapter.notifyDataSetChanged();
		}
		Log.d("Click", "refreshOrderLists:end" + placedOrderValues.size());
	}

	void loadInitialData() {
		SharedPreferences settings = this.getSharedPreferences(CAR_PREFS, 0);
		if (!settings.getBoolean(PREFS_FIRST_TIME, false)) {
			colorDatasource.open();
			colorDatasource.insertData();
			colorDatasource.close();
			manuDatasource.open();
			manuDatasource.insertData();
			manuDatasource.close();

			modelDatasource.open();
			modelDatasource.insertData();
			modelDatasource.close();

			SharedPreferences.Editor editor = settings.edit();
			editor.putBoolean(PREFS_FIRST_TIME, true);
			editor.commit();
		}
	}

	void updateOrder(Long orderId, boolean status) {
		orderDatasource.open();
		orderDatasource.updateOrderStaus(orderId, status);
		orderDatasource.close();
		refreshOrderLists();
	}

	public void startService() {
		Intent intent = new Intent(this, CarOrderService.class);
		startService(intent);
	}
}
