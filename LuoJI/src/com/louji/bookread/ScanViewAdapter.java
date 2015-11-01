package com.louji.bookread;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.RandomAccessFile;
import java.io.UnsupportedEncodingException;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.util.Vector;

import com.louji.adapter.PageAdapter;
import com.louji.base.R;
import com.louji.util.Util;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Paint;
import android.graphics.Paint.Align;
import android.provider.Contacts;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

@SuppressLint("InflateParams")
public class ScanViewAdapter extends PageAdapter
{
	Context context;
	private MappedByteBuffer m_mbBuf = null;

	private int m_mbBufLen = 0;
	private int m_mbBufBegin = 0;
	private int m_mbBufEnd = 0;
	private String encode = "gb2312";
	private int mWidth;
	private int mHeight;

	private Vector<String> m_lines = new Vector<String>();

	private int m_fontSize = 20;
	private int marginWidth = 4;
	private int marginHeight = 40;

	private int mLineCount;

	private float mVisibleWidth;
	private float mVisibleHeight;

	private Paint mPaint;

	private int islast = 0;
	private int isfirst = 0;

	public ScanViewAdapter(Context context, String filePath, int w, int h)
	{
		this.context = context;

		initScreen(w, h);// 初始化屏幕显示

	}

	private void initScreen(int w, int h)
	{
		mWidth = w;
		mHeight = h;
		mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
		mPaint.setTextAlign(Align.LEFT);
		mPaint.setTextSize(m_fontSize);
		mVisibleWidth = mWidth - marginWidth * 2;
		mVisibleHeight = mHeight - marginHeight * 2;
		mLineCount = (int) (mVisibleHeight / Util.dip2px(context, m_fontSize));

	}

	/**
	 * 读取上一页数据
	 * 
	 * @param nFromPos
	 * @return
	 */
	protected byte[] readParagraphBack(int nFromPos)
	{
		int nEnd = nFromPos;
		int i;
		byte b0;

		i = nEnd - 1;
		while (i > 0)
		{
			b0 = m_mbBuf.get(i);
			if (b0 == 0x0a && i != nEnd - 1)
			{
				i++;
				break;
			}
			i--;
		}

		if (i < 0)
			i = 0;
		int nParaSize = nEnd - i;
		int j;
		byte[] buf = new byte[nParaSize];
		for (j = 0; j < nParaSize; j++)
		{
			buf[j] = m_mbBuf.get(i + j);
		}
		paragraph = buf.length;
		return buf;
	}

	int paragraph = 1;

	/**
	 * 读取下一页数据
	 * 
	 * @param nFromPos
	 * @return
	 */
	protected byte[] readParagraphForward(int nFromPos)
	{
		int nStart = nFromPos;
		int i = nStart;
		byte b0;

		while (i < m_mbBufLen)
		{
			b0 = m_mbBuf.get(i++);
			if (b0 == 0x0a)
			{
				break;
			}
		}

		int nParaSize = i - nStart;
		byte[] buf = new byte[nParaSize];
		for (i = 0; i < nParaSize; i++)
		{
			buf[i] = m_mbBuf.get(nFromPos + i);
		}
		paragraph = buf.length;
		return buf;
	}

	/**
	 * 下一页数据获取
	 * 
	 * @return
	 */
	protected Vector<String> pageDown()
	{
		String strParagraph = "";
		Vector<String> lines = new Vector<String>();
		while (lines.size() < mLineCount && m_mbBufEnd < m_mbBufLen)
		{
			byte[] paraBuf = readParagraphForward(m_mbBufEnd);
			m_mbBufEnd += paraBuf.length;
			try
			{
				strParagraph = new String(paraBuf, encode);
			} catch (UnsupportedEncodingException e)
			{
				e.printStackTrace();
			}
			String strReturn = "";
			if (strParagraph.indexOf("\r\n") != -1)
			{
				strReturn = "\r\n";
				strParagraph = strParagraph.replaceAll("\r\n", "");
			} else if (strParagraph.indexOf("\n") != -1)
			{
				strReturn = "\n";
				strParagraph = strParagraph.replaceAll("\n", "");
			}

			if (strParagraph.length() == 0)
			{
				lines.add(strParagraph);
			}
			while (strParagraph.length() > 0)
			{
				int nSize;
				if (strParagraph.length() > 17)
				{
					nSize = mPaint.breakText(strParagraph, 0, 16, true,
							mVisibleWidth, null);
				} else
				{
					nSize = mPaint.breakText(strParagraph, true, mVisibleWidth,
							null);
				}
				lines.add(strParagraph.substring(0, nSize));
				strParagraph = strParagraph.substring(nSize);
				if (lines.size() >= mLineCount)
				{
					break;
				}
			}

			if (strParagraph.length() != 0)
			{
				try
				{
					m_mbBufEnd -= (strParagraph + strReturn)
							.getBytes(encode).length;
				} catch (UnsupportedEncodingException e)
				{
					e.printStackTrace();
				}
			}
		}
		return lines;
	}

