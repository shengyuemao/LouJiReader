package com.louji.cartoon;

import com.louji.base.R;

import android.os.Bundle;
import android.view.Window;

public class CartoonChapterActivity extends BaseActivity
{
@Override
protected void onCreate(Bundle savedInstanceState)
{
	// TODO Auto-generated method stub
	super.onCreate(savedInstanceState);
	requestWindowFeature(Window.FEATURE_NO_TITLE);
	setContentView(R.layout.activity_cartoon);
}
}
