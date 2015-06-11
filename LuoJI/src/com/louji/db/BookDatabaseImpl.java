package com.louji.db;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.database.Cursor;

import com.louji.dbbean.BookBean;
import com.louji.dbbuilder.BookDatabaseBuilder;

public class BookDatabaseImpl extends DatabaseImpl implements
		Database<BookBean>
{

	private BookDatabaseBuilder database = new BookDatabaseBuilder();

	public BookDatabaseImpl(Context context)
	{
		super(context);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void save(BookBean v, String dbname)
	{
		// 打开数据库 ，必须
		openDatabase();

		db.insert(dbname, null, database.deconstruct(v));
		// 关闭数据库，必须
		closeDatabase();
	}

	@Override
	public void delete(int id, String dbname)
	{
		// 打开数据库 ，必须
		openDatabase();
		String[] whereArgs =
		{ String.valueOf(id) };
		db.delete(dbname, "bookid = ?", whereArgs);

		// 关闭数据库，必须
		closeDatabase();
	}

	@Override
	public void update(BookBean v, String dbname)
	{
		// 打开数据库 ，必须
		openDatabase();
		String[] whereArgs =
		{ String.valueOf((v).getBookid()) };
		db.update(dbname, database.deconstruct(v), "bookid = ?", whereArgs);

		// 关闭数据库，必须
		closeDatabase();
	}

	@Override
	public BookBean selectline(int id)
	{
		// 打开数据库 ，必须
		openDatabase();
		Cursor cursor = db.rawQuery("select * from " + BookBean.getTable()
				+ "where bookid = ?", new String[]
		{ String.valueOf(id) });
		if (cursor.moveToFirst())
		{
			BookBean user = database.build(cursor);
			return user;
		}
		// 关闭数据库，必须
		closeDatabase();
		return null;
	}

	@Override
	public List<BookBean> selectMore(String what)
	{
		List<BookBean> bookBeans = new ArrayList<BookBean>();
		// 打开数据库 ，必须
		openDatabase();
		Cursor cursor = db.rawQuery("select * from " + BookBean.getTable()
				+ "where booktitle like ?", new String[]
		{ "%" + what + "%" });
		while (cursor.moveToNext())
		{
			BookBean user = database.build(cursor);
			bookBeans.add(user);
		}
		// 关闭数据库，必须
		closeDatabase();

		return bookBeans;
	}

	@Override
	public BookBean selectline(String what)
	{
		// 打开数据库 ，必须
		openDatabase();
		String[] whereArgs =
		{ "%" + what + "%" };
		Cursor cursor = db.rawQuery("select * from " + BookBean.getTable()
				+ " where booktitle like ?", whereArgs);

		if (cursor.moveToFirst())
		{
			BookBean UserBean = database.build(cursor);
			return UserBean;
		}
		// 关闭数据库，必须
		closeDatabase();
		return null;
	}

	@Override
	public List<BookBean> selectMore(int id)
	{
		List<BookBean> bookBeans = new ArrayList<BookBean>();
		// 打开数据库 ，必须
		openDatabase();
		Cursor cursor = db.rawQuery("select * from " + BookBean.getTable()
				+ " where bookid = ?", new String[]
		{ String.valueOf(id) });
		while (cursor.moveToNext())
		{
			BookBean user = database.build(cursor);
			bookBeans.add(user);
		}
		// 关闭数据库，必须
		closeDatabase();

		return bookBeans;
	}

	@Override
	public List<BookBean> selectScrollData(int offset, int maxResult)
	{
		List<BookBean> bookBeans = new ArrayList<BookBean>();
		// 打开数据库 ，必须
		openDatabase();
		Cursor cursor = db.rawQuery("select * from " + BookBean.getTable()
				+ " order by bookid asc limit ?,?", new String[]
		{ String.valueOf(offset), String.valueOf(maxResult) });
		while (cursor.moveToNext())
		{
			BookBean user = database.build(cursor);
			bookBeans.add(user);
		}

		// 关闭数据库，必须
		closeDatabase();
		return bookBeans;
	}

	@Override
	public List<BookBean> selectMore(int id, int offset, int maxResult)
	{
		List<BookBean> bookBeans = new ArrayList<BookBean>();
		// 打开数据库 ，必须
		openDatabase();
		Cursor cursor = db.rawQuery(
				"select * from " + BookBean.getTable()
						+ " where bookid = ? order by bookid asc limit ?,?",
				new String[]
				{ String.valueOf(id), String.valueOf(offset),
						String.valueOf(maxResult) });
		while (cursor.moveToNext())
		{
			BookBean bookBean = database.build(cursor);
			bookBeans.add(bookBean);
		}

		// 关闭数据库，必须
		closeDatabase();
		return bookBeans;
	}

	@Override
	public List<BookBean> selectMore(String what, int offset, int maxResult)
	{
		List<BookBean> bookBeans = new ArrayList<BookBean>();
		// 打开数据库 ，必须
		openDatabase();
		Cursor cursor = db
				.rawQuery(
						"select * from "
								+ BookBean.getTable()
								+ " where booktitle like ? order by bookid asc limit ?,?",
						new String[]
						{ "%" + what + "%", String.valueOf(offset),
								String.valueOf(maxResult) });
		while (cursor.moveToNext())
		{
			BookBean bookBean = database.build(cursor);
			bookBeans.add(bookBean);
		}

		// 关闭数据库，必须
		closeDatabase();
		return bookBeans;
	}

	@Override
	public long selectCount()
	{
		// 打开数据库 ，必须
		openDatabase();
		Cursor cursor = db.rawQuery(
				"select count(*) from " + BookBean.getTable(), null);
		cursor.moveToFirst();
		long reslut = cursor.getLong(0);
		// 关闭数据库，必须
		closeDatabase();
		return reslut;
	}

	@Override
	public List<BookBean> selectMore()
	{
		List<BookBean> bookBeans = new ArrayList<BookBean>();
		// 打开数据库 ，必须
		openDatabase();
		Cursor cursor = db.rawQuery("select * from " + BookBean.getTable(),
				null);
		while (cursor.moveToNext())
		{
			BookBean user = database.build(cursor);
			bookBeans.add(user);
		}
		// 关闭数据库，必须
		closeDatabase();

		return bookBeans;
	}

}
