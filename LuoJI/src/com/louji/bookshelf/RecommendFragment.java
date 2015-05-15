package com.louji.bookshelf;

import java.util.ArrayList;
import java.util.List;

import com.louji.adapter.BookMarkAdapter;
import com.louji.base.R;
import com.louji.bean.BookBean;
import com.yalantis.taurus.PullToRefreshView;
import com.yalantis.taurus.PullToRefreshView.OnRefreshListener;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

public class RecommendFragment extends Fragment
{

	public static final int REFRESH_DELAY = 4000;// 刷新间隔

	private PullToRefreshView pullToRefreshView;// 列表外层
	private ListView listView;// 列表

	private List<BookBean> bookBeans;

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
		listView.setAdapter(new BookMarkAdapter(getActivity(), bookBeans));
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
