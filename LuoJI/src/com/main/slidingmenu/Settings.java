// 熊猫设置的Actvity
// Leo @ 2010/10/05

package com.main.slidingmenu;

import android.app.Activity;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.Spinner;
import android.widget.TextView;

import com.main.luoji.R;

public class Settings extends Activity {
	final int MENU_SAVE_AND_RETURN = 1;
	
	public final static String PANDA_READER_PREF = "cn.com.nd.PandaReaderLight";	// PREF的标识
	
	public final static String PREF_TAG_FONT_SIZE = "tagFontSize";					// 字体大小的标识
	public final static String PREF_TAG_FONT_COLOR = "tagFontColor";				// 字体颜色的标识
	public final static String PREF_TAG_BACKGROUND_COLOR = "tagBgColor";			// 背景颜色的标识
	public final static String PREF_TAG_SCREEN_BRIGHTNESS = "tagScrBrightness";		// 屏幕亮度的标识
	
	SharedPreferences spSetting;													// 用户设置
	
	String fontColor;
	String bgColor;
	float fontSize;
	float scrBrightness;
	
	TextView tvSettingPrev;
	
	Spinner spFontColor;
	Spinner spBgColor;
	SeekBar sbFontSize;
	SeekBar sbScrBrightness;
	
	ArrayAdapter<String> adapterFontColor;
	ArrayAdapter<String> adapterBgColor;
	OnSeekBarChangeListener osbl;
	
