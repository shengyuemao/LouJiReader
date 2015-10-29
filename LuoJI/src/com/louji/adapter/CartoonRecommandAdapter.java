package com.louji.adapter;

import java.util.List;

import com.louji.base.R;
import com.louji.bean.CartoonBean;
import com.louji.util.NetImageLoader;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class CartoonRecommandAdapter extends BaseAdapter
{

	private Context context;
	private List<CartoonBean> cartoonBeans;

	public CartoonRecommandAdapter(Context context,
			List<CartoonBean> cartoonBeans)
	{
		// TODO Auto-generated constructor stub
		this.context = context;
		this.cartoonBeans = cartoonBeans;
	}

	@Override
	public int getCount()
	{
		// TODO Auto-generated method stub
		return cartoonBeans.size();
	}

	@Override
	public CartoonBean getItem(int position)
	{
		// TODO Auto-generated method stub
		return cartoonBeans.get(position);
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
			convertView = LayoutInflater.from(context)
					.inflate(R.layout.fragment_cartoon_item, null);
			viewHolder = new ViewHolder();
			viewHolder.contentView = (TextView) convertView
					.findViewById(R.id.cartoonfragment_item_book_info);
			viewHolder.titleView = (TextView) convertView
					.findViewById(R.id.cartoonfragment_item_book_title);
			viewHolder.imageView = (ImageView) convertView
					.findViewById(R.id.cartoonfragment_item_book_image);
			convertView.setTag(viewHolder);
		} else
		{
			viewHolder = (ViewHolder) convertView.getTag();
		}

		CartoonBean cartoonBean = getItem(position);
		viewHolder.contentView.setText(cartoonBean.getImagecontent());
		viewHolder.titleView.setText(cartoonBean.getImagetitle());
		if (cartoonBean.getImageurl() != null
				&& !cartoonBean.getImageurl().equals(""))
		{
			NetImageLoader imageLoader = new NetImageLoader(context);
			imageLoader.displayImage(cartoonBean.getImageurl(),
					viewHolder.imageView);
			// OnlineImageLoader.displayImage(cartoonBean.getImageurl(),
			// viewHolder.imageView);
		} else
			if (cartoonBean.getImagefilepath() != null
					&& !cartoonBean.getImagefilepath().equals(""))
		{
			viewHolder.imageView.setImageBitmap(
					BitmapFactory.decodeFile(cartoonBean.getImagefilepath()));
		}

		return convertView;
	}

	static class ViewHolder
	{
		TextView titleView;
		TextView contentView;
		ImageView imageView;
	}

}
