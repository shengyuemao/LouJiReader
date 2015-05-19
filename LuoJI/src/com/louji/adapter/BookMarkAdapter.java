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

/**
 * �鼮�б������� ���������鼮�б�
 * 
 * @author ʢ��ï
 * @since 2015/5/19
 */
@SuppressLint("InflateParams")
public class BookMarkAdapter extends BaseAdapter
{

	private List<BookBean> bookBeans; // �鼮�б�
	private Context context;

	private OnLineReaderListener onLineReaderListener;// �����Ķ�

	private OnDownLoadListener onDownLoadListener;// ���ر����Ķ�

	public BookMarkAdapter(Context context, List<BookBean> bookBeans)// ��ʼ��
	{
		this.bookBeans = bookBeans;
		this.context = context;
	}

	/**
	 * ��ȡ�б���Ŀ
	 */
	@Override
	public int getCount()
	{
		// TODO Auto-generated method stub
		return bookBeans.size();
	}

	/**
	 * ��ȡ��ǰ��
	 */
	@Override
	public BookBean getItem(int position)
	{
		// TODO Auto-generated method stub
		return bookBeans.get(position);
	}

	/**
	 * ��ȡ��ǰ���ID
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
			convertView = LayoutInflater.from(context).inflate(
					R.layout.fragment_recommend_item, null);
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
					onDownLoadListener.download(getItem(position), v);
				}

			}
		});

		return convertView;
	}

	/**
	 * ���������Ķ��¼�
	 * 
	 * @param onLineReaderListener
	 */
	public void setOnLineReaderListener(
			OnLineReaderListener onLineReaderListener)
	{
		this.onLineReaderListener = onLineReaderListener;
	}

	/**
	 * ���������Ķ��¼�
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
	}

	/**
	 * �����Ķ�
	 * 
	 * @author ʢ��ï
	 *
	 */
	public interface OnLineReaderListener
	{
		public void onReader(BookBean bookBean);
	}

	/**
	 * �����Ķ�
	 * 
	 * @author ʢ��ï
	 *
	 */
	public interface OnDownLoadListener
	{
		public void download(BookBean bookBean, final View v);
	}

}
