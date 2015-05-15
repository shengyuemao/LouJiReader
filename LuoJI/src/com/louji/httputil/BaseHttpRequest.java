package com.louji.httputil;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicHeader;
import org.apache.http.protocol.HttpContext;

import android.content.Context;
import android.util.Log;

import com.louji.http.AsyncHttpClient;
import com.louji.http.AsyncHttpRequest;
import com.louji.http.RequestHandle;
import com.louji.http.ResponseHandlerInterface;

public abstract class BaseHttpRequest implements BaseInterface {

	public static final String LOG_TAG = "BaseHttpRequest";
	public Context context;
	
	public BaseHttpRequest(Context context) {
		// TODO Auto-generated constructor stub
		this.context = context;
	}
	
	public AsyncHttpClient asyncHttpClient = new AsyncHttpClient() {

		protected AsyncHttpRequest newAsyncHttpRequest(
				DefaultHttpClient client, HttpContext httpContext,
				HttpUriRequest uriRequest, String contentType,
				ResponseHandlerInterface responseHandler, Context context) {
			AsyncHttpRequest httpRequest = getHttpRequest(client, httpContext,
					uriRequest, contentType, responseHandler, context);

			return httpRequest == null ? super.newAsyncHttpRequest(client,
					httpContext, uriRequest, contentType, responseHandler,
					context) : httpRequest;

		};
	};

	private final List<RequestHandle> requestHandles = new LinkedList<RequestHandle>();

	protected static final String PROTOCOL_HTTP = "http://";
	protected static final String PROTOCOL_HTTPS = "https://";

	protected static String PROTOCOL = PROTOCOL_HTTPS;

	@Override
	public List<RequestHandle> getRequestHandles() {
		// TODO Auto-generated method stub
		return requestHandles;
	}

	@Override
	public void addRequestHandle(RequestHandle handle) {
		// TODO Auto-generated method stub
		if (null != handle) {
			requestHandles.add(handle);
		}
	}

	@Override
	public void onRun(String url, String headersRaw, String bodyStr) {
		// TODO Auto-generated method stub
		addRequestHandle(executeSample(getAsyncHttpClient(), getUrl(url),
				getRequestHeaders(headersRaw), getRequestEntity(bodyStr),
				getResponseHandler()));
	}

	@Override
	public void onCancel(Context context) {
		// TODO Auto-generated method stub
		asyncHttpClient.cancelRequests(context, true);
	}

	@Override
	public String getDefaultHeaders() {
		return null;
	}

	public List<Header> getRequestHeadersList(String headersRaw) {
		List<Header> headers = new ArrayList<Header>();
		if (headersRaw != null && headersRaw.length() > 3) {
			String[] lines = headersRaw.split("\\r?\\n");
			for (String line : lines) {
				try {
					int equalSignPos = line.indexOf('=');
					if (1 > equalSignPos) {
						throw new IllegalArgumentException(
								"Wrong header format, may be 'Key=Value' only");
					}

					String headerName = line.substring(0, equalSignPos).trim();
					String headerValue = line.substring(1 + equalSignPos)
							.trim();
					Log.d(LOG_TAG, String.format("Added header: [%s:%s]",
							headerName, headerValue));

					headers.add(new BasicHeader(headerName, headerValue));
				} catch (Throwable t) {
					Log.e(LOG_TAG, "Not a valid header line: " + line, t);
				}
			}
		}
		return headers;
	}

	@Override
	public Header[] getRequestHeaders(String res) {
		// TODO Auto-generated method stub
		List<Header> headers = getRequestHeadersList(res);

		return headers.toArray(new Header[headers.size()]);
	}

	@Override
	public HttpEntity getRequestEntity(String bodyStr) {
		// TODO Auto-generated method stub
		String bodyText;
		if (isRequestBodyAllowed() && (bodyText = getBodyText(bodyStr)) != null) {
			try {
				return new StringEntity(bodyText);
			} catch (UnsupportedEncodingException e) {
				Log.e(LOG_TAG, "cannot create String entity", e);
			}
		}
		return null;
	}

	public AsyncHttpClient getAsyncHttpClient() {
		return this.asyncHttpClient;
	}

	public String getUrl(String url) {
		if (url == null) {
			return getDefaultURL();
		}
		return url;
	}

	public String getBodyText(String defaultText) {
		return defaultText;
	}

	@Override
	public void setAsyncHttpClient(AsyncHttpClient client) {
		// TODO Auto-generated method stub
		this.asyncHttpClient = client;
	}

	@Override
	public AsyncHttpRequest getHttpRequest(DefaultHttpClient client,
			HttpContext httpContext, HttpUriRequest uriRequest,
			String contentType, ResponseHandlerInterface responseHandler,
			Context context) {
		// TODO Auto-generated method stub
		return null;
	}

}
