package com.louji.adapter;

import java.util.List;

import com.gc.materialdesign.views.ButtonFlat;
import com.gc.materialdesign.views.Slider;
import com.louji.base.R;
import com.louji.bean.BookBean;
import com.louji.readtwo.Read;
import com.louji.util.NetImageLoader;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * 书籍列表适配器 用于适配书籍列表
 * 
 * @author 盛月茂
 * @since 2015/5/19
 */
@SuppressLint("InflateParams")
public class BookMarkAdapter extends BaseAdapter
{

	private List<BookBean> bookBeans; // 书籍列表
	private Context context;

	private OnLineReaderListener onLineReaderListener;// 在线阅读

	private OnDownLoadListener onDownLoadListener;// 下载本地阅读

	public BookMarkAdapter(Context context, List<BookBean> bookBeans)// 初始化
	{
		this.bookBeans = bookBeans;
		this.context = context;
	}

	/**
	 * 获取列表数目
	 */
	@Override
	public int getCount()
	{
		// TODO Auto-generated method stub
		return bookBeans.size();
	}

	/**
	 * 获取当前项
	 */
	@Override
	public BookBean getItem(int position)
	{
		// TODO Auto-generated method stub
		return bookBeans.get(position);
	}

	/**
	 * 获取当前项的ID
	 */
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
			convertView = LayoutInflater.from(context)
					.inflate(R.layout.fragment_recommend_item, null);
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
			viewHolder.progress = (Slider) convertView
					.findViewById(R.id.recommendfragment_item_book_slider);

			convertView.setTag(viewHolder);
		} else
		{
			viewHolder = (ViewHolder) convertView.getTag();
			viewHolder.bookDownload.setText("下载");
		}
		NetImageLoader imageLoader = new NetImageLoader(context);
		imageLoader.displayImage(getItem(position).getImageUrl(),
				viewHolder.imageView);
		/*
		 * OnlineImageLoader.displayImage(getItem(position).getImageUrl(),
		 * viewHolder.imageView, R.drawable.bookpricture);
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

		if (getItem(position).getBookFilePath() != null)
		{
			viewHolder.bookDownload.setText("阅读");
		}

		viewHolder.bookDownload.setOnClickListener(new OnClickListener()
		{

			@Override
			public void onClick(View v)
			{

				ButtonFlat btn = (ButtonFlat) v.findViewById(
						R.id.recommendfragment_item_book_download);
				if (onDownLoadListener != null && !btn.getText().equals("阅读"))
				{
					onDownLoadListener.download(getItem(position), v);
				} else
				{
					Bundle bundle = new Bundle();
					bundle.putString("filePath",
							getItem(position).getBookFilePath());

					Intent intent = new Intent();
					intent.setClass(context, Read.class);
					intent.putExtras(bundle);
					context.startActivity(intent);
				}

			}
		});

		return convertView;
	}

	/**
	 * 设置在线阅读事件
	 * 
	 * @param onLineReaderListener
	 */
	public void setOnLineReaderListener(
			OnLineReaderListener onLineReaderListener)
	{
		this.onLineReaderListener = onLineReaderListener;
	}

	/**
	 * 设置下载阅读事件
	 * 
	 * @param onDownLoadListener
	 */
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
		Slider progress;
	}

	/**
	 * 在线阅读
	 * 
	 * @author 盛月茂
	 *
	 */
	public interface OnLineReaderListener
	{
		public void onReader(BookBean bookBean);
	}

	/**
	 * 下载阅读
	 * 
	 * @author 盛月茂
	 *
	 */
	public interface OnDownLoadListener
	{
		public void download(BookBean bookBean, View v);
	}

}
