package com.louji.db;

import android.content.Context;

import com.louji.dbbean.UserBean;

public class DatabaseFactory
{

	public static Database<UserBean> userData(Context activity)
	{
		return new UserDatabaseImpl(activity);
	}

	public static BookDatabaseImpl bookData(Context activity)
	{
		return new BookDatabaseImpl(activity);
	}

}
