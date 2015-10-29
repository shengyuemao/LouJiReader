package com.louji.cartoon;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

import com.louji.base.R;
import com.louji.contacts.Contacts;
import com.louji.util.FileUtil;
import com.louji.util.Logger;
import com.louji.util.NetImageLoader;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;
/**
 * 该Activity主要实现网络在线阅读漫画
 * 
 * @author Administrator
 *
 */
public class CartoonActivity extends BaseActivity
{

	private ImageView imageView; // 漫画显示

	private ImageButton lastPage;
	private ImageButton nextPage;

	private Bitmap bmap = null;
	private int disWidth;
	private int disHeight;

	private LinkedList<String> imageList;// 漫画列表
	private Map<String, String> imagePosition;// 漫画当前位置
	private int imageIndex;

	private String[] imageArray;// 图片集合

	private boolean iszoom = true;
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_cartoon);

		lastPage = (ImageButton) findViewById(R.id.cartoon_lastPage);
		nextPage = (ImageButton) findViewById(R.id.cartoon_nextPage);
		imageView = (ImageView) findViewById(R.id.cartoon_imageView);

		lastPage.setOnClickListener(lastPageButton);
		nextPage.setOnClickListener(nextPageButton);

		initData();// 初始化数据

		getDisplayMetrics();
		netLoadImages();// 加载图片列表

		initLoadImages();
	}

	private void initData()
	{
		imageList = new LinkedList<String>();
		imagePosition = new HashMap<String, String>();
	}

	/**
	 * 初始化图片信息
	 */
	public void initLoadImages()
	{
		setImageView(0);

	}

	/**
	 * 获取手机分辨率，根据大小设置高和宽
	 */
	public void getDisplayMetrics()
	{
		DisplayMetrics dm = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(dm);
		disWidth = dm.widthPixels;
		disHeight = dm.heightPixels;

	}

	/**
	 * 加载网络文件夹下的所有图片
	 */
	private void netLoadImages()
	{
		String picPath = getNetPicPath();
		for (int i = 0; i < 52; i++)
		{
			DecimalFormat df = new DecimalFormat("000");
			String str = df.format(i + 1);
			String url = Contacts.BASE_CARTOON_URL + "chapter1/" + str + ".jpg";
			Logger.i(url);
			imageList.add(url);
		}

		imageArray = imageList.toArray(new String[imageList.size()]);
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
	 * 根据intent获得漫画图片的网络路径
	 */
	public String getNetPicPathJpeg()
	{
		String picPath = "";
		Bundle bundle = this.getIntent().getExtras();
		if (bundle != null && bundle.size() > 0)
		{
			picPath = bundle.getString("picUrlJpeg");

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

	/**
	 * 设置当前图片大小
	 */
	public void setImageView(int index)
	{
		imagePosition = new HashMap<String, String>();
		imagePosition.clear();
		imagePosition.put("positionId", String.valueOf(index));

		Logger.i(imageArray[index] + "this is one");
		NetImageLoader imageLoader = new NetImageLoader(
				getApplicationContext());
		imageLoader.displayImage(imageArray[index] + "", imageView);
		FileUtil.resetBitmap();// 重置图片属性参数
	}
	// setImageView方法的重载,用于显示bitmap
	public void setImageView(Bitmap bitmap)
	{
		imageView.setImageBitmap(bitmap);
	}
	// 图片不存在信息
	public void showNoPicMsg()
	{
		Toast.makeText(this, "没有图片", Toast.LENGTH_SHORT).show();
	}

	// 信息提示
	public void showMsg(int id)
	{
		Toast.makeText(this, id, Toast.LENGTH_SHORT).show();
	}

	// 信息提示的重载
	public void showMsg(String str)
	{
		Toast.makeText(this, str, Toast.LENGTH_SHORT).show();
	}
	// lastPageButton的监听
	public View.OnClickListener lastPageButton = new View.OnClickListener()
	{

		public void onClick(View v)
		{
			getArrayAtBitmap("left");
		}
	};
	// nextPageButton的监听
	public View.OnClickListener nextPageButton = new View.OnClickListener()
	{

		public void onClick(View v)
		{
			getArrayAtBitmap("right");
		}
	};
	// 页面跳转的方法
	public void getArrayAtBitmap(String str)
	{
		if (imageList != null && imageList.size() > 0)
		{
			int position = Integer.parseInt(imagePosition.get("positionId"));
			if ("left".equals(str))
			{
				if (position >= 1)
				{
					imageIndex = position - 1;
					setImageView(imageIndex);
					showMsg((imageIndex + 1) + "/" + imageArray.length);
				} else
				{
					showMsg(R.string.cartoon_pageFirst);
				}
			}
			if ("right".equals(str))
			{
				if (position < imageArray.length - 1)
				{
					imageIndex = position + 1;
					setImageView(imageIndex);
					showMsg((imageIndex + 1) + "/" + imageArray.length);
				} else if (position == imageArray.length - 1)
				{
					showMsg(R.string.cartoon_pageEnd);
				}
			}
		} else
		{
			showNoPicMsg();
		}
	}
	
}
