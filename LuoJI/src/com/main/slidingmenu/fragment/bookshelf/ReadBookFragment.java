package com.main.slidingmenu.fragment.bookshelf;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import android.content.DialogInterface;
import android.content.DialogInterface.OnDismissListener;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.text.Layout;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.method.ScrollingMovementMethod;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.main.luoji.R;
import com.main.slidingmenu.Settings;
import com.main.widgets.CustomTxtView;
import com.main.widgets.SearchTxtDlg;

public class ReadBookFragment extends Fragment implements OnClickListener
{

	public final int REQUEST_CODE_GOTO_BOOKMARK = 1;

	private CustomTxtView tvMain; // 主看书控件，自定义
	private SearchTxtDlg searchDlg;// 搜索对话框，自定义
	String strSelection = ""; // 用户选择的字符串
	String strTxt = ""; // 用于显示的文本字符串
	String strPath = ""; // 完整的文件路径
	int position = 0; // 当前阅读位置，取一行的行首
	int markPos = 0; // 书签位置

	final int BUFFER_SIZE = 1024 * 3; // 没时间了，暂时先不做大文件处理了-_-||

	final int SCROLL_STEP = 1; // 自动滚动的步长
	final int BEGIN_SCROLL = 1; // 开始滚屏
	final int END_SCROLL = 2; // 终止滚屏
	final int STOP_SCROLL = 3; // 结束滚屏

	final int MENU_BOOKMARK = Menu.FIRST;
	final int MENU_SEARCH = Menu.FIRST + 1;

	final int DIALOG_AFTER_SELECTION = 4;
	final int DIALOG_GET_SEARCH_KEY_WORD = 5;

	boolean isAutoScrolling = false;
	boolean isInSearching = false;
	boolean hasBookMark = false;

	Button BtnAutoScroll;

