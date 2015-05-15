package com.louji.adapter;

import java.util.List;

import android.annotation.SuppressLint;
import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.gc.materialdesign.views.ButtonFlat;
import com.gc.materialdesign.views.ButtonFloat;
import com.louji.base.R;
import com.louji.bean.BookBean;

@SuppressLint("InflateParams")
public class BookMarkAdapter extends BaseAdapter
{

	private List<BookBean> bookBeans;
	private Context context;

	public BookMarkAdapter(Context context, List<BookBean> bookBeans)
	{
		this.bookBeans = bookBeans;
		this.context = context;
	}

	@Override
	public int getCount()
	{
		// TODO Auto-generated method stub
		return bookBeans.size();
	}

	@Override
	public BookBean getItem(int position)
	{
		// TODO Auto-generated method stub
		return bookBeans.get(position);
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
		final ViewHolder viewHolder;
		if (convertView == null)
		{
			viewHolder = new ViewHolder();
			convertView = LayoutInflater.from(context).inflate(
					R.layout.recommendfragment_item, null);
			viewHolder.imageView = (ImageView) convertView
					.findViewById(R.id.recommendfragment_item_book_image);
			viewHolder.bookTitle = (TextView) convertView
					.findViewById(R.id.recommendfragment_item_book_title);
			viewHolder.bookBody = (TextView) convertView
					.findViewById(R.id.recommendfragment_item_book_info);
			viewHolder.bookOnline = (ButtonFlat) convertView
					.findViewById(R.id.recommendfragment_item_book_online);
			viewHolder.bookDownload = (ButtonFlat) convertView
					.findViewById(R.id.recommendfragment_item_book_download);
			convertView.setTag(viewHolder);
		}else{
			viewHolder = (ViewHolder)convertView.getTag();
		}
		
		/*Uri uri = Uri.parse(getItem(position).getImageUrl());//œ¬‘ÿÕº∆¨		
		viewHolder.imageView.setImageURI(uri);*/
		
		viewHolder.bookTitle.setText(getItem(position).getBookTitle());
		
		viewHolder.bookBody.setText(getItem(position).getBookInfo());
		
		return convertView;
	}

	static class ViewHolder
	{
		ImageView imageView;
		TextView bookTitle;
		TextView bookBody;
		ButtonFlat bookOnline, bookDownload;
	}

}
