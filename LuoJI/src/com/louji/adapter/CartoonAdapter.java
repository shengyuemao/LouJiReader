package com.louji.adapter;

import java.util.List;

import com.louji.util.NetImageLoader;
import com.louji.widgets.ImageTouchView;
import com.louji.widgets.PhotoView;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;

public class CartoonAdapter extends PagerAdapter
{

	private List<PhotoView> imageViews;
	private Context context;

	private NetImageLoader imageLoader;

	private OnClickPointerListener onClickPointerListener;

	public CartoonAdapter(Context context, List<PhotoView> imageViews)
	{
		// TODO Auto-generated constructor stub
		this.context = context;
		this.imageViews = imageViews;
		this.imageLoader = new NetImageLoader(context);
	}

	public OnClickPointerListener getOnClickPointerListener()
	{
		return onClickPointerListener;
	}

	public void setOnClickPointerListener(
			OnClickPointerListener onClickPointerListener)
	{
		this.onClickPointerListener = onClickPointerListener;
	}

	@Override
	public int getCount()
	{
		// TODO Auto-generated method stub
		return imageViews.size();
	}

	@Override
	public void destroyItem(View container, int position, Object object)
	{
		// TODO Auto-generated method stub
		((ViewPager) container).removeView(imageViews.get(position));
	}

	@Override
	public Object instantiateItem(View container, int position)
	{
		// TODO Auto-generated method stub
		PhotoView imageView = imageViews.get(position);
		imageView.setId(position);
		imageLoader.displayImage(imageView.getTag() + "", imageView);

		((ViewPager) container).addView(imageViews.get(position));

		return imageViews.get(position);
	}

	@Override
	public boolean isViewFromObject(View arg0, Object arg1)
	{
		// TODO Auto-generated method stub
		return arg0 == arg1;
	}

	public interface OnClickPointerListener
	{
		public void onRightClicked(int position);
		public void onLeftClicked(int position);
	}

}
