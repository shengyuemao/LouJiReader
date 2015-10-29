package com.louji.widgets;

import java.util.List;

import com.louji.util.NetImageLoader;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;

public class CartoonLinearLayout extends LinearLayout
{

	private NetImageLoader imageLoader;

	public CartoonLinearLayout(Context context)
	{
		super(context);
		// TODO Auto-generated constructor stub
	}

	public CartoonLinearLayout(Context context, AttributeSet attrs)
	{
		super(context, attrs);
		// TODO Auto-generated constructor stub

	}

	public void setCartoonListAdapter(List<PhotoView> cartoonListAdapter)
	{

		if (cartoonListAdapter != null)
		{
			imageLoader = new NetImageLoader(getContext());
			for (int i = 0; i < cartoonListAdapter.size(); i++)
			{
				PhotoView imageTouchView = cartoonListAdapter.get(i);
				LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
						LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
				imageTouchView.setLayoutParams(params);
				CartoonLinearLayout.this.addView(imageTouchView,
						LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
				imageLoader.displayImage(imageTouchView.getTag() + "",
						imageTouchView);
			}

		}
	}

}
