package com.louji.adapter;

import java.util.List;

import com.louji.base.R;
import com.louji.bean.ReadedBookGridBean;
import com.louji.util.NetImageLoader;
import com.louji.widgets.RoundImageView;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

@SuppressLint("InflateParams")
public class ReadedGridAdapter extends BaseAdapter
{

	private List<ReadedBookGridBean> readedBookGridBeans;
	private Context context;
	private LayoutInflater inflater;

	public ReadedGridAdapter(List<ReadedBookGridBean> readedBookGridBeans,
			Context context)
	{
		this.readedBookGridBeans = readedBookGridBeans;
		this.context = context;
		inflater = LayoutInflater.from(this.context);
	}

	@Override
	public int getCount()
	{
		// TODO Auto-generated method stub
		return readedBookGridBeans.size();
	}

	@Override
	public ReadedBookGridBean getItem(int position)
	{
		// TODO Auto-generated method stub
		return readedBookGridBeans.get(position);
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
		ViewHolder holder;
		if (convertView == null)
		{
			convertView = inflater.inflate(R.layout.fragment_readed_grid_item,
					null);
			holder = new ViewHolder();
			holder.imageView = (RoundImageView) convertView
					.findViewById(R.id.fragment_readed_image_item);
			holder.textView = (TextView) convertView
					.findViewById(R.id.fragment_readed_text_item);
			convertView.setTag(holder);
		} else
		{
			holder = (ViewHolder) convertView.getTag();
		}
		NetImageLoader imageLoader = new NetImageLoader(context);
		imageLoader.displayImage(getItem(position).getImageUrl(),
				holder.imageView);

		holder.textView.setText(getItem(position).getBookName());
		return convertView;
	}

	static class ViewHolder
	{
		RoundImageView imageView;
		TextView textView;
	}

}
