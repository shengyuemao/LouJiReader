package com.louji.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Map;

import com.louji.contacts.Contacts;

import android.content.Context;
import android.os.Environment;
import android.util.Log;

/**
 * 文件工具类
 * 
 * @author 盛月茂
 *
 */
public class FileUtil
{
	/**
	 * SD卡是否有用
	 * 
	 * @return
	 */
	public static boolean hasSdcard()
	{
		if (Environment.getExternalStorageState()
				.equals(Environment.MEDIA_MOUNTED))
		{
			return true;
		} else
		{
			return false;
		}
	}

	/**
	 * 从SD卡中读取数据
	 * 
	 * @param file
	 * @return
	 * @throws IOException
	 */
	public static byte[] readFileFromSdcard(File file) throws IOException
	{
		FileInputStream fileInputStream = new FileInputStream(file);
		int length = fileInputStream.available();
		byte[] buffer = new byte[length];
		fileInputStream.read(buffer);
		fileInputStream.close();
		return buffer;
	}

	/**
	 * 按长度从文件中读取数据
	 * 
	 * @param file
	 * @param length
	 * @return
	 * @throws IOException
	 */
	public static String readFileFromSdcard(File file, int length)
			throws IOException
	{
		FileInputStream fileInputStream = new FileInputStream(file);
		byte[] buffer = new byte[length];
		fileInputStream.read(buffer);
		String string = new String(buffer, "gb2312");
		fileInputStream.close();
		return string;
	}

	/**
	 * 按长度从文件中读取数据
	 * 
	 * @param file
	 * @param length
	 * @param count
	 * @return
	 * @throws IOException
	 */
	public static String readFileFromSdcard(File file, int length, int count)
			throws IOException
	{
		FileInputStream fileInputStream = new FileInputStream(file);
		byte[] buffer = new byte[fileInputStream.available()];
		fileInputStream.read(buffer, count * length, length);
		String string = new String(buffer, "gb2312");
		fileInputStream.close();
		return string;
	}

	/**
	 * 写文件到应用缓存文件夹中
	 * 
	 * @param context
	 * @param buffer
	 * @return
	 * @throws IOException
	 */
	public static String writeFiletoSdcard(Context context, byte[] buffer)
			throws IOException
	{
		File file = getRandomCacheFile(context);

		FileOutputStream fileOutputStream = new FileOutputStream(file);

		fileOutputStream.write(buffer);
		fileOutputStream.close();
		return file.getAbsolutePath();
	}

	/**
	 * 创建缓存文件
	 * 
	 * @param context
	 * @return
	 */
	public static File getRandomCacheFile(Context context)
	{
		File dir = context.getCacheDir();
		if (dir == null)
		{
			dir = context.getFilesDir();
		}
		return new File(dir, "sample-" + System.currentTimeMillis() + ".txt");
	}

	/**
	 * 删除文件
	 * 
	 * @param filePath
	 * @return
	 */
	public static boolean deleteRandonCacheFile(String filePath)
	{
		if (filePath != null)
		{
			File dir = new File(filePath);
			return dir.delete();
		} else
		{
			return false;
		}
	}

	/**
	 * 以下为Cartoon相关的数据字段，
	 */
	public static String _sdCardPath = getSDCardPath();
	public static String _rootTempPath = _sdCardPath + Contacts.TEMPPATH;
	private final static String TAG = "Utils";
	public static int bmpWidth = 0;
	public static int bmpHeight = 0;
	public static double zoomBigscale;
	public static double zoomSmallscale;
	public static float scaleWidth = 1;
	public static float scaleHeight = 1;
	public static float scale = 1;
	public static int ScaleAngle = 0;
	public static int ScaleTimes = 0;
	public static String _bookMarks = Contacts.BOOKMARKS;

	/**
	 * 判断SD卡是否存在,如果存在则继续加载内容,同时创建文件夹cartoonReader/temp/
	 */
	public static boolean createFile()
	{
		if (_sdCardPath != null)
		{
			String filePath = _rootTempPath;
			File file = new File(filePath);
			if (!file.exists())
			{
				file.mkdirs();
			}
			file = null; // 释放文件对象
			return true;
		}
		return false;
	}

	// 获取SDcard的方法
	public static String getSDCardPath()
	{
		if (Environment.getExternalStorageState()
				.equals(Environment.MEDIA_MOUNTED))
		{
			File sdCard = Environment.getExternalStorageDirectory();
			String path = sdCard.getPath();
			return path;
		}
		return null;
	}

	// 读取txt中保存的信息
	public static String getFileRead(String filePath)
	{
		String content = "";
		String path = _rootTempPath;
		String line = "";
		File file = null;
		FileReader fr = null;
		BufferedReader bfr = null;

		if (_sdCardPath != null)
		{
			try
			{
				path += filePath;
				file = new File(path);
				if (file.exists())
				{
					fr = new FileReader(file);
					bfr = new BufferedReader(fr);
					while ((line = bfr.readLine()) != null)
					{
						content += line.trim();
					}
				}
			} catch (Exception e)
			{
				Log.i(TAG, e.getMessage());
			} finally
			{
				try
				{
					if (bfr != null)
					{
						bfr.close();
					}
					if (fr != null)
					{
						fr.close();
					}
				} catch (Exception e)
				{
					Log.i(TAG, e.getMessage());
				}
			}
		}
		return content;
	}
	/**
	 * 重置缩放参数
	 */
	public static void resetBitmap()
	{
		zoomBigscale = 1.25;
		zoomSmallscale = 0.8;
		bmpWidth = 0;
		bmpHeight = 0;
		ScaleAngle = 0;
		ScaleTimes = 1;
		scaleWidth = 1;
		scaleHeight = 1;
	}
	
	/**
	 * 获取图片的路径
	 * @param imagePosition
	 * @param imageList
	 * @return
	 */
		public static String getImagePath(Map<String, String> imagePosition, LinkedList<String> imageList) {
			String picPath = null;
			if(imagePosition != null && imagePosition.size() > 0) {
				int position = Integer.parseInt(imagePosition.get("positionId"));
				if(imageList != null && imageList.size() > 0) {
					picPath = imageList.get(position);
				}
			}
			return picPath;
		}
		
		/**
		 * 获取图片位置
		 * @param imagePosition
		 * @param imageList
		 * @return
		 */
		public static String getImagePagePosition(Map<String, String> imagePosition, LinkedList<String> imageList) {
			String pagePosition = null;
			if(imagePosition != null && imagePosition.size() > 0) {
				int page = Integer.parseInt(imagePosition.get("positionId")) + 1;
				int totalPages = imageList.size();
				StringBuffer sbf = new StringBuffer();
				sbf.append(String.valueOf(page));
				sbf.append("/");
				sbf.append(String.valueOf(totalPages));
				pagePosition = sbf.toString();
				sbf = null;
			}
			return pagePosition;
		}
}
