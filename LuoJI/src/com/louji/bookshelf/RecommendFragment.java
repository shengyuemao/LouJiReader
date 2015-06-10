package com.louji.bookshelf;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.Header;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import com.gc.materialdesign.views.ButtonFlat;
import com.gc.materialdesign.views.Slider;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.louji.adapter.BookMarkAdapter;
import com.louji.adapter.BookMarkAdapter.OnDownLoadListener;
import com.louji.adapter.BookMarkAdapter.OnLineReaderListener;
import com.louji.base.R;
import com.louji.bean.BookBean;
import com.louji.http.BinaryHttpResponseHandler;
import com.louji.http.JsonHttpResponseHandler;
import com.louji.httputil.Binary;
import com.louji.httputil.Canstact;
import com.louji.httputil.GetNet;
import com.louji.jsonbean.BookJsonBean;
import com.louji.util.FileUtil;
import com.yalantis.taurus.PullToRefreshView;
import com.yalantis.taurus.PullToRefreshView.OnRefreshListener;

/**
 * 推荐页
 * 
 * @author 盛月茂
 *
 */
@SuppressLint("InflateParams")
public class RecommendFragment extends Fragment
{

	public static final int REFRESH_DELAY = 4000;// 刷新间隔

	private PullToRefreshView pullToRefreshView;// 列表外层
	private ListView listView;// 列表

	private List<BookBean> bookBeans;

	private BookMarkAdapter bookMarkAdapter;

	@Override
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
	{
		View view = inflater.inflate(R.layout.fragment_recommend, null);

		initData();

		initView(view);

		return view;
	}

	/**
	 * 初始化数据
	 */
	private void initData()
	{
		bookBeans = new ArrayList<BookBean>(); // 初始化bookBeans;

		GetNet getNet = new GetNet(getActivity());

		getNet.setResponseHandlerInterface(new JsonHttpResponseHandler("utf-8")
		{

			@Override
			public void onSuccess(int statusCode, Header[] headers,
					JSONObject response)
			{
				// TODO Auto-generated method stub
				super.onSuccess(statusCode, headers, response);
				Toast.makeText(getActivity(), response.toString(),
						Toast.LENGTH_LONG).show();

				Gson gson = new Gson();
				List<BookJsonBean> bookJsonBeans = gson.fromJson(
						response.toString(),
						new TypeToken<List<BookJsonBean>>()
						{
						}.getType());
				for (int i = 0; i < bookJsonBeans.size(); i++)
				{
					BookBean bookBean = new BookBean();
					bookBean.setBookUrl(bookJsonBeans.get(i).getBookurl());
					bookBean.setBookTitle(bookJsonBeans.get(i).getBooktitle());
					bookBean.setBookInfo(bookJsonBeans.get(i).getBookcontent());
					bookBean.setImageUrl(bookJsonBeans.get(i).getBookimage());
					bookBeans.add(bookBean);
				}

			}

			@Override
			public void onFailure(int statusCode, Header[] headers,
					Throwable throwable, JSONObject errorResponse)
			{

				for (int i = 0; i < 20; i++)
				{
					BookBean bookBean = new BookBean();
					bookBean.setBookTitle("海贼王");
					bookBean.setBookInfo("《ONE PIECE》（海贼王、航海王）动画，于1999年10月20日在日本富士电视台开始播放，至今仍在播放中....");
					bookBean.setBookUrl(Canstact.URL);
					bookBeans.add(bookBean);
				}
				super.onFailure(statusCode, headers, throwable, errorResponse);
			}

		});

		getNet.onRun(Canstact.URL, "", "");

	}

	/**
	 * 初始化控件
	 * 
	 * @param view
	 */
	public void initView(View view)
	{
		pullToRefreshView = (PullToRefreshView) view
				.findViewById(R.id.recommendfragment_refresh);
		pullToRefreshView.setOnRefreshListener(new RefreshListener());

		listView = (ListView) view
				.findViewById(R.id.recommendfragment_list_view);
		bookMarkAdapter = new BookMarkAdapter(getActivity(), bookBeans);
		listView.setAdapter(bookMarkAdapter);
		bookMarkAdapter.setOnLineReaderListener(new OnLineReader());
		bookMarkAdapter.setOnDownLoadListener(new OnDownLoad());

	}

	/**
	 * 定义下载事件
	 * 
	 * @author 盛月茂
	 * @since 2015年6月9号
	 *
	 */
	private class OnDownLoad implements OnDownLoadListener
	{

		@Override
		public void download(BookBean bookBean, final View v)
		{

			Binary binary = new Binary(getActivity());
			binary.setResponseHandlerInterface(new BinaryHttp(v));

			binary.onRun(Canstact.URL, "", "");

		}
	}

	/**
	 * 在线阅读事件
	 * 
	 * @author 盛月茂
	 *
	 */
	private class OnLineReader implements OnLineReaderListener
	{
		@Override
		public void onReader(BookBean bookBean)
		{
			// 跳转到阅读界面
			Bundle bundle = new Bundle();

			Intent intent = new Intent();
			intent.setClass(getActivity(), ReadActivity.class);
			bundle.putString("filePath", bookBean.getBookUrl());
			intent.putExtras(bundle);
			getActivity().startActivity(intent);

		}
	}

	/**
	 * 刷新事件
	 * 
	 * @author 盛月茂
	 *
	 */
	private class RefreshListener implements OnRefreshListener
	{
		@Override
		public void onRefresh()
		{
			// 测试刷新
			pullToRefreshView.postDelayed(new Runnable()
			{

				@Override
				public void run()
				{
					pullToRefreshView.setRefreshing(false);

				}
			}, REFRESH_DELAY);

		}
	}

	/**
	 * 字节流下载事件
	 * 
	 * @author Administrator
	 *
	 */
	private class BinaryHttp extends BinaryHttpResponseHandler
	{

		private final View v;
		private Slider slider;
		String filePath;

		private BinaryHttp(View v)
		{
			this.v = v;
			this.slider = (Slider) v.getRootView().findViewById(
					R.id.recommendfragment_item_book_slider);
			//
		}

		@Override
		public void onStart()
		{

			super.onStart();
		}

		@Override
		public void onProgress(int bytesWritten, int totalSize)
		{
			this.slider.setVisibility(View.VISIBLE);
			slider.setMax(totalSize);
			slider.setValue(bytesWritten);
			super.onProgress(bytesWritten, totalSize);

		}

		@Override
		public void onSuccess(int statusCode, Header[] headers,
				byte[] binaryData)
		{

			try
			{

				filePath = FileUtil
						.writeFiletoSdcard(getActivity(), binaryData);

				v.setOnClickListener(new OnChangeListener());
				((ButtonFlat) v).setText("阅读");
				slider.setVisibility(View.GONE);

			} catch (IOException e)
			{
				Toast.makeText(getActivity(), "没法写入", Toast.LENGTH_LONG).show();
				e.printStackTrace();
			}

		}

		@Override
		public void onFailure(int statusCode, Header[] headers,
				byte[] binaryData, Throwable error)
		{
			slider.setVisibility(View.GONE);

		}

		/**
		 * 阅读按钮事件
		 * 
		 * @author Administrator
		 *
		 */
		private class OnChangeListener implements OnClickListener
		{
			@Override
			public void onClick(View v)
			{
				Bundle bundle = new Bundle();
				bundle.putString("filePath", filePath);

				Intent intent = new Intent();
				intent.setClass(getActivity(), ReadBookActivity.class);
				intent.putExtras(bundle);
				getActivity().startActivity(intent);

			}
		}
	}

}
