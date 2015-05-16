package com.louji.httputil;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.HttpUriRequest;

import android.content.Context;
import android.util.Log;

import com.louji.http.AsyncHttpClient;
import com.louji.http.RangeFileAsyncHttpResponseHandler;
import com.louji.http.RequestHandle;
import com.louji.http.ResponseHandlerInterface;

public class RangeResponse extends GetNet
{

	public static final String CONTENT_LENGTH = "Content-Length";
	public static final String ACCEPT_RANGES = "Accept-Ranges";
	public static final int CHUNK_SIZE = 10240;

	private File file;
	private long fileSize = -1;

	public RangeResponse(Context context)
	{
		super(context);
		// TODO Auto-generated constructor stub
	}

	public void onCreate()
	{
		try
		{
			// Temporary file to host the URL's downloaded contents.
			file = File.createTempFile("temp_", "_handled",
					context.getCacheDir());
		} catch (IOException e)
		{
			Log.e(LOG_TAG, "Cannot create temporary file", e);
		}
	}

	public void onDestroy()
	{
		try
		{
			// Temporary file to host the URL's downloaded contents.
			file = File.createTempFile("temp_", "_handled",
					context.getCacheDir());
		} catch (IOException e)
		{
			Log.e(LOG_TAG, "Cannot create temporary file", e);
		}
	}

	@Override
	public String getDefaultURL()
	{
		// TODO Auto-generated method stub
		return "http://upload.wikimedia.org/wikipedia/commons/f/fa/Geysers_on_Mars.jpg";
	}

	@Override
	public RequestHandle executeSample(AsyncHttpClient client, String URL,
			Header[] headers, HttpEntity entity,
			ResponseHandlerInterface responseHandler)
	{

		if (fileSize > 0)
		{
			return client.get(context, URL, headers, null, responseHandler);
		} else
		{
			return client.head(context, URL, headers, null, responseHandler);
		}

	}

	public void sendNextRangeRequest(String url, String headersRaw,
			String bodyStr)
	{
		if (file.length() < fileSize)
		{
			onRun(url, headersRaw, bodyStr);
		}
	}

	@Override
	public List<RequestHandle> getRequestHandles()
	{
		// TODO Auto-generated method stub
		return super.getRequestHandles();
	}

	@Override
	public ResponseHandlerInterface getResponseHandler()
	{
		// TODO Auto-generated method stub
		return new RangeFileAsyncHttpResponseHandler(file)
		{

			@Override
			public void onSuccess(int statusCode, Header[] headers, File file)
			{
				// TODO Auto-generated method stub

			}

			@Override
			public void onFailure(int statusCode, Header[] headers,
					Throwable throwable, File file)
			{
				// TODO Auto-generated method stub

			}

			@Override
			public void updateRequestHeaders(HttpUriRequest uriRequest)
			{
				// TODO Auto-generated method stub
				super.updateRequestHeaders(uriRequest);
			}

		};
	}

}
