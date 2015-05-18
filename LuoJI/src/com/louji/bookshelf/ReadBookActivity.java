package com.louji.bookshelf;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.louji.adapter.ScanViewAdapter;
import com.louji.base.R;
import com.louji.widgets.ScanView;

public class ReadBookActivity extends Activity
{
	ScanView scanview;
	ScanViewAdapter adapter;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.activity_main);
		

		DisplayMetrics dm = new DisplayMetrics();
		dm = this.getResources().getDisplayMetrics();
		int screenWidth = dm.widthPixels;
		int screenHeight = dm.heightPixels;

		//Â·¾¶
		String filePath = getIntent().getExtras().getString("filePath");

		scanview = (ScanView) findViewById(R.id.scanview);		

		try
		{
			adapter = new ScanViewAdapter(this,  filePath, screenWidth,
					screenHeight);
			adapter.openbook(filePath);
			scanview.setAdapter(adapter);
		} catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}


}