	Handler autoScrollHandle = new Handler()
	{
		@Override
		public void handleMessage(Message msg)
		{
			super.handleMessage(msg);
			/* 判断消息 */
			switch (msg.what)
			{
			case BEGIN_SCROLL:
				if (tvMain.getScrollY() >= tvMain.getLineCount()
						* tvMain.getLineHeight() - tvMain.getHeight())
				{
					tvMain.scrollTo(0,
							tvMain.getLineCount() * tvMain.getLineHeight()
									- tvMain.getHeight());
					autoScrollHandle.sendEmptyMessage(END_SCROLL);
				} else
				{
					// 按步长滚动
					tvMain.scrollTo(0, tvMain.getScrollY() + SCROLL_STEP);
					autoScrollHandle.sendEmptyMessageDelayed(BEGIN_SCROLL, 100);
				}
				break;
			case END_SCROLL:
				// 已经滚动到底部
				autoScrollHandle.removeMessages(STOP_SCROLL);
				autoScrollHandle.removeMessages(BEGIN_SCROLL);
				break;
			case STOP_SCROLL:
				// 用户中断滚屏
				autoScrollHandle.removeMessages(END_SCROLL);
				autoScrollHandle.removeMessages(BEGIN_SCROLL);
				break;
			default:
				break;
			}
		}
	};

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState)
	{
		View view = inflater.inflate(R.layout.readbook, null);
		// TODO Auto-generated method stub
		initView(view);
		return view;
	}

	private void initView(View view)
	{
		tvMain = (CustomTxtView) view.findViewById(R.id.viewtxt_main_view);
		BtnAutoScroll = (Button) view
				.findViewById(R.id.viewtxt_auto_scroll_button);

		searchDlg = new SearchTxtDlg(getActivity());
		searchDlg.setOnDismissListener(new OnDismissListener()
		{

			@Override
			public void onDismiss(DialogInterface dialog)
			{
				String keywords = searchDlg.getSearchKeyword();
				if (keywords.length() > 0)
				{
					isInSearching = true;
					SpannableStringBuilder style = new SpannableStringBuilder(
							strTxt);
					int beginFlag = 0;
					while (strTxt.indexOf(keywords, beginFlag) != -1)
					{
						int markStart = strTxt.indexOf(keywords, beginFlag);
						int markEnd = strTxt.indexOf(keywords, beginFlag)
								+ keywords.length();
						style.setSpan(new ForegroundColorSpan(Color.GRAY),
								markStart, markEnd,
								Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
						beginFlag = markEnd;
					}
					tvMain.setText(style);
				} else
				{
					Toast.makeText(getActivity(), "没有找到关键字", Toast.LENGTH_LONG)
							.show();
				}
			}
		});

		CustomTxtView.OnTouchListener viewTouch = new TextView.OnTouchListener()
		{

			@Override
			public boolean onTouch(View v, MotionEvent event)
			{
				// TODO Auto-generated method stub
				if (tvMain.getSelectionEnd() - tvMain.getSelectionStart() != 0
						&& tvMain.isInSelectMode()
						&& event.getAction() == MotionEvent.ACTION_UP)
				{

					char[] bufTmp = new char[128]; // 需做文字长度是否超出短信范围判断
					tvMain.getText().getChars(tvMain.getSelectionStart(),
							tvMain.getSelectionEnd(), bufTmp, 0);

					// strSelection = String.copyValueOf(bufTmp);
					strSelection = String.copyValueOf(
							bufTmp,
							0,
							tvMain.getSelectionEnd()
									- tvMain.getSelectionStart());
					getActivity().showDialog(DIALOG_AFTER_SELECTION);
				}

				return false;
			}

		};
		tvMain.setOnTouchListener(viewTouch);

		// 读文件
		try
		{
			Bundle b = getActivity().getIntent().getExtras();
			String str = b.getString("FILE_PATH");
			strPath = str; // 保存一份副本

			FileReader fr = new FileReader(str);
			String DecType = fr.getEncoding();

			char[] buf = new char[BUFFER_SIZE];
			try
			{
				fr.read(buf);
			} catch (IOException e)
			{
			}

			// byte[] bytes = new byte[buf.length];
			// String strDst = new String(bytes, 0, buf.length, DecType);
			strTxt = new String(buf);
			tvMain.setText(strTxt);

			SharedPreferences spSetting = getActivity().getSharedPreferences(
					Settings.PANDA_READER_PREF, 0);
			String fontColor = spSetting.getString(
					Settings.PREF_TAG_FONT_COLOR, "白色");
			String bgColor = spSetting.getString(
					Settings.PREF_TAG_BACKGROUND_COLOR, "黑色");
			float fontSize = spSetting.getFloat(Settings.PREF_TAG_FONT_SIZE,
					25.0f);
			float scrBrightness = spSetting.getFloat(
					Settings.PREF_TAG_SCREEN_BRIGHTNESS, 1.0f);

			// 有点别扭，应该有更好的解决方案
			if (bgColor == "白色")
				tvMain.setBackgroundColor(Color.WHITE);
			else if (bgColor == "黑色")
				tvMain.setBackgroundColor(Color.BLACK);
			else if (bgColor == "红色")
				tvMain.setBackgroundColor(Color.RED);
			else if (bgColor == "绿色")
				tvMain.setBackgroundColor(Color.GREEN);
			else if (bgColor == "蓝色")
				tvMain.setBackgroundColor(Color.BLUE);

			if (fontColor == "白色")
				tvMain.setTextColor(Color.WHITE);
			else if (fontColor == "黑色")
				tvMain.setTextColor(Color.BLACK);
			else if (fontColor == "红色")
				tvMain.setTextColor(Color.RED);
			else if (fontColor == "绿色")
				tvMain.setTextColor(Color.GREEN);
			else if (fontColor == "蓝色")
				tvMain.setTextColor(Color.BLUE);

			tvMain.setTextSize(fontSize);

			WindowManager.LayoutParams lp = getActivity().getWindow()
					.getAttributes();
			lp.screenBrightness = scrBrightness;
			getActivity().getWindow().setAttributes(lp);

			tvMain.setCursorVisible(false);
			tvMain.setMovementMethod(ScrollingMovementMethod.getInstance());
		} catch (FileNotFoundException e)
		{

		}

		// 设置按钮事件监听器
		Button BtnPrePage = (Button) view.findViewById(R.id.viewtxt_pre_button);
		BtnPrePage.setOnClickListener(this);
		Button BtnNextPage = (Button) view
				.findViewById(R.id.viewtxt_next_button);
		BtnNextPage.setOnClickListener(this);
		Button BtnAutoScroll = (Button) view
				.findViewById(R.id.viewtxt_auto_scroll_button);
		BtnAutoScroll.setOnClickListener(this);
	}

	// 重新回到看书界面
	@Override
	public void onResume()
	{
		super.onResume();
		Layout l = tvMain.getLayout();
		if (null != l)
		{
			// 回到上次观看的位置
			if (hasBookMark)
			{
				hasBookMark = false;
				position = markPos;
			} else
			{
				int line = l.getLineForOffset(position);
				float sy = l.getLineBottom(line);
				tvMain.scrollTo(0, (int) sy);
			}
			Log.e("REALPOS_RESUME", "REAL_POS " + position);
		}
	}

	@Override
	public void onClick(View v)
	{
		// TODO Auto-generated method stub
		switch (v.getId())
		{
		case R.id.viewtxt_pre_button:
			if (tvMain.getScrollY() <= tvMain.getHeight())
				tvMain.scrollTo(0, 0);
			else
				tvMain.scrollTo(0, tvMain.getScrollY() - tvMain.getHeight());

			Log.e("", "LINEHEIGHT = " + tvMain.getLineHeight());
			break;
		case R.id.viewtxt_next_button:
			if (tvMain.getScrollY() >= tvMain.getLineCount()
					* tvMain.getLineHeight() - tvMain.getHeight() * 2)
				tvMain.scrollTo(
						0,
						tvMain.getLineCount() * tvMain.getLineHeight()
								- tvMain.getHeight());
			else
				tvMain.scrollTo(0, tvMain.getScrollY() + tvMain.getHeight());

			Log.e("",
					"LINECOUNT*LINEHEIGHT = "
							+ (tvMain.getLineCount() * tvMain.getLineHeight() - tvMain
									.getHeight()));
			Log.e("", "SCROLLY = " + tvMain.getScrollY());
			Log.e("", "TVHEIGHT = " + tvMain.getHeight());
			break;
		case R.id.viewtxt_auto_scroll_button:
			isAutoScrolling = !isAutoScrolling;
			if (isAutoScrolling)
			{
				autoScrollHandle.sendEmptyMessage(BEGIN_SCROLL);
				// BtnAutoScroll.setText("停止滚屏");
			} else
			{
				autoScrollHandle.sendEmptyMessage(STOP_SCROLL);
				// BtnAutoScroll.setText("自动滚屏");
			}
			break;

		default:
			break;
		}
	}

	// 主菜单

	// 主菜单点击事件
	@Override
	public boolean onOptionsItemSelected(MenuItem item)
	{
		super.onOptionsItemSelected(item);
		switch (item.getItemId())
		{
		case MENU_BOOKMARK:
			// 去往书签管理Activity
			Intent i = new Intent(getActivity(), Menu.class);
			Bundle b = new Bundle();
			b.putString("BOOKNAME", strPath);
			Layout l = tvMain.getLayout();
			int line = l.getLineForVertical(tvMain.getScrollY());
			int off = l.getOffsetForHorizontal(line, 0);
			position = off;
			b.putInt("POSITION", position);
			Log.e("REALPOS_BEFORE_GO", "REAL_POS " + position);
			i.putExtras(b);
			startActivityForResult(i, REQUEST_CODE_GOTO_BOOKMARK);
			break;

		case MENU_SEARCH:
			if (isInSearching)
			{
				tvMain.setText(strTxt);
				isInSearching = false;
			} else
			{
				searchDlg.setDisplay();
			}
			break;
		default:
			break;
		}

		return false;
	}

	/**
	 * 去往书签位置
	 * 
	 * @param data
	 */
	public void BackToPosition(Intent data)
	{
		Bundle b = data.getExtras();
		int mark = b.getInt("POSITION");
		Layout l = tvMain.getLayout();
		if (null != l)
		{
			// 去往书签位置
			int line = l.getLineForOffset(mark);
			float sy = l.getLineBottom(line);
			tvMain.scrollTo(0, (int) sy);
			markPos = mark;
			hasBookMark = true;
			Log.e("REALPOS_RES", "REAL_POS " + mark);
		}
	}
}
