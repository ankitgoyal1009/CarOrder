package com.ankit.carorder.dbmanager;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class CarHelper extends SQLiteOpenHelper {
	private static final String DATABASE_NAME = "car.db";
	private static final int DATABASE_VERSION = 1;

	// Database creation sql statement
	public static final String TABLE_MANUFACTURER = "manufacturer";
	public static final String TABLE_MODEL = "model";
	public static final String TABLE_COLOR = "color";
	public static final String TABLE_ORDER = "carorder";

	public static final String COLUMN_ID = "_id";
	public static final String COLUMN_MANUFACTURER = "manufacturer";

	public static final String COLUMN_MODEL = "model";
	public static final String COLUMN_MODEL_MFG = "model_manufacturer";

	public static final String COLUMN_COLOR = "color";

	public static final String COLUMN_ORDER_MODEL = "model";
	public static final String COLUMN_ORDER_COLOR = "color";
	public static final String COLUMN_ORDER_STATUS = "status";
	public static final String COLUMN_ORDER_TIME = "time";
	public static final String COLUMN_ORDER_DELIVERY_TIME = "deliveryTime";

	// private static final String COLUMN_ORDER = null;

	private String createManufacturerTable() {
		return "create table " + TABLE_MANUFACTURER + "(" + COLUMN_ID
				+ " integer primary key autoincrement, " + COLUMN_MANUFACTURER
				+ " text not null);";
	}

	private String createModelTable() {
		return "create table " + TABLE_MODEL + "(" + COLUMN_ID
				+ " integer primary key autoincrement, " + COLUMN_MODEL
				+ " text not null," + COLUMN_MODEL_MFG + " text);";
	}

	private String createColorTable() {
		return "create table " + TABLE_COLOR + "(" + COLUMN_ID
				+ " integer primary key autoincrement, " + COLUMN_COLOR
				+ " text not null);";
	}

	private String createOrderTable() {
		return "create table " + TABLE_ORDER + " ( " + COLUMN_ID
				+ " integer primary key autoincrement, " + COLUMN_ORDER_MODEL
				+ " integer," + COLUMN_ORDER_COLOR + " integer,"+ COLUMN_ORDER_TIME+" VARCHAR,"
				+ COLUMN_ORDER_DELIVERY_TIME+" VARCHAR,"+ COLUMN_ORDER_STATUS + " integer);";
	}
	
	public CarHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase database) {
		database.execSQL(createColorTable());
		database.execSQL(createManufacturerTable());
		database.execSQL(createModelTable());
		database.execSQL(createOrderTable());
	}

	@Override
	public void onUpgrade(SQLiteDatabase arg0, int arg1, int arg2) {
		// TODO Auto-generated method stub

	}

}
