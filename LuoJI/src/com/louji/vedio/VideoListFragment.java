package com.louji.vedio;

import java.util.LinkedList;

import com.louji.adapter.VedioListAdapter;
import com.louji.base.R;
import com.yalantis.taurus.PullToRefreshView;
import com.yalantis.taurus.PullToRefreshView.OnRefreshListener;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

public class VideoListFragment extends Fragment
{
	public static final int REFRESH_DELAY = 4000;// 刷新间隔
	private LinkedList<String> vedioPaths;
	private PullToRefreshView pullToRefreshView;// 列表外层
	private ListView listView;// 列表
	private VedioListAdapter vedioLists;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState)
	{
		// TODO Auto-generated method stub
		View view = inflater.inflate(R.layout.fragment_vediolist, null);
		initData();
		initView(view);
		return view;
	}

	private void initData()
	{
		vedioPaths = new LinkedList<String>();
		vedioPaths.add(0,
				"http://dl14.80s.im:920/1510/[野山鹰]第01集/[野山鹰]第01集_hd.mp4");
		vedioPaths.add(1,
				"http://dl14.80s.im:920/1510/[野山鹰]第02集/[野山鹰]第02集_hd.mp4");
		vedioPaths.add(2,
				"http://dl14.80s.im:920/1510/[野山鹰]第03集/[野山鹰]第03集_hd.mp4");
		vedioPaths.add(3,
				"http://dl11.80s.im:920/1510/[野山鹰]第04集/[野山鹰]第04集_hd.mp4");
		vedioPaths.add(4,
				"http://dl11.80s.im:920/1510/[野山鹰]第05集/[野山鹰]第05集_hd.mp4");
		vedioLists = new VedioListAdapter(vedioPaths, getActivity());

	}

	private void initView(View view)
	{
		pullToRefreshView = (PullToRefreshView) view
				.findViewById(R.id.vediolistfragment_refresh);
		listView = (ListView) view
				.findViewById(R.id.vediolistfragment_list_view);
		pullToRefreshView.setOnRefreshListener(new RefreshListener());

		listView.setAdapter(vedioLists);
		listView.setOnItemClickListener(new OnItemClickListener()
		{

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id)
			{
				// TODO Auto-generated method stub
				Intent intent = new Intent(getActivity(),
						VedioPlayerActivity.class);
				getActivity().startActivity(intent);

			}
		});
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
