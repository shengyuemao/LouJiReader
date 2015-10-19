package com.louji.db;

import com.louji.base.LoujiBaseApplication;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

public class DatabaseImpl
{

	public static final String DB_NAME = "app_maomo";

	public Context mActivity = LoujiBaseApplication.context;

	public static SQLiteDatabase db;

	public DatabaseImpl(Context context)
	{
		create();
	}

	public void openDatabase()
	{
		db = mActivity
				.openOrCreateDatabase(DB_NAME, Context.MODE_PRIVATE, null);
	}

	public void closeDatabase()
	{
		db.close();
	}

	/**
	 * ³¹µ×É¾³ýÊý¾Ý¿â
	 * 
	 * @return
	 */
	public boolean deleteDatabase()
	{
		return mActivity.deleteDatabase(DB_NAME);
	}

	private void create()
	{

		db = mActivity
				.openOrCreateDatabase(DB_NAME, Context.MODE_PRIVATE, null);

		// create tables if necessary
		db.execSQL("CREATE TABLE IF NOT EXISTS think_louji_book (bookid INTEGER PRIMARY KEY AUTOINCREMENT, booktitle VARCHAR( 50 ), bookcontent TEXT NOT NULL, bookurl VARCHAR( 200 ),bookimage VARCHAR(200), isrecommend INT( 2 ), booklocalpath VARCHAR(200));");

		db.execSQL("CREATE TABLE IF NOT EXISTS  think_louji_user (uid INTEGER PRIMARY KEY AUTOINCREMENT,username VARCHAR( 100 ),password VARCHAR( 50 ),email VARCHAR( 50 ),phone VARCHAR( 30 ),authinfo VARCHAR( 255 ),photo VARCHAR( 100 ),info TEXT,time DATETIME DEFAULT CURRENT_TIMESTAMP);");
		db.close();

	}
}
