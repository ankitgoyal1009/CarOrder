package com.ankit.carorder.dbmanager.datasource;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.util.Log;

import com.ankit.carorder.dbmanager.CarHelper;
import com.ankit.carorder.dbmanager.model.CarColor;

public class CarColorDatasource extends CarDatasource {
	String[] allColorColumns = { CarHelper.COLUMN_ID, CarHelper.COLUMN_COLOR };

	public CarColorDatasource(Context context) {
		super(context);

	}

	public List<CarColor> getAllColor() {
		List<CarColor> colors = new ArrayList<CarColor>();
		Cursor cursor = database.query(CarHelper.TABLE_COLOR, allColorColumns,
				null, null, null, null, null);
		cursor.moveToFirst();
		while (!cursor.isAfterLast()) {
			CarColor carColor = new CarColor();
			carColor.setId(cursor.getLong(0));
			carColor.setColorName(cursor.getString(1));
			colors.add(carColor);
			cursor.moveToNext();
		}
		cursor.close();
		return colors;
	}

	@Override
	public void insertData() {

		ContentValues values = new ContentValues();
		ContentValues values1 = new ContentValues();
		ContentValues values2 = new ContentValues();
		values.put(CarHelper.COLUMN_COLOR, "Red");
		values1.put(CarHelper.COLUMN_COLOR, "Black");
		values2.put(CarHelper.COLUMN_COLOR, "Blue");
		database.insert(CarHelper.TABLE_COLOR, null, values);
		database.insert(CarHelper.TABLE_COLOR, null, values1);
		database.insert(CarHelper.TABLE_COLOR, null, values2);
	}
}
