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

	private CustomTxtView tvMain; // ������ؼ����Զ���
	private SearchTxtDlg searchDlg;// �����Ի����Զ���
	String strSelection = ""; // �û�ѡ����ַ���
	String strTxt = ""; // ������ʾ���ı��ַ���
	String strPath = ""; // �������ļ�·��
	int position = 0; // ��ǰ�Ķ�λ�ã�ȡһ�е�����
	int markPos = 0; // ��ǩλ��

	final int BUFFER_SIZE = 1024 * 3; // ûʱ���ˣ���ʱ�Ȳ������ļ�������-_-||

	final int SCROLL_STEP = 1; // �Զ������Ĳ���
	final int BEGIN_SCROLL = 1; // ��ʼ����
	final int END_SCROLL = 2; // ��ֹ����
	final int STOP_SCROLL = 3; // ��������

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
			/* �ж���Ϣ */
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
					// ����������
					tvMain.scrollTo(0, tvMain.getScrollY() + SCROLL_STEP);
					autoScrollHandle.sendEmptyMessageDelayed(BEGIN_SCROLL, 100);
				}
				break;
			case END_SCROLL:
				// �Ѿ��������ײ�
				autoScrollHandle.removeMessages(STOP_SCROLL);
				autoScrollHandle.removeMessages(BEGIN_SCROLL);
				break;
			case STOP_SCROLL:
				// �û��жϹ���
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
					Toast.makeText(getActivity(), "û���ҵ��ؼ���", Toast.LENGTH_LONG)
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

					char[] bufTmp = new char[128]; // �������ֳ����Ƿ񳬳����ŷ�Χ�ж�
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

		// ���ļ�
		try
		{
			Bundle b = getActivity().getIntent().getExtras();
			String str = b.getString("FILE_PATH");
			strPath = str; // ����һ�ݸ���

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
					Settings.PREF_TAG_FONT_COLOR, "��ɫ");
			String bgColor = spSetting.getString(
					Settings.PREF_TAG_BACKGROUND_COLOR, "��ɫ");
			float fontSize = spSetting.getFloat(Settings.PREF_TAG_FONT_SIZE,
					25.0f);
			float scrBrightness = spSetting.getFloat(
					Settings.PREF_TAG_SCREEN_BRIGHTNESS, 1.0f);

			// �е��Ť��Ӧ���и��õĽ������
			if (bgColor == "��ɫ")
				tvMain.setBackgroundColor(Color.WHITE);
			else if (bgColor == "��ɫ")
				tvMain.setBackgroundColor(Color.BLACK);
			else if (bgColor == "��ɫ")
				tvMain.setBackgroundColor(Color.RED);
			else if (bgColor == "��ɫ")
				tvMain.setBackgroundColor(Color.GREEN);
			else if (bgColor == "��ɫ")
				tvMain.setBackgroundColor(Color.BLUE);

			if (fontColor == "��ɫ")
				tvMain.setTextColor(Color.WHITE);
			else if (fontColor == "��ɫ")
				tvMain.setTextColor(Color.BLACK);
			else if (fontColor == "��ɫ")
				tvMain.setTextColor(Color.RED);
			else if (fontColor == "��ɫ")
				tvMain.setTextColor(Color.GREEN);
			else if (fontColor == "��ɫ")
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

		// ���ð�ť�¼�������
		Button BtnPrePage = (Button) view.findViewById(R.id.viewtxt_pre_button);
		BtnPrePage.setOnClickListener(this);
		Button BtnNextPage = (Button) view
				.findViewById(R.id.viewtxt_next_button);
		BtnNextPage.setOnClickListener(this);
		Button BtnAutoScroll = (Button) view
				.findViewById(R.id.viewtxt_auto_scroll_button);
		BtnAutoScroll.setOnClickListener(this);
	}

	// ���»ص��������
	@Override
	public void onResume()
	{
		super.onResume();
		Layout l = tvMain.getLayout();
		if (null != l)
		{
			// �ص��ϴιۿ���λ��
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
				// BtnAutoScroll.setText("ֹͣ����");
			} else
			{
				autoScrollHandle.sendEmptyMessage(STOP_SCROLL);
				// BtnAutoScroll.setText("�Զ�����");
			}
			break;

		default:
			break;
		}
	}

	// ���˵�

	// ���˵�����¼�
	@Override
	public boolean onOptionsItemSelected(MenuItem item)
	{
		super.onOptionsItemSelected(item);
		switch (item.getItemId())
		{
		case MENU_BOOKMARK:
			// ȥ����ǩ����Activity
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
	 * ȥ����ǩλ��
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
			// ȥ����ǩλ��
			int line = l.getLineForOffset(mark);
			float sy = l.getLineBottom(line);
			tvMain.scrollTo(0, (int) sy);
			markPos = mark;
			hasBookMark = true;
			Log.e("REALPOS_RES", "REAL_POS " + mark);
		}
	}
}
