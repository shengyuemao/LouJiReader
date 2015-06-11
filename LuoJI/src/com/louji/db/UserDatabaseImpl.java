package com.louji.db;

import java.util.ArrayList;
import java.util.List;

import com.louji.dbbean.UserBean;
import com.louji.dbbuilder.UserDatabaseBuilder;

import android.content.Context;
import android.database.Cursor;

public class UserDatabaseImpl extends DatabaseImpl implements
		Database<UserBean>
{

	public UserDatabaseImpl(Context context)
	{
		super(context);
	}

	private UserDatabaseBuilder database = new UserDatabaseBuilder();

	@Override
	public void save(UserBean v, String dbname)
	{
		// �����ݿ� ������
		openDatabase();

		db.insert(dbname, null, database.deconstruct(v));
		// �ر����ݿ⣬����
		closeDatabase();
	}

	@Override
	public void update(UserBean v, String dbname)
	{
		// �����ݿ� ������
		openDatabase();
		String[] whereArgs =
		{ String.valueOf((v).getUid()) };
		db.update(dbname, database.deconstruct(v), "uid = ?", whereArgs);

		// �ر����ݿ⣬����
		closeDatabase();
	}

	@Override
	public UserBean selectline(int id)
	{
		// �����ݿ� ������
		openDatabase();
		Cursor cursor = db.rawQuery("select * from "+UserBean.getTable() +"where uid = ?",
				new String[]
				{ String.valueOf(id) });
		if (cursor.moveToFirst())
		{
			UserBean user = database.build(cursor);
			return user;
		}
		// �ر����ݿ⣬����
		closeDatabase();
		return null;
	}

	@Override
	public List<UserBean> selectMore(String what)
	{
		List<UserBean> UserBeans = new ArrayList<UserBean>();
		// �����ݿ� ������
		openDatabase();
		Cursor cursor = db.rawQuery(
				"select * from "+UserBean.getTable()+"where username like ?", new String[]
				{ "%" + what + "%" });
		while (cursor.moveToNext())
		{
			UserBean user = database.build(cursor);
			UserBeans.add(user);
		}
		// �ر����ݿ⣬����
		closeDatabase();

		return UserBeans;
	}

	@Override
	public void delete(int id, String dbname)
	{
		// �����ݿ� ������
		openDatabase();
		String[] whereArgs =
		{ String.valueOf(id) };
		db.delete(dbname, "uid = ?", whereArgs);

		// �ر����ݿ⣬����
		closeDatabase();
	}

	@Override
	public UserBean selectline(String what)
	{
		// �����ݿ� ������
		openDatabase();
		String[] whereArgs =
		{ "%" + what + "%" };
		Cursor cursor = db.rawQuery(
				"select * from "+UserBean.getTable()+" where username like ?", whereArgs);

		if (cursor.moveToFirst())
		{
			UserBean UserBean = database.build(cursor);
			return UserBean;
		}
		// �ر����ݿ⣬����
		closeDatabase();
		return null;
	}

	@Override
	public List<UserBean> selectMore(int id)
	{
		List<UserBean> UserBeans = new ArrayList<UserBean>();
		// �����ݿ� ������
		openDatabase();
		Cursor cursor = db.rawQuery("select * from "+UserBean.getTable()+" where uid = ?",
				new String[]
				{ String.valueOf(id) });
		while (cursor.moveToNext())
		{
			UserBean user = database.build(cursor);
			UserBeans.add(user);
		}
		// �ر����ݿ⣬����
		closeDatabase();

		return UserBeans;
	}

	@Override
	public List<UserBean> selectScrollData(int offset, int maxResult)
	{
		List<UserBean> users = new ArrayList<UserBean>();
		// �����ݿ� ������
		openDatabase();
		Cursor cursor = db.rawQuery(
				"select * from "+UserBean.getTable()+" order by uid asc limit ?,?",
				new String[]
				{ String.valueOf(offset), String.valueOf(maxResult) });
		while (cursor.moveToNext())
		{
			UserBean user = database.build(cursor);
			users.add(user);
		}

		// �ر����ݿ⣬����
		closeDatabase();
		return users;
	}

	@Override
	public List<UserBean> selectMore(int id, int offset, int maxResult)
	{
		List<UserBean> users = new ArrayList<UserBean>();
		// �����ݿ� ������
		openDatabase();
		Cursor cursor = db
				.rawQuery(
						"select * from "+UserBean.getTable()+" where uid = ? order by uid asc limit ?,?",
						new String[]
						{ String.valueOf(id), String.valueOf(offset),
								String.valueOf(maxResult) });
		while (cursor.moveToNext())
		{
			UserBean user = database.build(cursor);
			users.add(user);
		}

		// �ر����ݿ⣬����
		closeDatabase();
		return users;
	}

	@Override
	public List<UserBean> selectMore(String what, int offset, int maxResult)
	{
		List<UserBean> users = new ArrayList<UserBean>();
		// �����ݿ� ������
		openDatabase();
		Cursor cursor = db
				.rawQuery(
						"select * from "+UserBean.getTable()+" where username like ? order by uid asc limit ?,?",
						new String[]
						{ "%" + what + "%", String.valueOf(offset),
								String.valueOf(maxResult) });
		while (cursor.moveToNext())
		{
			UserBean user = database.build(cursor);
			users.add(user);
		}

		// �ر����ݿ⣬����
		closeDatabase();
		return users;
	}

	@Override
	public long selectCount()
	{
		// �����ݿ� ������
		openDatabase();
		Cursor cursor = db.rawQuery("select count(*) from "+UserBean.getTable(), null);
		cursor.moveToFirst();
		long reslut = cursor.getLong(0);
		// �ر����ݿ⣬����
		closeDatabase();
		return reslut;
	}

	@Override
	public List<UserBean> selectMore()
	{
		// TODO Auto-generated method stub
		return null;
	}

}
