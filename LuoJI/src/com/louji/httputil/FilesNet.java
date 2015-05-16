package com.louji.httputil;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.Random;

import org.apache.http.Header;
import org.apache.http.HttpEntity;

import android.content.Context;
import android.util.Log;

import com.louji.http.AsyncHttpClient;
import com.louji.http.RequestHandle;
import com.louji.http.RequestParams;
import com.louji.http.ResponseHandlerInterface;

public class FilesNet extends Post {

	public FilesNet(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean isRequestBodyAllowed() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public RequestHandle executeSample(AsyncHttpClient client, String URL,
			Header[] headers, HttpEntity entity,
			ResponseHandlerInterface responseHandler) {

		try {
			RequestParams params = new RequestParams();
			final String contentType = RequestParams.APPLICATION_OCTET_STREAM;
			params.put("fileOne", createTempFile("fileOne", 1020), contentType,
					"fileOne");
			params.put("fileTwo", createTempFile("fileTwo", 1030), contentType,
					"fileTwo");
			params.put("fileThree", createTempFile("fileThree", 1040),
					contentType, "fileThree");
			params.put("fileFour", createTempFile("fileFour", 1050),
					contentType, "fileFour");
			params.put("fileFive", createTempFile("fileFive", 1060),
					contentType, "fileFive");
			params.setHttpEntityIsRepeatable(true);
			params.setUseJsonStreamer(false);
			return client.post(context, URL, params, responseHandler);
		} catch (FileNotFoundException fnfException) {
			Log.e(LOG_TAG, "executeSample failed with FileNotFoundException",
					fnfException);
		}

		return null;
	}

	public File createTempFile(String namePart, int byteSize) {
		try {
			File f = File.createTempFile(namePart, "_handled",
					context.getCacheDir());
			FileOutputStream fos = new FileOutputStream(f);
			Random r = new Random();
			byte[] buffer = new byte[byteSize];
			r.nextBytes(buffer);
			fos.write(buffer);
			fos.flush();
			fos.close();
			return f;
		} catch (Throwable t) {
			Log.e(LOG_TAG, "createTempFile failed", t);
		}
		return null;
	}

}
