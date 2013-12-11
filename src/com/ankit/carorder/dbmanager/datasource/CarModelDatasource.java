package com.ankit.carorder.dbmanager.datasource;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.ankit.carorder.dbmanager.CarHelper;
import com.ankit.carorder.dbmanager.model.CarModel;

public class CarModelDatasource extends CarDatasource {
	String[] allModelColumns = { CarHelper.COLUMN_ID, CarHelper.COLUMN_MODEL,
			CarHelper.COLUMN_MODEL_MFG };

	public CarModelDatasource(Context context) {
		super(context);
	}

	public List<CarModel> getAllModel() {
		List<CarModel> models = new ArrayList<CarModel>();
		Cursor cursor = database.query(CarHelper.TABLE_MODEL, allModelColumns,
				null, null, null, null, null);
		cursor.moveToFirst();
		while (!cursor.isAfterLast()) {
			CarModel carModel = new CarModel();
			carModel.setId(cursor.getLong(0));
			carModel.setModelName(cursor.getString(1));
			models.add(carModel);
			cursor.moveToNext();
		}
		cursor.close();
		return models;
	}

	public List<CarModel> getAllCarFromManufacturer(String manufacturer) {
		List<CarModel> models = new ArrayList<CarModel>();
		Cursor cursor = database.query(CarHelper.TABLE_MODEL, allModelColumns,
				CarHelper.COLUMN_MODEL_MFG + "='" + manufacturer + "'"
						, null, null, null, null);
		cursor.moveToFirst();
		while (!cursor.isAfterLast()) {
			CarModel carModel = new CarModel();
			carModel.setId(cursor.getLong(0));
			carModel.setModelName(cursor.getString(1));
			models.add(carModel);
			cursor.moveToNext();
		}
		cursor.close();
		return models;
	}
	@Override
	public void insertData() {
		ContentValues values = new ContentValues();
		ContentValues values1 = new ContentValues();
		values.put(CarHelper.COLUMN_MODEL, "Figo");
		values.put(CarHelper.COLUMN_MODEL_MFG, "Ford");
		values1.put(CarHelper.COLUMN_MODEL_MFG, "Maruti");
		values1.put(CarHelper.COLUMN_MODEL, "Alto");
		database.insert(CarHelper.TABLE_MODEL, null, values);
		database.insert(CarHelper.TABLE_MODEL, null, values1);

	}
}