	@Override  
    public void onCreate(Bundle savedInstanceState) { 
        super.onCreate(savedInstanceState);  
        setContentView(R.layout.settings);  
        
        tvSettingPrev = (TextView) findViewById(R.id.setting_textview_setting_prev);
        sbFontSize = (SeekBar) findViewById(R.id.setting_seekbar_font_size);
        sbScrBrightness = (SeekBar) findViewById(R.id.setting_seekbar_scr_brightness);
        spFontColor = (Spinner) findViewById(R.id.setting_spinner_font_color);
        spBgColor = (Spinner) findViewById(R.id.setting_spinner_bg_color);
        
        initFromPref();
		
    	   	
    	// 字体颜色选择器
		adapterFontColor = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_item);  
		adapterFontColor.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);  
		adapterFontColor.add("白色");  
		adapterFontColor.add("黑色");  
		adapterFontColor.add("红色");  
		adapterFontColor.add("绿色");  
		adapterFontColor.add("蓝色");  
		
		spFontColor.setAdapter(adapterFontColor);   
		spFontColor.setOnItemSelectedListener(new OnItemSelectedListener() {  

			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				Spinner sp = (Spinner) parent;  
				fontColor = sp.getSelectedItem().toString();
		    	if (fontColor == "白色") 
		    		tvSettingPrev.setTextColor(Color.WHITE);
		    	else if (fontColor == "黑色") 
		    		tvSettingPrev.setTextColor(Color.BLACK);
		    	else if (fontColor == "红色") 	
		    		tvSettingPrev.setTextColor(Color.RED);
		    	else if (fontColor == "绿色") 
		    		tvSettingPrev.setTextColor(Color.GREEN);
		    	else if (fontColor == "蓝色") 
		    		tvSettingPrev.setTextColor(Color.BLUE);
		    	
				fontColor = spFontColor.getSelectedItem().toString();
			}

			public void onNothingSelected(AdapterView<?> parent) {
				// TODO Auto-generated method stub
				
			}  }); 
		
		// 背景颜色选择器
		adapterBgColor = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_item);  
		adapterBgColor.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);  
		adapterBgColor.add("白色");  
		adapterBgColor.add("黑色");  
		adapterBgColor.add("红色");  
		adapterBgColor.add("绿色");  
		adapterBgColor.add("蓝色");  
		
		spBgColor.setAdapter(adapterBgColor);   
		spBgColor.setOnItemSelectedListener(new OnItemSelectedListener() {  

			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				Spinner sp = (Spinner) parent;  
				bgColor = sp.getSelectedItem().toString();
		    	if (bgColor == "白色")
		    		tvSettingPrev.setBackgroundColor(Color.WHITE);
		    	else if (bgColor == "黑色") 
		    		tvSettingPrev.setBackgroundColor(Color.BLACK);
		    	else if (bgColor == "红色") 	
		    		tvSettingPrev.setBackgroundColor(Color.RED);
		    	else if (bgColor == "绿色") 
		    		tvSettingPrev.setBackgroundColor(Color.GREEN);
		    	else if (bgColor == "蓝色") 
		    		tvSettingPrev.setBackgroundColor(Color.BLUE);
		    	
				bgColor = spBgColor.getSelectedItem().toString();
			}

			public void onNothingSelected(AdapterView<?> parent) {
				// TODO Auto-generated method stub
				
			}  }); 
		
		// 文字背景颜色预览代码，此段待斟酌
		if (fontColor == "白色") {
    		spFontColor.setSelection(0);
    		tvSettingPrev.setTextColor(Color.WHITE);
    	}
    	else if (fontColor == "黑色") {
    		spFontColor.setSelection(1);
    		tvSettingPrev.setTextColor(Color.BLACK);
    	} 
    	else if (fontColor == "红色") {
    		spFontColor.setSelection(2);
    		tvSettingPrev.setTextColor(Color.RED);
    	} 	
    	else if (fontColor == "绿色") {
    		spFontColor.setSelection(3);
    		tvSettingPrev.setTextColor(Color.GREEN);
    	} 
    	else if (fontColor == "蓝色") {
    		spFontColor.setSelection(4);
    		tvSettingPrev.setTextColor(Color.BLUE);
    	} 
    			
    	if (bgColor == "白色") {
    		spBgColor.setSelection(0);
    		tvSettingPrev.setBackgroundColor(Color.WHITE);
    	}
    	else if (bgColor == "黑色") {
    		spBgColor.setSelection(1);
    		tvSettingPrev.setBackgroundColor(Color.BLACK);
    	} 
    	else if (bgColor == "红色") {
    		spBgColor.setSelection(2);
    		tvSettingPrev.setBackgroundColor(Color.RED);
    	}	
    	else if (bgColor == "绿色") {
    		spBgColor.setSelection(3);
    		tvSettingPrev.setBackgroundColor(Color.GREEN);
    	}
    	else if (bgColor == "蓝色") {
    		spBgColor.setSelection(4);
    		tvSettingPrev.setBackgroundColor(Color.BLUE);
    	}
    	
    	tvSettingPrev.setTextSize(fontSize);
    	
    	// 预览滚动条???
    	tvSettingPrev.setMovementMethod(ScrollingMovementMethod.getInstance());
    	
    	WindowManager.LayoutParams lp = getWindow().getAttributes();  
    	lp.screenBrightness = scrBrightness;  
    	getWindow().setAttributes(lp); 
    	
    	// 滑动条改变的监听器
		osbl = new OnSeekBarChangeListener() {
			
			// 拖动中
			public void onProgressChanged(SeekBar seekBar, int progress,
					boolean fromUser) {
				// TODO Auto-generated method stub
				switch (seekBar.getId()) {
				case R.id.setting_seekbar_font_size:
					tvSettingPrev.setTextSize((progress+1) * 5.0f);
					fontSize = (progress+1) * 5.0f;
					break;
				case R.id.setting_seekbar_scr_brightness:
			    	WindowManager.LayoutParams lp = getWindow().getAttributes();  
			    	lp.screenBrightness = progress / 10.0f;  
			    	getWindow().setAttributes(lp); 
			    	scrBrightness = progress / 10.0f;
					break;
				}
			}

			// 开始拖动
			public void onStartTrackingTouch(SeekBar seekBar) {
				// TODO Auto-generated method stub
				switch (seekBar.getId()) {
				case R.id.setting_seekbar_font_size:
					break;
				case R.id.setting_seekbar_scr_brightness:
					break;
				}
			}

			// 结束拖动
			public void onStopTrackingTouch(SeekBar seekBar) {
				// TODO Auto-generated method stub
				switch (seekBar.getId()) {
				case R.id.setting_seekbar_font_size:
					break;
				case R.id.setting_seekbar_scr_brightness:
					break;
				}
			}
			
		};
		
		sbFontSize.setOnSeekBarChangeListener(osbl);
		sbScrBrightness.setOnSeekBarChangeListener(osbl);
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		menu.add(0, MENU_SAVE_AND_RETURN, 0, "保存并返回")
		.setIcon(android.R.drawable.ic_menu_save);
		
		return true;
	}
	
	// 主菜单点击事件
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		super.onOptionsItemSelected(item);
		switch (item.getItemId()) {
		case MENU_SAVE_AND_RETURN:
			saveToPref();
			this.finish();
			break;
		}
		
		return false;
	}
	
    private void initFromPref() {
    	spSetting = getSharedPreferences(PANDA_READER_PREF, 0);
    	fontColor = spSetting.getString(PREF_TAG_FONT_COLOR, "白色");
    	bgColor = spSetting.getString(PREF_TAG_BACKGROUND_COLOR, "黑色");
    	fontSize = spSetting.getFloat(PREF_TAG_FONT_SIZE, 25.0f);
    	scrBrightness = spSetting.getFloat(PREF_TAG_SCREEN_BRIGHTNESS, 1.0f);
    }
    
    private void saveToPref() {
    	spSetting = getSharedPreferences(PANDA_READER_PREF, 0);
    	SharedPreferences.Editor ed = spSetting.edit();
    	
    	ed.putString(PREF_TAG_FONT_COLOR, fontColor);
    	ed.putString(PREF_TAG_BACKGROUND_COLOR, bgColor);
    	ed.putFloat(PREF_TAG_FONT_SIZE, fontSize);
    	ed.putFloat(PREF_TAG_SCREEN_BRIGHTNESS, scrBrightness);
    	ed.commit();
    }
}