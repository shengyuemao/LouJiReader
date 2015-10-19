package com.louji.bookread;

import java.io.IOException;

import org.apache.http.Header;

import android.app.Activity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Window;
import android.view.WindowManager;

import com.louji.base.R;
import com.louji.http.BinaryHttpResponseHandler;
import com.louji.httputil.Binary;
import com.louji.util.FileUtil;

/**
 * 在线阅读
 * 
 * @author Administrator
 *
 */
public class ReadActivity extends Activity
{

	ScanView scanview;
	ScanViewAdapter adapter;
	int screenWidth;
	int screenHeight;
	String filePath;

	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);

		setContentView(R.layout.activity_readbook);

		// 获取屏幕尺寸
		getScreenSize();

		String url = getIntent().getExtras().getString("filePath");
		Binary binary = new Binary(this);
		binary.setResponseHandlerInterface(new BinaryHttp());
		binary.onRun(url, "", "");
	}

	private void openbook(String filePath)
	{

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

	private class BinaryHttp extends BinaryHttpResponseHandler
	{
		@Override
		public void onSuccess(int statusCode, Header[] headers,
				byte[] binaryData)
		{
			try
			{
				filePath = FileUtil.writeFiletoSdcard(getApplicationContext(),
						binaryData);
				openbook(filePath);
			} catch (IOException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

		@Override
		public void onFailure(int statusCode, Header[] headers,
				byte[] binaryData, Throwable error)
		{
			// TODO Auto-generated method stub

		}
	}

	@Override
	protected void onDestroy()
	{
		
		FileUtil.deleteRandonCacheFile(filePath);
		super.onDestroy();
	}

}
