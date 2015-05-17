package com.louji.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

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

	public static void writeFiletoSdcard(String filePath, byte[] buffer)
			throws IOException
	{
		File file = new File(filePath);
		FileOutputStream fileOutputStream = new FileOutputStream(file);
		fileOutputStream.write(buffer);
		fileOutputStream.close();
	}

}
