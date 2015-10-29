package com.louji.widgets;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import com.louji.base.R;
import com.louji.jsonbean.CartoonJsonBean;
import com.louji.util.Logger;
import com.louji.util.NetImageLoader;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;

public class RecycleView extends FrameLayout
{

	private int image_counts = 5;// Ĭ�����ͼƬ
	private int time_interval = 5; // Ĭ��ʱ����5s
	private int selected_color = 0xff673ab7;
	private int unselected_color = 0xffffffff;
	// �Զ��ֲ����ÿ���
	private boolean isAutoPlay = true;

	// �Զ����ֲ�ͼ����Դ
	private List<String> imageUrls;
	private List<CartoonJsonBean> cartoonBeans ;
	// ���ֲ�ͼƬ��ImageView ��list
	private List<ImageView> imageViewsList;
	// ���±��View��list
	private List<View> dotViewsList;

	private ViewPager viewPager;
	// ��ǰ�ֲ�ҳ
	private int currentItem = 0;
	// ��ʱ����
	private ScheduledExecutorService scheduledExecutorService;

	private Context context;
	
	private OnHotCartoonListener onHotCartoonListener ;

	// Handler�����ֲ�ͼ
	@SuppressLint("HandlerLeak")
	private Handler handler = new Handler()
	{

		@SuppressLint("HandlerLeak")
		@Override
		public void handleMessage(Message msg)
		{
			// TODO Auto-generated method stub
			super.handleMessage(msg);
			viewPager.setCurrentItem(currentItem);
		}

	};

	public RecycleView(Context context)
	{
		this(context, null);
		// TODO Auto-generated constructor stub
	
	}

	public RecycleView(Context context, AttributeSet attrs)
	{
		this(context, attrs, 0);
		// TODO Auto-generated constructor stub
		
	}

	public RecycleView(Context context, AttributeSet attrs, int defStyleAttr)
	{
		super(context, attrs, defStyleAttr);
		// TODO Auto-generated constructor stub
		this.context = context;
		
		if (isAutoPlay)
		{
			startPlay();
		}
	}

	public void startPlay()
	{
		scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();
		scheduledExecutorService.scheduleAtFixedRate(new SlideShowTask(), 1,
				time_interval, TimeUnit.SECONDS);
	}

	public void stopPlay()
	{
		scheduledExecutorService.shutdown();
	}
	private void initData()
	{
		imageViewsList = new ArrayList<ImageView>();
		dotViewsList = new ArrayList<View>();
		initUI(context);
	}

	@SuppressLint("InflateParams")
	private void initUI(Context context)
	{
		if (imageUrls == null || imageUrls.size() == 0)
		{
			return;
		}

		View view = LayoutInflater.from(context)
				.inflate(R.layout.layout_recycle, null);// ���ز���

		LinearLayout dotLayout = (LinearLayout) view.findViewById(R.id.recycle_dotLayout);
		dotLayout.removeAllViews();// ����ӿؼ�

		int count = imageUrls.size();

		for (int i = 0; i < count; i++)
		{
			ImageView imageView = new ImageView(context);
			imageView.setTag(imageUrls.get(i));
			imageView.setId(i);
			imageView.setOnClickListener(new OnClickListener()
			{
				
				@Override
				public void onClick(View v)
				{
					int position = ((ImageView)v).getId() ;
					if (onHotCartoonListener != null)
					{
						onHotCartoonListener.onClicked(cartoonBeans.get(position));
					}
					
				}
			});
			Logger.i(imageUrls.get(i));
			if (i == 0)
				imageView.setScaleType(ScaleType.FIT_XY);
			imageViewsList.add(imageView);
			View dotView = new View(context);
			LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
					LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
			params.weight = 1;
			dotLayout.addView(dotView, params);
			dotViewsList.add(dotView);
		}

		viewPager = (ViewPager) view.findViewById(R.id.recycle_viewPager);
		viewPager.setFocusable(true);

		viewPager.setAdapter(new ViewPagerAdapter());
		viewPager.setOnPageChangeListener(new ViewPageChangeListener());
		
		this.addView(view);
	}

	private class ViewPagerAdapter extends PagerAdapter
	{

		@Override
		public void destroyItem(View container, int position, Object object)
		{
			// TODO Auto-generated method stub

			// super.destroyItem(container, position, object);
			((ViewPager) container).removeView(imageViewsList.get(position));
		}

