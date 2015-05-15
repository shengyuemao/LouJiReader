package com.louji.adapter;

import java.util.List;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.gc.materialdesign.views.ButtonFlat;
import com.louji.base.R;
import com.louji.bean.BookBean;

@SuppressLint("InflateParams")
public class BookMarkAdapter extends BaseAdapter
{

	private List<BookBean> bookBeans;
	private Context context;

	private OnLineReaderListener onLineReaderListener;

	private OnDownLoadListener onDownLoadListener;

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
	public View getView(final int position, View convertView, ViewGroup parent)
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
		} else
		{
			viewHolder = (ViewHolder) convertView.getTag();
		}

		/*
		 * Uri uri = Uri.parse(getItem(position).getImageUrl());//ÏÂÔØÍ¼Æ¬
		 * viewHolder.imageView.setImageURI(uri);
		 */

		viewHolder.bookTitle.setText(getItem(position).getBookTitle());

		viewHolder.bookBody.setText(getItem(position).getBookInfo());

		viewHolder.bookOnline.setOnClickListener(new OnClickListener()
		{

			@Override
			public void onClick(View v)
			{
				if (onLineReaderListener != null)
				{
					onLineReaderListener.onReader(getItem(position));
				}

			}
		});

		viewHolder.bookDownload.setOnClickListener(new OnClickListener()
		{

			@Override
			public void onClick(View v)
			{
				
				if (onDownLoadListener != null)
				{
					onDownLoadListener.download(getItem(position),v);
				}

			}
		});

		return convertView;
	}

	public void setOnLineReaderListener(
			OnLineReaderListener onLineReaderListener)
	{
		this.onLineReaderListener = onLineReaderListener;
	}

	public void setOnDownLoadListener(OnDownLoadListener onDownLoadListener)
	{
		this.onDownLoadListener = onDownLoadListener;
	}

	static class ViewHolder
	{
		ImageView imageView;
		TextView bookTitle;
		TextView bookBody;
		ButtonFlat bookOnline, bookDownload;
	}

	/**
	 * ÔÚÏßÔÄ¶Á
	 * 
	 * @author Ê¢ÔÂÃ¯
	 *
	 */
	public interface OnLineReaderListener
	{
		public void onReader(BookBean bookBean);
	}

	public interface OnDownLoadListener
	{
		public void download(BookBean bookBean,final View v);
	}

}
