package com.louji.httputil;

import org.apache.http.Header;
import org.apache.http.HttpEntity;

import android.content.Context;

import com.louji.http.AsyncHttpClient;
import com.louji.http.BinaryHttpResponseHandler;
import com.louji.http.RequestHandle;
import com.louji.http.ResponseHandlerInterface;

public class Binary extends BaseHttpRequest
{
	private ResponseHandlerInterface responseHandlerInterface;

	public Binary(Context context)
	{
		super(context);
		// TODO Auto-generated constructor stub
	}
	
	public void setResponseHandlerInterface(
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

	@Override
	public String getDefaultURL()
	{
		// TODO Auto-generated method stub
		return "https://httpbin.org/gzip";
	}

	@Override
	public boolean isRequestHeadersAllowed()
	{
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isRequestBodyAllowed()
	{
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public RequestHandle executeSample(AsyncHttpClient client, String URL,
			Header[] headers, HttpEntity entity,
			ResponseHandlerInterface responseHandler)
	{
		// TODO Auto-generated method stub
		return client.get(context, URL, headers, null, responseHandler);
	}

}
