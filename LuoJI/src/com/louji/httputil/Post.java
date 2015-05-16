package com.louji.httputil;

import org.apache.http.Header;
import org.apache.http.HttpEntity;

import android.content.Context;

import com.louji.http.AsyncHttpClient;
import com.louji.http.AsyncHttpResponseHandler;
import com.louji.http.RequestHandle;
import com.louji.http.ResponseHandlerInterface;

public class Post  extends BaseHttpRequest{

	public Post(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	@Override
	public ResponseHandlerInterface getResponseHandler() {
		// TODO Auto-generated method stub
		return new AsyncHttpResponseHandler() {
			
			@Override
			public void onStart() {
				// TODO Auto-generated method stub
				super.onStart();
			}
			
			@Override
			public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
				// TODO Auto-generated method stub
				if(responseBody != null){
					
				}
			}
			
			@Override
			public void onFailure(int statusCode, Header[] headers,
					byte[] responseBody, Throwable error) {
				// TODO Auto-generated method stub
				if(responseBody != null){
					
				}
			}
		};
	}

	@Override
	public String getDefaultURL() {
		// TODO Auto-generated method stub
		return PROTOCOL + "httpbin.org/post";
	}

	@Override
	public boolean isRequestHeadersAllowed() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isRequestBodyAllowed() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public RequestHandle executeSample(AsyncHttpClient client, String URL,
			Header[] headers, HttpEntity entity,
			ResponseHandlerInterface responseHandler) {
		// TODO Auto-generated method stub
		return client.post(context, URL, headers, entity, null, responseHandler);
	}

}
