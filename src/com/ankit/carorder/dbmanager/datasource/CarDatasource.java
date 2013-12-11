package com.ankit.carorder.dbmanager.datasource;

import com.ankit.carorder.dbmanager.CarHelper;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public abstract class CarDatasource {
	protected SQLiteDatabase database;
	private CarHelper dbHelper;
	public String[] allColumns;

	public CarDatasource(Context context) {
		dbHelper = new CarHelper(context);
	}

	public void open() throws SQLException {
		database = dbHelper.getWritableDatabase();
	}

	public void close() {
		dbHelper.close();
	}

	public abstract void insertData(); 

}
