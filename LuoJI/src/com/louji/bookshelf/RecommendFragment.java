package com.louji.bookshelf;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.Header;
import org.json.JSONArray;
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
import com.louji.bookread.ReadActivity;
import com.louji.bookread.ReadBookActivity;
import com.louji.contacts.Contacts;
import com.louji.db.DatabaseFactory;
import com.louji.http.BinaryHttpResponseHandler;
import com.louji.http.JsonHttpResponseHandler;
import com.louji.httputil.Binary;
import com.louji.httputil.HttpUtil;
import com.louji.jsonbean.BookJsonBean;
import com.louji.util.FileUtil;
import com.louji.util.Logger;
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

	private com.louji.dbbean.BookBean bookBean;

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
		bookBean = new com.louji.dbbean.BookBean();

		HttpUtil.get(Contacts.RECOMMEND_URL, new JsonHttpResponseHandler()
		{

			@Override
			public void onSuccess(int statusCode, Header[] headers,
					JSONArray response)
			{
				// TODO Auto-generated method stub
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
					bookBean.setIsrecommend(bookJsonBeans.get(i)
							.getIsrecommend());
					bookBeans.add(bookBean);
				}

				super.onSuccess(statusCode, headers, response);
			}

			@Override
			public void onSuccess(int statusCode, Header[] headers,
					JSONObject response)
			{
				// TODO Auto-generated method stub
				super.onSuccess(statusCode, headers, response);

			}

			@Override
			public void onFailure(int statusCode, Header[] headers,
					Throwable throwable, JSONArray errorResponse)
			{

				super.onFailure(statusCode, headers, throwable, errorResponse);
			}

		});

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
		public void download(BookBean bookBean1, View v)
		{
			Logger.v(bookBean1.getBookUrl());

			bookBean.setBookcontent(bookBean1.getBookInfo());
			bookBean.setBookimage(bookBean1.getImageUrl());
			bookBean.setBooktitle(bookBean1.getBookTitle());
			bookBean.setBookurl(bookBean1.getBookUrl());
			bookBean.setIsrecommend(bookBean1.getIsrecommend());

			String[] contentType = new String[]
			{ "text/plain" };

			final Slider slider = (Slider) v.getRootView().findViewById(
					R.id.recommendfragment_item_book_slider);

			Binary binary = new Binary(getActivity());
			binary.setResponseHandlerInterface(BinaryHttp(v, contentType,
					slider));

			binary.onRun(bookBean1.getBookUrl(), "", "");
		}

		/**
		 * 字节流写入
		 * 
		 * @param v
		 * @param contentType
		 * @param slider
		 * @return
		 */
		private BinaryHttpResponseHandler BinaryHttp(final View v,
				String[] contentType, final Slider slider)
		{
			return new BinaryHttpResponseHandler(contentType)
			{
				String filePath;

				@Override
				public void onProgress(int bytesWritten, int totalSize)
				{
					slider.setVisibility(View.VISIBLE);
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

						filePath = FileUtil.writeFiletoSdcard(getActivity(),
								binaryData);

						/*Database<com.louji.dbbean.BookBean> bookDatabase = DatabaseFactory
								.bookData(getActivity());*/
						bookBean.setBooklocalpath(filePath);

						DatabaseFactory.bookData(getActivity()).save(bookBean, "think_louji_book");

						v.setOnClickListener(new OnChangeListener());
						((ButtonFlat) v).setText("阅读");
						slider.setVisibility(View.GONE);

					} catch (IOException e)
					{
						Toast.makeText(getActivity(), "没法写入", Toast.LENGTH_LONG)
								.show();
						e.printStackTrace();
					}
				}

				@Override
				public void onFailure(int statusCode, Header[] headers,
						byte[] binaryData, Throwable error)
				{
					if (binaryData != null)
					{
						String data = new String(binaryData);
						Logger.v(data);
					} else
					{
						Logger.v("error");
					}
				}

				/**
				 * 阅读按钮事件
				 * 
				 * @author Administrator
				 *
				 */
				class OnChangeListener implements OnClickListener
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
			};
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

}
