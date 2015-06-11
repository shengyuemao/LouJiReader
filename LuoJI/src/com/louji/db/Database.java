package com.louji.db;

import java.util.List;

public interface Database<T>
{

	public void save(T v, String dbname);

	public void delete(int id, String dbname);

	public void update(T v, String dbname);

	public T selectline(int id);

	public List<T> selectMore(String what);

	public T selectline(String what);

	public List<T> selectMore();

	public List<T> selectMore(int id);

	public List<T> selectScrollData(int offset, int maxResult);

	public List<T> selectMore(int id, int offset, int maxResult);

	public List<T> selectMore(String what, int offset, int maxResult);

	public long selectCount();

}
