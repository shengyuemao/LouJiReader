package com.louji.adapter;

import android.view.View;

public abstract class PageAdapter
{
	/**
	 * @return 页面view
	 */
	public abstract View getView();

	/**
	 * 获取列表数目
	 * @return
	 */
	public abstract int getCount();

	/**
	 * 将内容添加到view中
	 * 
	 * @param view
	 *            包含内容的view
	 * @param position
	 *            第position页
	 */
	public abstract void addContent(View view, int position);
}
