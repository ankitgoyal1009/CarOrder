package com.ankit.carorder.dbmanager.datasource;

import java.util.ArrayList;
import java.util.List;

import com.ankit.carorder.dbmanager.CarHelper;
import com.ankit.carorder.dbmanager.model.Manufacturer;
import com.ankit.carorder.dbmanager.model.Order;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

public class OrderDatasource extends CarDatasource {

	private String[] allModelColumns = { CarHelper.COLUMN_ID,
			CarHelper.COLUMN_ORDER_MODEL, CarHelper.COLUMN_ORDER_COLOR,
			CarHelper.COLUMN_ORDER_STATUS, CarHelper.COLUMN_ORDER_TIME };

	public OrderDatasource(Context context) {
		super(context);

	}

	public List<Order> getAllOrders() {

		List<Order> orders = new ArrayList<Order>();
		Cursor cursor = database.query(CarHelper.TABLE_ORDER, allModelColumns,
				null, null, null, null, null);
		cursor.moveToFirst();
		while (!cursor.isAfterLast()) {
			Order order = new Order();
			order.setId(cursor.getLong(cursor
					.getColumnIndex(CarHelper.COLUMN_ID)));
			order.setModelName(cursor.getString(cursor
					.getColumnIndex(CarHelper.COLUMN_ORDER_MODEL)));
			order.setColor(cursor.getString(cursor
					.getColumnIndex(CarHelper.COLUMN_ORDER_COLOR)));
			order.setStatus(cursor.getString(cursor
					.getColumnIndex(CarHelper.COLUMN_ORDER_STATUS)));
			order.setTime(cursor.getString(cursor
					.getColumnIndex(CarHelper.COLUMN_ORDER_TIME)));
			orders.add(order);
			cursor.moveToNext();
		}
		cursor.close();
		return orders;
	}

	public List<Order> getAllOrdersByStatus(int status) {
		List<Order> orders = new ArrayList<Order>();
		Cursor cursor = database.query(CarHelper.TABLE_ORDER, allModelColumns,
				CarHelper.COLUMN_ORDER_STATUS+" = "+status, null, null, null, null);
		cursor.moveToFirst();
		while (!cursor.isAfterLast()) {
			Order order = new Order();
			order.setId(cursor.getLong(cursor
					.getColumnIndex(CarHelper.COLUMN_ID)));
			order.setModelName(cursor.getString(cursor
					.getColumnIndex(CarHelper.COLUMN_ORDER_MODEL)));
			order.setColor(cursor.getString(cursor
					.getColumnIndex(CarHelper.COLUMN_ORDER_COLOR)));
			order.setStatus(cursor.getString(cursor
					.getColumnIndex(CarHelper.COLUMN_ORDER_STATUS)));
			order.setTime(cursor.getString(cursor
					.getColumnIndex(CarHelper.COLUMN_ORDER_TIME)));
			orders.add(order);
			cursor.moveToNext();
		}
		cursor.close();
		return orders;
	}

	public void isOrderPlaces(int orderId) {
	}

	public void createOrder(Order order) {
		ContentValues values = new ContentValues();
		
		values.put(CarHelper.COLUMN_ORDER_COLOR, order.getColor());
		values.put(CarHelper.COLUMN_ORDER_MODEL, order.getModelName());
		values.put(CarHelper.COLUMN_ORDER_STATUS, order.getStatus());
		values.put(CarHelper.COLUMN_ORDER_TIME, order.getTime());
		database.insert(CarHelper.TABLE_ORDER, null, values);
		
	}

	@Override
	public void insertData() {
		
	}
	public int updateOrderStaus(Long orderId, boolean status) {
		ContentValues values = new ContentValues();
		values.put(CarHelper.COLUMN_ORDER_STATUS, status);
		return database.update(CarHelper.TABLE_ORDER, values,CarHelper.COLUMN_ID+" = "+orderId,null);
		
	}
	public int updateOrderStaus(Long orderId, boolean status, String delivery_date) {
		ContentValues values = new ContentValues();
		values.put(CarHelper.COLUMN_ORDER_DELIVERY_TIME, delivery_date);
		values.put(CarHelper.COLUMN_ORDER_STATUS, status);
		return database.update(CarHelper.TABLE_ORDER, values,CarHelper.COLUMN_ID+" = "+orderId,null);
		
	}
	
}
