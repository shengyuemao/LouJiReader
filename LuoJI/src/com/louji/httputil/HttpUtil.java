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
	
	private static AsyncHttpClient client = new AsyncHttpClient(); // 实例话对象
	static {
		client.setTimeout(11000); // 设置链接超时，如果不设置，默认为10s
	}

	public static void get(String urlString, AsyncHttpResponseHandler res) // 用一个完整url获取一个string对象
	{
		client.get(urlString, res);
	}

	public static void get(String urlString, RequestParams params,
			AsyncHttpResponseHandler res) // url里面带参数
	{
		client.get(urlString, params, res);
	}

	public static void get(String urlString, JsonHttpResponseHandler res) // 不带参数，获取json对象或者数组
	{
		client.get(urlString, res);
	}

	public static void get(String urlString, RequestParams params,
			JsonHttpResponseHandler res) // 带参数，获取json对象或者数组
	{
		client.get(urlString, params, res);
	}

	public static void get(String uString, BinaryHttpResponseHandler bHandler) // 下载数据使用，会返回byte数据
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
















