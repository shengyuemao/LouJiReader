package com.louji.dbbuilder;

import android.content.ContentValues;
import android.database.Cursor;

public abstract class DatabaseBuilder<T> {
	/**
	 * Creates object out of cursor
	 * 
	 * @param c
	 * @return
	 */
	public abstract T build(Cursor c);
	
	/**
	 * Puts an object into a ContentValues instance
	 * 
	 * @param t
	 * @return
	 */
	public abstract ContentValues deconstruct(T t);
}
