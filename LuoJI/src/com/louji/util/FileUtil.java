package com.louji.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import android.content.Context;
import android.os.Environment;

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
		if (Environment.getExternalStorageState().equals(
				Environment.MEDIA_MOUNTED))
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

}
