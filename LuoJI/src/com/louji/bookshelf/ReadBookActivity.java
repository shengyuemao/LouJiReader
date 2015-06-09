package com.louji.bookshelf;

import java.io.IOException;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnLongClickListener;
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
	int screenWidth;
	int screenHeight;
	View rootView;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		rootView = LayoutInflater.from(this).inflate(
				R.layout.activity_readbook, null);
		setContentView(rootView);
		
		// 获取屏幕尺寸
		getScreenSize();

		// 打开书籍
		openbook();

		rootView.setOnLongClickListener(new OnLongClickListener()
		{

			@Override
			public boolean onLongClick(View v)
			{
				return false;
			}
		});

		

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

	@SuppressWarnings("deprecation")
	private void getScreenSize()
	{
		screenWidth = getWindowManager().getDefaultDisplay().getWidth();
		screenHeight = getWindowManager().getDefaultDisplay().getHeight();
	}

}
