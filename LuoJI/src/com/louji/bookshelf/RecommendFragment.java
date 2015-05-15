package com.louji.bookshelf;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.Header;
import org.apache.http.HttpResponse;

import com.gc.materialdesign.views.ButtonFlat;
import com.louji.adapter.BookMarkAdapter;
import com.louji.adapter.BookMarkAdapter.OnDownLoadListener;
import com.louji.base.R;
import com.louji.bean.BookBean;
import com.louji.http.FileAsyncHttpResponseHandler;
import com.louji.http.ResponseHandlerInterface;
import com.louji.httputil.FileNet;
import com.yalantis.taurus.PullToRefreshView;
import com.yalantis.taurus.PullToRefreshView.OnRefreshListener;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

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
		View view = inflater.inflate(R.layout.recommendfragment, null);

		initData();

		initView(view);

		return view;
	}

	public void initData()
	{
		bookBeans = new ArrayList<BookBean>(); // 初始化bookBeans;
		for (int i = 0; i < 20; i++)
		{
			BookBean bookBean = new BookBean();
			bookBean.setBookTitle("海贼王");
			bookBean.setBookInfo("《ONE PIECE》（海贼王、航海王）动画，于1999年10月20日在日本富士电视台开始播放，至今仍在播放中....");
			bookBean.setBookUrl("http://dm.txt99.cc/2/4/%E7%A5%9E%E5%8C%BB%E8%B4%B5%E5%A5%B3%EF%BC%9A%E7%9B%9B%E5%AE%A0%E4%B8%83%E7%9A%87%E5%A6%83.txt");
			bookBeans.add(bookBean);
		}

	}

	public void initView(View view)
	{
		pullToRefreshView = (PullToRefreshView) view
				.findViewById(R.id.recommendfragment_refresh);
		pullToRefreshView.setOnRefreshListener(new RefreshListener());

		listView = (ListView) view
				.findViewById(R.id.recommendfragment_list_view);
		bookMarkAdapter = new BookMarkAdapter(getActivity(), bookBeans);
		listView.setAdapter(bookMarkAdapter);
		bookMarkAdapter.setOnDownLoadListener(new OnDownLoadListener()
		{

			@Override
			public void download(BookBean bookBean, final View v)
			{
				Toast.makeText(getActivity(), "start", Toast.LENGTH_LONG)
						.show();

				FileNet fileNet = new FileNet(getActivity());
				fileNet.setResponseHandlerInterface(new FileAsyncHttpResponseHandler(
						getActivity())
				{

					String filePath;
					@Override
					public void onSuccess(int statusCode, Header[] headers,
							File file)
					{
						Toast.makeText(getActivity(), "success",
								Toast.LENGTH_LONG).show();

						((ButtonFlat) v).setText("阅读");
						filePath = getTargetFile().getAbsolutePath();

						v.setOnClickListener(new OnClickListener()
						{

							@Override
							public void onClick(View v)
							{
								// 跳转到阅读界面
								Bundle bundle = new Bundle();
								bundle.putString("filePath", filePath);
								
								Intent  intent  = new Intent();
								intent.putExtras(bundle);
								getActivity().startActivity(intent);

							}
						});

					}

					@Override
					public void onFailure(int statusCode, Header[] headers,
							Throwable throwable, File file)
					{
						Toast.makeText(getActivity(), "error",
								Toast.LENGTH_LONG).show();

					}
				});
				fileNet.onRun(bookBean.getBookUrl(), "", "");

			}
		});

	}

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
