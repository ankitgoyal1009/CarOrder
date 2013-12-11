package com.ankit.carorder;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import com.ankit.carorder.commons.Constants;
import com.ankit.carorder.commons.Utills;
import com.ankit.carorder.dbmanager.datasource.CarColorDatasource;
import com.ankit.carorder.dbmanager.datasource.CarModelDatasource;
import com.ankit.carorder.dbmanager.datasource.ManufacturerDatasource;
import com.ankit.carorder.dbmanager.datasource.OrderDatasource;
import com.ankit.carorder.dbmanager.model.CarColor;
import com.ankit.carorder.dbmanager.model.CarModel;
import com.ankit.carorder.dbmanager.model.Manufacturer;
import com.ankit.carorder.dbmanager.model.Order;

public class NewOrder extends Activity {
	private OrderDatasource orderDatasource;
	ManufacturerDatasource manuDatasource;
	CarModelDatasource modelDatasource;
	CarColorDatasource colorDatasource;
	private ArrayList<Manufacturer> manuValues;
	private ArrayList<CarModel> modelValues;
	private Spinner modelSpinner;
	private Spinner manuSpinner;
	private ArrayList<CarColor> colorValues;
	private Spinner colorSpinner;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_order);
		manuDatasource = new ManufacturerDatasource(this);
		modelDatasource = new CarModelDatasource(this);
		colorDatasource = new CarColorDatasource(this);
		orderDatasource = new OrderDatasource(this);
		initializeManufacturerList();
		initializeColorList();
		manuSpinner = (Spinner) findViewById(R.id.spinner_manufacturer);
		modelSpinner = (Spinner) findViewById(R.id.spinner_model);
		colorSpinner = (Spinner) findViewById(R.id.spinner_color);
		// Create an ArrayAdapter using the array list and a default spinner
		// layout
		ArrayAdapter<Manufacturer> manuAdapter = new ArrayAdapter<Manufacturer>(
				this, android.R.layout.simple_list_item_1, manuValues);
		ArrayAdapter<CarColor> colorAdapter = new ArrayAdapter<CarColor>(this,
				android.R.layout.simple_list_item_1, colorValues);

		// Specify the layout to use when the list of choices appears
		manuAdapter
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		colorAdapter
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		// Apply the adapter to the spinner
		manuSpinner.setAdapter(manuAdapter);
		colorSpinner.setAdapter(colorAdapter);

		// Apply the on item selected listner to the spinner
		manuSpinner.setOnItemSelectedListener(new SpinnerItemListner());
		Button submit = (Button) findViewById(R.id.order_submit);
		submit.setOnClickListener(new OrderClickListner());
	}

	private void initializeColorList() {
		colorDatasource.open();
		colorValues = (ArrayList<CarColor>) colorDatasource.getAllColor();
		colorDatasource.close();
	}

	public void initializeModelList(String selectedManuId) {
		modelDatasource.open();
		modelValues = (ArrayList<CarModel>) modelDatasource
				.getAllCarFromManufacturer(selectedManuId);
		modelDatasource.close();
	}

	void initializeManufacturerList() {
		manuDatasource.open();
		manuValues = (ArrayList<Manufacturer>) manuDatasource
				.getAllManufecture();
		manuDatasource.close();
	}

	void createOrder() {
		Order order = new Order();
		CarColor color = (CarColor) colorSpinner.getSelectedItem();
		CarModel model = (CarModel) modelSpinner.getSelectedItem();
		order.setColor(color.getColorName());
		order.setModelName(model.getModelName());
		order.setStatus(Integer.toString(Constants.PENDING_ORDER));
		order.setTime(Utills.getSystemDate());
		orderDatasource.open();
		orderDatasource.createOrder(order);
		orderDatasource.close();
		this.finish();
	}

	/*String getSystemDate() {
		Calendar c = Calendar.getInstance();
		System.out.println("Current time => " + c.getTime());

		SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
		return df.format(c.getTime());
	}*/

	class SpinnerItemListner implements OnItemSelectedListener {

		public void onItemSelected(AdapterView<?> adapterView, View view,
				int position, long id) {

			String selectedManuId = manuValues.get(position)
					.getManufacturerName();
			initializeModelList(selectedManuId);
			ArrayAdapter<CarModel> modelAdapter = new ArrayAdapter<CarModel>(
					NewOrder.this, android.R.layout.simple_list_item_1,
					modelValues);
			modelAdapter
					.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
			// Apply the adapter to the spinner
			modelSpinner.setAdapter(modelAdapter);
			modelSpinner.invalidate();
			modelAdapter.notifyDataSetChanged();

		}

		@Override
		public void onNothingSelected(AdapterView<?> arg0) {

		}

	}

	class OrderClickListner implements OnClickListener {

		@Override
		public void onClick(View v) {
			createOrder();
		}

	}
}
