package com.louji.httputil;

import java.io.File;
import java.io.FileNotFoundException;

import org.apache.http.HttpEntity;

import android.content.Context;

import com.louji.http.AsyncHttpClient;
import com.louji.http.AsyncHttpResponseHandler;
import com.louji.http.BinaryHttpResponseHandler;
import com.louji.http.JsonHttpResponseHandler;
import com.louji.http.RequestParams;

public class HttpUtil {
	
	private static AsyncHttpClient client = new AsyncHttpClient(); // ʵ��������
	static {
		client.setTimeout(11000); // �������ӳ�ʱ����������ã�Ĭ��Ϊ10s
	}

	public static void get(String urlString, AsyncHttpResponseHandler res) // ��һ������url��ȡһ��string����
	{
		client.get(urlString, res);
	}

	public static void get(String urlString, RequestParams params,
			AsyncHttpResponseHandler res) // url���������
	{
		client.get(urlString, params, res);
	}

	public static void get(String urlString, JsonHttpResponseHandler res) // ������������ȡjson�����������
	{
		client.get(urlString, res);
	}

	public static void get(String urlString, RequestParams params,
			JsonHttpResponseHandler res) // ����������ȡjson�����������
	{
		client.get(urlString, params, res);
	}

	public static void get(String uString, BinaryHttpResponseHandler bHandler) // ��������ʹ�ã��᷵��byte����
	{
		client.get(uString, bHandler);
	}

	public static void post(String url, RequestParams params,
			AsyncHttpResponseHandler responseHandler) {
		client.post(url, params, responseHandler);
	}
	
	public static void post(String url,RequestParams params, JsonHttpResponseHandler res ){
		client.post(url, params,res);		
	}
	
	public static void post(Context context ,String url,HttpEntity params,JsonHttpResponseHandler res){
		client.post(context, url, params, "application/x-www-form-urlencoded", res);
	}
	
	public static void post(Context context,String url,File pictureFile,AsyncHttpResponseHandler res){
		RequestParams params = new RequestParams();
		try{
			params.put("file", pictureFile,"application/octet-stream");
			client.post(url, params,res);
		}catch(FileNotFoundException e){
			
		}
	}

	public static AsyncHttpClient getClient() {
		return client;
	}
}
















