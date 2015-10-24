package com.louji.cartoon;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Map;

import com.louji.base.R;
import com.louji.contacts.Contacts;
import com.louji.util.FileUtil;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.KeyEvent;

public class BaseActivity extends Activity
{

	protected static Map<String, String> imagePosition ;
	protected static LinkedList<String> imageList ;
	public static ArrayList<Activity> allActivity = new ArrayList<Activity>() ;
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		allActivity.add(this) ;
	}
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event)
	{
		// TODO Auto-generated method stub
		if (KeyEvent.KEYCODE_BACK == keyCode && event.getRepeatCount() == 0)
		{
			promptExit(this) ;
		}
		return super.onKeyDown(keyCode, event);
	}
	
	/**
	 * 退出
	 */
	public static void exitApp(Context context){
		for(Activity ac : allActivity){
			if(ac != null){
				ac.finish();
			}
		}
		saveReaderState() ;
		System.exit(0);
	}
	
	@Override
	protected void onSaveInstanceState(Bundle outState)
	{
		// TODO Auto-generated method stub
		super.onSaveInstanceState(outState);
		saveReaderState() ;
	}
	
	/**
	 * 保存当前阅读的漫画信息
	 * 
	 */
	public static void saveReaderState(){
		try{
			String picPath = FileUtil.getImagePath(imagePosition, imageList);
			if(picPath != null){
				FileUtil.saveFile(Contacts.SHOWHISTORY, picPath, false) ;
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	/**
	 * 退出
	 */
	public static void promptExit(final Context context){
		AlertDialog.Builder dialog = new AlertDialog.Builder(context) ;
		dialog.setTitle(R.string.cartoon_logout_title) ;
		dialog.setMessage(R.string.cartoon_logout_body) ;
		dialog.setPositiveButton(R.string.cartoon_logout_submit, new DialogInterface.OnClickListener()
		{
			
			@Override
			public void onClick(DialogInterface dialog, int which)
			{
				// TODO Auto-generated method stub
				exitApp(context);
			}
		});
		dialog.setNegativeButton(R.string.cartoon_logout_cancel, new DialogInterface.OnClickListener()
		{
			
			@Override
			public void onClick(DialogInterface dialog, int which)
			{
				// TODO Auto-generated method stub
				dialog.dismiss();
			}
		});
		dialog.show() ;
	}
}
