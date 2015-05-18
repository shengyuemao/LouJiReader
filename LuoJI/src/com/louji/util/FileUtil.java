package com.louji.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.Buffer;

import android.content.Context;
import android.os.Environment;

public class FileUtil
{

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

	public static byte[] readFileFromSdcard(File file) throws IOException
	{
		FileInputStream fileInputStream = new FileInputStream(file);
		int length = fileInputStream.available();
		byte[] buffer = new byte[length];
		fileInputStream.read(buffer);
		fileInputStream.close();
		return buffer;
	}

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

	public static String writeFiletoSdcard(Context context, byte[] buffer)
			throws IOException
	{
		File file = getRandomCacheFile(context);

		FileOutputStream fileOutputStream = new FileOutputStream(file);

		fileOutputStream.write(buffer);
		fileOutputStream.close();
		return file.getAbsolutePath();
	}

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
