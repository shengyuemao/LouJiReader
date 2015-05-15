package com.louji.httputil;

import java.util.List;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.HttpContext;

import android.content.Context;

import com.louji.http.AsyncHttpClient;
import com.louji.http.AsyncHttpRequest;
import com.louji.http.RequestHandle;
import com.louji.http.ResponseHandlerInterface;

public interface BaseInterface {

	public List<RequestHandle> getRequestHandles();
	
	public void addRequestHandle(RequestHandle handle);
	
	public void onRun(String url,String headersRaw,String bodyStr);
	
	public void onCancel(Context context);
	
    public Header[] getRequestHeaders(String headersRaw);
    
    public HttpEntity getRequestEntity(String bodyStr);
    
	public void setAsyncHttpClient(AsyncHttpClient client);
	
	public AsyncHttpRequest getHttpRequest(DefaultHttpClient client,HttpContext httpContext,HttpUriRequest uriRequest,String contentType,ResponseHandlerInterface responseHandler,Context context);
	
	public ResponseHandlerInterface getResponseHandler();
	
	public String getDefaultURL();
	
	public String getDefaultHeaders();
	
	public boolean isRequestHeadersAllowed();
	
	public boolean isRequestBodyAllowed();
	
	public RequestHandle executeSample(AsyncHttpClient client,String URL,Header[] headers,HttpEntity entity,ResponseHandlerInterface responseHandler);
	
	
}
