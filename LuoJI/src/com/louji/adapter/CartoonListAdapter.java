package com.louji.adapter;

import java.util.List;

import com.louji.base.R;
import com.louji.util.NetImageLoader;
import com.louji.widgets.ImageTouchView;
import com.louji.widgets.PhotoView;

import android.content.Context;
import android.graphics.Matrix;
import android.text.Html.ImageGetter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

public class CartoonListAdapter extends BaseAdapter
{
	private List<String> imageViews;
	private Context context;
	private NetImageLoader imageLoader;
	public CartoonListAdapter(Context context, List<String> imageTouchViews)
	{
		// TODO Auto-generated constructor stub
		this.context = context;
		this.imageViews = imageTouchViews;
		this.imageLoader = new NetImageLoader(context);
	}

	@Override
	public int getCount()
	{
		// TODO Auto-generated method stub
		return imageViews.size();
	}

	@Override
	public String getItem(int position)
	{
		// TODO Auto-generated method stub
		return imageViews.get(position);
	}

	@Override
	public long getItemId(int position)
	{
		// TODO Auto-generated method stub
		return position;
	}

	@SuppressWarnings("null")
	@Override
	public View getView(int position, View convertView, ViewGroup parent)
	{
		ViewHolder viewHolder;
		if (convertView == null)
		{
			viewHolder = new ViewHolder();
			convertView = LayoutInflater.from(context)
					.inflate(R.layout.activity_list_cartoon_item, null);

			viewHolder.imageTouchView = (PhotoView) convertView
					.findViewById(R.id.activity_list_cartoon_item_image);

			/*
			 * Matrix matrix = new Matrix(); matrix.postScale((float) 0.5,
			 * (float) 0.5);
			 * 
			 * viewHolder.imageTouchView.setImageMatrix(matrix);
			 */

			convertView.setTag(viewHolder);
		} else
		{
			viewHolder = (ViewHolder) convertView.getTag();
		}
		imageLoader.displayImage(getItem(position), viewHolder.imageTouchView);

		return convertView;
	}

	static class ViewHolder
	{
		PhotoView imageTouchView;
	}

}
