package com.louji.bookshelf;

import java.io.IOException;

import android.R.integer;
import android.app.Activity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Window;
import android.view.WindowManager;

import com.louji.adapter.ScanViewAdapter;
import com.louji.base.R;
import com.louji.widgets.ScanView;

/**
 * 下载后阅读
 * 
 * @author 盛月茂
 *
 */
public class ReadBookActivity extends Activity
{
	ScanView scanview;
	ScanViewAdapter adapter;
	int screenWidth ;
	int screenHeight;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.activity_readbook);

		//获取屏幕尺寸
		getScreenSize();

		//打开书籍
		openbook();

	}

	private void openbook()
	{
		String filePath = getIntent().getExtras().getString("filePath");

		scanview = (ScanView) findViewById(R.id.scanview);

		try
		{
			adapter = new ScanViewAdapter(this, filePath, screenWidth,
					screenHeight);
			adapter.openbook(filePath);
			scanview.setAdapter(adapter);
		} catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void getScreenSize()
	{
		DisplayMetrics dm = new DisplayMetrics();
		dm = this.getResources().getDisplayMetrics();
		screenWidth = dm.widthPixels;
		screenHeight = dm.heightPixels;
	}

}
