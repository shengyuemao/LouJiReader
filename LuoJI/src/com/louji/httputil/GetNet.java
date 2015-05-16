package com.louji.httputil;

import org.apache.http.Header;
import org.apache.http.HttpEntity;

import android.content.Context;

import com.louji.http.AsyncHttpClient;
import com.louji.http.AsyncHttpResponseHandler;
import com.louji.http.RequestHandle;
import com.louji.http.ResponseHandlerInterface;

public class GetNet extends BaseHttpRequest{

	public GetNet(Context context) {
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
				
			}
			
			@Override
			public void onFailure(int statusCode, Header[] headers,
					byte[] responseBody, Throwable error) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onRetry(int retryNo) {
				// TODO Auto-generated method stub
				super.onRetry(retryNo);
			}
			
		};
	}

	@Override
	public String getDefaultURL() {
		// TODO Auto-generated method stub
		return "https://httpbin.org/get";
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
