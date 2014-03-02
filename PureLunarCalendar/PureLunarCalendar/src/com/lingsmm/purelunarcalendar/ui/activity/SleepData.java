package com.lingsmm.purelunarcalendar.ui.activity;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class SleepData extends SQLiteOpenHelper {

	private final static String DATABASE_NAME = "SLEEPCALENDAR.db";
	private final static int DATABASE_VERSION = 1;
	private final static String TABLE_NAME = "sleepcalendar_table";
	private final static String SLEEPCALENDAR_DATE = "sleepcalendar_date";
	private final static String SLEEP_TIEM = "sleep_time";
	private final static String SLEEP_HOUR = "sleep_hour";
	private final static String WAKE_TIME = "wake_time";
	private final static String WAKE_HOUR = "wake_hour";
	private final static String IS_LATE = "is_late";
	private final static String LATE_REASON = "late_reason";

	SleepData(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	// TODO 创建数据库后，对数据库的操作     
	@Override
	public void onCreate(SQLiteDatabase db) {
		String sql = "CREATE TABLE " + TABLE_NAME + "(" + SLEEPCALENDAR_DATE
				+ " text," + SLEEP_TIEM + " text," + SLEEP_HOUR + " INTEGER,"
				+ WAKE_TIME + " text," + WAKE_HOUR + " INTEGER," + IS_LATE + " INTEGER," + LATE_REASON +" text);";
		db.execSQL(sql);
	}   

	// TODO 更改数据库版本的操作
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		String sql = "DROP TABLE IF EXISTS " + TABLE_NAME;
		db.execSQL(sql);
		onCreate(db);
	}   

	// TODO 每次成功打开数据库后首先被执行
	@Override
	public void onOpen(SQLiteDatabase db) {
		super.onOpen(db);
	}

	public Cursor select() {
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db
				.query(TABLE_NAME, null, null, null, null, null, null);
		return cursor;
	}   

	// 增加操作
	public long insert(String sleepcalendardate, String sleeptime,
			int sleephour, String waketime, int wakehour, int intIslate,String latereason) {
		SQLiteDatabase db = this.getWritableDatabase();
		/* ContentValues */
		ContentValues cv = new ContentValues();
		cv.put(SLEEPCALENDAR_DATE, sleepcalendardate);
		cv.put(SLEEP_TIEM, sleeptime);
		cv.put(SLEEP_HOUR, sleephour);
		cv.put(WAKE_TIME, waketime);
		cv.put(WAKE_HOUR, wakehour);
		cv.put(IS_LATE, intIslate);
		cv.put(LATE_REASON, latereason);
		long row = db.insert(TABLE_NAME, null, cv);
		return row;
	}

	// 删除操作
	public void delete(String sleepcalendardate) {
		SQLiteDatabase db = this.getWritableDatabase();
		String where = SLEEPCALENDAR_DATE + " = ?";
		String[] whereValue = { sleepcalendardate };
		db.delete(TABLE_NAME, where, whereValue);
	}

	// 修改操作
	public void update(String sleepcalendardate, String sleep_time,
			int sleep_hour, String wake_time, int wake_hour, int intIslate,String latereason) {
		SQLiteDatabase db = this.getWritableDatabase();
		String where = SLEEPCALENDAR_DATE + " = ?";
		String[] whereValue = { sleepcalendardate };

		ContentValues cv = new ContentValues();
		cv.put(SLEEPCALENDAR_DATE, sleepcalendardate);
		cv.put(SLEEP_TIEM, sleep_time);
		cv.put(SLEEP_HOUR, sleep_hour);
		cv.put(WAKE_TIME, wake_time);
		cv.put(WAKE_HOUR, wake_hour);
		cv.put(IS_LATE, intIslate);
		cv.put(LATE_REASON, latereason);
		db.update(TABLE_NAME, cv, where, whereValue);
	}

}
