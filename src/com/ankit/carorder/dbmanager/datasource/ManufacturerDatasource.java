package com.ankit.carorder.dbmanager.datasource;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.ankit.carorder.dbmanager.CarHelper;
import com.ankit.carorder.dbmanager.model.Manufacturer;

public class ManufacturerDatasource extends CarDatasource {

	private String[] allManufacturerColumns = { CarHelper.COLUMN_ID,
			CarHelper.COLUMN_MANUFACTURER };

	public ManufacturerDatasource(Context context) {
		super(context);
	}

	public List<Manufacturer> getAllManufecture() {
		List<Manufacturer> manufacturers = new ArrayList<Manufacturer>();
		Cursor cursor = database.query(CarHelper.TABLE_MANUFACTURER,
				allManufacturerColumns, null, null, null, null, null);
		cursor.moveToFirst();
		while (!cursor.isAfterLast()) {
			Manufacturer manufacturer = new Manufacturer();
			manufacturer.setId(cursor.getLong(0));
			manufacturer.setManufacturerName(cursor.getString(1));
			manufacturers.add(manufacturer);
			cursor.moveToNext();
		}
		cursor.close();
		return manufacturers;
	}

	@Override
	public void insertData() {
		ContentValues values = new ContentValues();
		ContentValues values1 = new ContentValues();
		values.put(CarHelper.COLUMN_MANUFACTURER, "Maruti");
		values1.put(CarHelper.COLUMN_MANUFACTURER, "Ford");
		database.insert(CarHelper.TABLE_MANUFACTURER, null, values);
		database.insert(CarHelper.TABLE_MANUFACTURER, null, values1);

	}
}
