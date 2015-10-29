package com.louji.widgets;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;

public class RecommandCartoonLinearLayout extends LinearLayout
{

	private BaseAdapter baseAdapter;

	private int count;

	private OnClickItemListener onClickItemListener;

	public RecommandCartoonLinearLayout(Context context)
	{
		super(context);
		// TODO Auto-generated constructor stub
	}

	public RecommandCartoonLinearLayout(Context context, AttributeSet attrs)
	{
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}

	@SuppressLint("NewApi")
	public RecommandCartoonLinearLayout(Context context, AttributeSet attrs,
			int defStyleAttr)
	{
		super(context, attrs, defStyleAttr);
		
	}

	public void setBaseAdapter(BaseAdapter baseAdapter)
	{
		this.baseAdapter = baseAdapter;

		if (baseAdapter != null)
		{
			initData();
		}
	}

	private void initData()
	{
		count = baseAdapter.getCount();
		for (int i = 0; i < count; i++)
		{
			final int position = i;
			View itemView = baseAdapter.getView(i, null, null);
			itemView.setOnClickListener(new OnClickListener()
			{

				@Override
				public void onClick(View v)
				{
					// TODO Auto-generated method stub
					if (onClickItemListener != null)
					{
						onClickItemListener.onSelected(v, position);
					}

				}
			});
			this.addView(itemView, 0);
		}
	}
	
	
	public void removeView(int position){
		this.removeView(position);
		
	}
	
	public void addHeaderView(View view){
		this.addView(view, 0);
	}
	
	public void removeViews(){
		this.removeAllViews();
	}
	
	

	public OnClickItemListener getOnClickItemListener()
	{
		return onClickItemListener;
	}

	public void setOnClickItemListener(OnClickItemListener onClickItemListener)
	{
		this.onClickItemListener = onClickItemListener;
	}



	public interface OnClickItemListener
	{
		public void onSelected(View view, int position);
	}

}
