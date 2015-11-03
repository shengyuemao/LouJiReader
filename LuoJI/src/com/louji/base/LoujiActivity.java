package com.louji.base;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.widget.Toast;
import io.vov.vitamio.LibsChecker;

import com.louji.slidingmenu.MenuActivity;

/**
 * 欢迎页面
 * 
 * @author 盛月茂
 *
 */
public class LoujiActivity extends ActionBarActivity
{

	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);

		Intent intent;
		intent = new Intent();
		intent.setClass(this, MenuActivity.class);
		startActivity(intent);

		finish();// 结束当前activity

	}

}
