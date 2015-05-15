package com.louji.httputil;

import org.apache.http.Header;
import org.apache.http.HttpEntity;

import android.content.Context;

import com.louji.http.AsyncHttpClient;
import com.louji.http.RequestHandle;
import com.louji.http.ResponseHandlerInterface;

public class FileNet extends BaseHttpRequest {

	ResponseHandlerInterface responseHandlerInterface;
	
	public FileNet(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}
	
	 public void setResponseHandlerInterface(
			ResponseHandlerInterface responseHandlerInterface) {
		this.responseHandlerInterface = responseHandlerInterface;
	}
	@Override
	public ResponseHandlerInterface getResponseHandler() {
		// TODO Auto-generated method stub
		if (responseHandlerInterface == null) {
			return null;
		}
		return responseHandlerInterface;
				
	}

	@Override
	public String getDefaultURL() {
		// TODO Auto-generat3ed method stub
		return "";
	}

	@Override
	public boolean isRequestHeadersAllowed() {
		// TODO Auto-generated method stub
		return true;
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
		// TODO Auto-generated method stub
		return client.get(context, URL, headers, null, responseHandler);

	}

}
