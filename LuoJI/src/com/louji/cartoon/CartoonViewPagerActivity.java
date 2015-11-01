package com.louji.cartoon;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import com.louji.adapter.CartoonAdapter;
import com.louji.base.R;
import com.louji.contacts.Contacts;
import com.louji.util.FileUtil;
import com.louji.widgets.CartoonLinearLayout;
import com.louji.widgets.PhotoView;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.view.Window;

public class CartoonViewPagerActivity extends BaseActivity
{

	private ViewPager viewPager;
	private CartoonLinearLayout cartoonLinearLayout;
	private List<PhotoView> matrixImageViews;
	private List<String> imageurls;
	private CartoonAdapter cartoonAdapter;

	private LinkedList<String> imageList;
	private Map<String, String> imagePosition;

	

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);

		setContentView(R.layout.activity_viewpager_cartoon);

		viewPager = (ViewPager) findViewById(R.id.pager_cartoon);
		cartoonLinearLayout = (CartoonLinearLayout) findViewById(
				R.id.scroll_cartoon);
		initData();
		netLoadImages();

		// initViewPagerUI();
		// initListViewUI();
		initScrollViewUI();
	}

	private void initData()
	{
		matrixImageViews = new ArrayList<PhotoView>();
		imageList = new LinkedList<String>();
		imageurls = new ArrayList<String>();
		imagePosition = new HashMap<String, String>();
	}

	private void initScrollViewUI()
	{
		viewPager.setVisibility(View.GONE);
		cartoonLinearLayout.setCartoonListAdapter(matrixImageViews);
	}

	public void initViewPagerUI()
	{
		viewPager.setVisibility(View.VISIBLE);
		cartoonAdapter = new CartoonAdapter(getApplicationContext(),
				matrixImageViews);
		viewPager.setAdapter(cartoonAdapter);
		viewPager.setCurrentItem(0);

		viewPager.setOnPageChangeListener(new OnPageChangeListener()
		{

			@Override
			public void onPageSelected(int position)
			{
				// TODO Auto-generated method stub
				imagePosition.clear();
				imagePosition.put("positionId", String.valueOf(position));

			}

			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2)
			{
				// TODO Auto-generated method stub

			}

			@Override
			public void onPageScrollStateChanged(int arg0)
			{
				// TODO Auto-generated method stub

			}
		});
	}

	/**
	 * 加载网络文件夹下的所有图片
	 */
	private void netLoadImages()
	{
		List<String> title1s = new ArrayList<String>();
		List<String> title2s = new ArrayList<String>();
		List<String> picnames = new ArrayList<String>();
		// String picPath = getNetPicPath();
		for (int i = 0; i < 52; i++)
		{
			DecimalFormat df = new DecimalFormat("000");
			String str = df.format(i + 1);
			String url = Contacts.BASE_CARTOON_URL + "chapter1/" + str + ".jpg";
			imageList.add(url);
			imageurls.add(url);

			String[] strs = url.split("/");

			title1s.add(strs[3]);
			title2s.add(strs[4]);
			picnames.add(strs[5]);

			PhotoView matrixImageView = new PhotoView(getApplicationContext());
			matrixImageView.setTag(url);
			matrixImageViews.add(matrixImageView);
		}

	}

	
	/**
	 * 根据intent获得漫画图片的网络路径
	 */
	public String getNetPicPath()
	{
		String picPath = "";
		Bundle bundle = this.getIntent().getExtras();
		if (bundle != null && bundle.size() > 0)
		{
			picPath = bundle.getString("picUrl");

		} else
		{
			picPath = FileUtil.getFileRead(Contacts.SHOWHISTORY);
		}
		return picPath;
	}

	/**
	 * 设置当前图片的位置
	 */
	public int getPicPosition(String picPath, String[] imagesArray)
	{
		int position = 0;
		for (int i = 0; i < imagesArray.length; i++)
		{
			if (picPath.equals(imagesArray[i]))
			{
				position = i;
				break;
			}
		}
		return position;
	}
}
