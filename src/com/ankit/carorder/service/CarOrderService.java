package com.ankit.carorder.service;

import java.util.List;
import java.util.Random;

import com.ankit.carorder.commons.Constants;
import com.ankit.carorder.dbmanager.datasource.OrderDatasource;
import com.ankit.carorder.dbmanager.model.Order;

import android.app.IntentService;
import android.content.Intent;
import android.util.Log;

public class CarOrderService extends IntentService {
	// int orderNo;
	boolean isSuccess;
	private OrderDatasource orderDatasource;
	private List<Order> pendingOrderValues;

	public CarOrderService() {
		super("CarOrderService");

	}

	public CarOrderService(String name) {
		super(name);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onCreate() {
		Log.d("CarService", "onCreate:start");
		orderDatasource = new OrderDatasource(this);
		super.onCreate();
		Log.d("CarService", "onCreate:end");
	}

	@Override
	protected void onHandleIntent(Intent intent) {
		Log.d("CarService", "onHandleIntent:start");
			int min = 65;
			int max = 80;
			fetchData();
			if(pendingOrderValues.size()<=0)
				return;
			for(int i =0; i<pendingOrderValues.size();i++){
			Random r = new Random();
			int i1 = r.nextInt(max - min + 1) + min;
			if (i1 % 2 == 0) {
				// success
				isSuccess = true;
			} else {
				// failed
				isSuccess = false;
			}
			publishResults(pendingOrderValues.get(i).getId(), isSuccess);
		}
		Log.d("CarService", "onHandleIntent:end");
	}

	private void publishResults(Long orderNo, boolean isSuccess) {
		Log.d("CarService", "publishResults:start");
		Intent intent = new Intent(Constants.SERVICE_NOTIFICATION);
		intent.putExtra(Constants.RESULT, isSuccess);
		intent.putExtra(Constants.intentOrderNo, orderNo);
		sendBroadcast(intent);
		Log.d("CarService", "publishResults:end");
	}

	void fetchData() {
		orderDatasource.open();
		pendingOrderValues = orderDatasource
				.getAllOrdersByStatus(Constants.PENDING_ORDER);
		orderDatasource.close();

	}
}
