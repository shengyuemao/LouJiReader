package com.louji.base;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;

import com.louji.slidingmenu.MenuActivity;

public class LoujiActivity extends ActionBarActivity {

	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		Intent intent;
		intent = new Intent();
		intent.setClass(this, MenuActivity.class);
		startActivity(intent);
		
		finish();//½áÊøµ±Ç°activity
		
		
	}

}
