package com.louji.adapter;

import java.util.LinkedList;

import com.louji.base.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

public class VedioListAdapter extends BaseAdapter
{

	private LinkedList<String> paths;
	private Context context;

	public VedioListAdapter(LinkedList<String> paths, Context context)
	{
		// TODO Auto-generated constructor stub
		this.paths = paths;
		this.context = context;
	}

	@Override
	public int getCount()
	{
		// TODO Auto-generated method stub
		return paths.size();
	}

	@Override
	public String getItem(int position)
	{
		// TODO Auto-generated method stub
		return paths.get(position);
	}

	@Override
	public long getItemId(int position)
	{
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent)
	{
		// TODO Auto-generated method stub
		ViewHolder viewHolder;
		if (convertView == null)
		{
			viewHolder = new ViewHolder();
			convertView = LayoutInflater.from(context)
					.inflate(R.layout.fragment_vediolist_item, null);
			viewHolder.textureView = (ImageView) convertView
					.findViewById(R.id.surface);
			convertView.setTag(viewHolder);
		} else
		{
			viewHolder = (ViewHolder) convertView.getTag();
		}

		return convertView;
	}

	static class ViewHolder
	{
		ImageView textureView;
	}

}
