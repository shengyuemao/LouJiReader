package com.louji.httputil;

import java.io.File;
import java.util.List;

import org.apache.http.Header;
import org.apache.http.HttpEntity;

import android.content.Context;

import com.louji.http.AsyncHttpClient;
import com.louji.http.RequestHandle;
import com.louji.http.ResponseHandlerInterface;

public class RangeResponse extends GetNet
{

	public static final String CONTENT_LENGTH = "Content-Length";
	public static final String ACCEPT_RANGES = "Accept-Ranges";
	public static final int CHUNK_SIZE = 10240;

	private File file;
	private long fileSize = -1;

	private ResponseHandlerInterface responseHandlerInterface;

	public RangeResponse(Context context, File file, long fileSize)
	{
		super(context);
		this.file = file;
		this.fileSize = fileSize;
	}

	public long getFileSize()
	{
		return fileSize;
	}

	public void setFileSize(long fileSize)
	{
		this.fileSize = fileSize;
	}

	public void onDestroy()
	{

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

	public void setResponseHandlder(
			ResponseHandlerInterface responseHandlerInterface)
	{
		this.responseHandlerInterface = responseHandlerInterface;
	}

	@Override
	public ResponseHandlerInterface getResponseHandler()
	{
		// TODO Auto-generated method stub
		return responseHandlerInterface;
	}

}