		@Override
		public Object instantiateItem(View container, int position)
		{
			// TODO Auto-generated method stub
			ImageView imageView = imageViewsList.get(position);
			NetImageLoader imageLoader = new NetImageLoader(context);
			imageLoader.displayImage(imageView.getTag() + "",
					imageView);
			((ViewPager) container).addView(imageView);
			return imageView;
		}

		@Override
		public int getCount()
		{
			// TODO Auto-generated method stub
			return imageViewsList.size();
		}

		@Override
		public boolean isViewFromObject(View arg0, Object arg1)
		{
			// TODO Auto-generated method stub
			return arg0 == arg1;
		}

	}

	private class ViewPageChangeListener implements OnPageChangeListener
	{
		boolean isAutoPlay = false;

		@Override
		public void onPageScrollStateChanged(int arg0)
		{
			// TODO Auto-generated method stub
			switch (arg0)
			{
				case 1 : // ���ƻ���
					isAutoPlay = false;
					break;
				case 2 : // �����л���
					isAutoPlay = true;
					break;

				case 0 : // �������������л���ϻ��߼������
					// ��ǰΪ���һ�ţ���ʱ�������󻬣����л�����һ��
					if (viewPager.getCurrentItem() == viewPager.getAdapter()
							.getCount() - 1 && !isAutoPlay)
					{
						viewPager.setCurrentItem(0);
					}
					// ��ǰΪ��һ�ţ���ʱ�������һ������л������һ��
					else if (viewPager.getCurrentItem() == 0 && !isAutoPlay)
					{
						viewPager.setCurrentItem(
								viewPager.getAdapter().getCount() - 1);
					}
					break;
			}

		}

		@Override
		public void onPageScrolled(int arg0, float arg1, int arg2)
		{
			// TODO Auto-generated method stub

		}

		@Override
		public void onPageSelected(int pos)
		{
			// TODO Auto-generated method stub
			currentItem = pos;
			for (int i = 0; i < dotViewsList.size(); i++)
			{
				if (i == pos)
				{
					((View) dotViewsList.get(pos))
							.setBackgroundColor(0xff673ab7);
				} else
				{
					((View) dotViewsList.get(i)).setBackgroundColor(0xffffffff);
				}
			}
		}

	}

	/**
	 * ����ImageView��Դ�������ڴ�
	 * 
	 */
	public void destoryBitmaps()
	{

		for (int i = 0; i < image_counts; i++)
		{
			ImageView imageView = imageViewsList.get(i);
			Drawable drawable = imageView.getDrawable();
			if (drawable != null)
			{
				// ���drawable��view������
				drawable.setCallback(null);
			}
		}
	}

	private class SlideShowTask implements Runnable
	{

		@Override
		public void run()
		{
			// TODO Auto-generated method stub
			synchronized (viewPager)
			{
				currentItem = (currentItem + 1) % imageViewsList.size();
				handler.obtainMessage().sendToTarget();
			}
		}

	}

	public int getSelected_color()
	{
		return selected_color;
	}

	public void setSelected_color(int selected_color)
	{
		this.selected_color = selected_color;
	}

	public int getUnselected_color()
	{
		return unselected_color;
	}

	public void setUnselected_color(int unselected_color)
	{
		this.unselected_color = unselected_color;
	}

	public int getImage_counts()
	{
		return image_counts;
	}

	public void setImage_counts(int image_counts)
	{
		this.image_counts = image_counts;
	}

	public int getTime_interval()
	{
		return time_interval;
	}

	public void setTime_interval(int time_interval)
	{
		this.time_interval = time_interval;
	}

	public boolean isAutoPlay()
	{
		return isAutoPlay;
	}

	public void setAutoPlay(boolean isAutoPlay)
	{
		this.isAutoPlay = isAutoPlay;
		if (isAutoPlay)
		{
			startPlay();
		} else
		{
			stopPlay();
		}
	}

	public List<String> getImageUrls()
	{
		return imageUrls;
	}

	public void setImageUrls(List<String> imageUrls)
	{
		this.imageUrls = imageUrls;
		initData();
	}
	
	
	
	public List<CartoonJsonBean> getCartoonBeans()
	{
		return cartoonBeans;
	}

	public void setCartoonBeans(List<CartoonJsonBean> cartoonBeans)
	{
		this.cartoonBeans = cartoonBeans;
	}
	

	public OnHotCartoonListener getOnHotCartoonListener()
	{
		return onHotCartoonListener;
	}

	public void setOnHotCartoonListener(OnHotCartoonListener onHotCartoonListener)
	{
		this.onHotCartoonListener = onHotCartoonListener;
	}

	public interface OnHotCartoonListener{
		public void onClicked(CartoonJsonBean cartoonBean);
	}

}