	/**
	 * 上一页数据获取
	 */
	protected void pageUp()
	{
		if (m_mbBufBegin < 0)
			m_mbBufBegin = 0;
		Vector<String> lines = new Vector<String>();
		String strParagraph = "";
		while (lines.size() < mLineCount && m_mbBufBegin > 0)
		{
			Vector<String> paraLines = new Vector<String>();
			byte[] paraBuf = readParagraphBack(m_mbBufBegin);
			m_mbBufBegin -= paraBuf.length;
			try
			{
				strParagraph = new String(paraBuf, encode);
			} catch (UnsupportedEncodingException e)
			{
				e.printStackTrace();
			}
			strParagraph = strParagraph.replaceAll("\r\n", "");
			strParagraph = strParagraph.replaceAll("\n", "");

			if (strParagraph.length() == 0)
			{
				paraLines.add(strParagraph);
			}
			while (strParagraph.length() > 0)
			{
				int nSize;
				if (strParagraph.length() > 17)
				{
					nSize = mPaint.breakText(strParagraph, 0, 16, true,
							mVisibleWidth, null);
				} else
				{
					nSize = mPaint.breakText(strParagraph, true, mVisibleWidth,
							null);
				}
				paraLines.add(strParagraph.substring(0, nSize));
				strParagraph = strParagraph.substring(nSize);
			}
			lines.addAll(0, paraLines);
		}

		int begin = m_mbBufBegin;
		while (lines.size() > mLineCount)
		{
			try
			{
				m_mbBufBegin += lines.get(0).getBytes(encode).length;
				begin += lines.get(0).getBytes(encode).length;
				lines.remove(0);
			} catch (UnsupportedEncodingException e)
			{
				e.printStackTrace();
			}
		}
		m_mbBufEnd = begin;
		return;
	}

	/**
	 * 上一页显示
	 * 
	 * @throws IOException
	 */
	public void prePage() throws IOException
	{
		if (m_mbBufBegin <= 0)
		{
			m_mbBufBegin = 0;
			return;
		} else
			m_lines.clear();
		pageUp();
	}

	/**
	 * 下一页显示
	 * 
	 * @throws IOException
	 */
	public void nextPage() throws IOException
	{
		if (m_mbBufEnd >= m_mbBufLen)
		{
			return;
		}
		m_lines.clear();
		m_lines = pageDown();
		m_mbBufBegin = m_mbBufEnd;
	}

	/**
	 * 添加内容
	 */
	public void addContent(View view)
	{
		TextView content = (TextView) view.findViewById(R.id.content);
		content.setTextSize(20);

		if (m_mbBufEnd > m_mbBufLen)
			return;

		m_lines.clear();
		m_mbBufBegin = m_mbBufEnd;
		m_lines = pageDown();
		if (m_lines.size() > 0)
		{
			String contents = "";
			for (String strLine : m_lines)
			{
				contents += strLine;
			}
			content.setText(contents);
		}

	}

	/**
	 * 打开书籍文件 调用时必须执行
	 * 
	 * @param strFilePath
	 *            文件目录
	 * @throws IOException
	 */
	@SuppressWarnings("resource")
	public void openbook(String strFilePath) throws IOException
	{

		File book_file = new File(strFilePath);		
		long lLen = book_file.length();
		m_mbBufLen = (int) lLen;
		m_mbBuf = new RandomAccessFile(book_file, "r").getChannel()
				.map(FileChannel.MapMode.READ_ONLY, 0, lLen);
	}

	

	public int getM_mbBufLen()
	{
		return m_mbBufLen;
	}

	public void setM_mbBufLen(int m_mbBufLen)
	{
		this.m_mbBufLen = m_mbBufLen;
	}

	public int getM_mbBufBegin()
	{
		return m_mbBufBegin;
	}

	public void setM_mbBufBegin(int m_mbBufBegin)
	{
		this.m_mbBufBegin = m_mbBufBegin;
	}

	public int getM_mbBufEnd()
	{
		return m_mbBufEnd;
	}

	public void setM_mbBufEnd(int m_mbBufEnd)
	{
		this.m_mbBufEnd = m_mbBufEnd;
	}

	public int getMaxValue()
	{
		return (int) m_mbBufLen;
	}

	public View getView()
	{
		View view = LayoutInflater.from(context).inflate(R.layout.layout_page,
				null);
		return view;
	}

	@Override
	public int getCount()
	{
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void addContent(View view, int position)
	{
		// TODO Auto-generated method stub

	}
}
