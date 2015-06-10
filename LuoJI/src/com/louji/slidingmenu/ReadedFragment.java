package com.louji.slidingmenu;

import java.util.ArrayList;
import java.util.List;
import com.louji.adapter.ReadedGridAdapter;
import com.louji.base.R;
import com.louji.bean.ReadedBookGridBean;
import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

@SuppressLint("InflateParams")
public class ReadedFragment extends Fragment
{

	private GridView bookself;
	private ReadedGridAdapter readedGridAdapter;
	private List<ReadedBookGridBean> readedBookGridBeans;

	@Override
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
	{
		View view = inflater.inflate(R.layout.fragment_readed, null);

		initView(view);
		initData();

		return view;
	}

	private void initView(View view)
	{
		bookself = (GridView) view.findViewById(R.id.fragment_readed_bookself);
		readedBookGridBeans = new ArrayList<ReadedBookGridBean>();
	}

	private void initData()
	{

		for (int i = 0; i < 9; i++)
		{
			ReadedBookGridBean readedBookGridBean = new ReadedBookGridBean();
			readedBookGridBean
					.setImageUrl("http://maomo-public.stor.sinaapp.com/ic_index_hotel.png");
			readedBookGridBeans.add(readedBookGridBean);
		}

		readedGridAdapter = new ReadedGridAdapter(readedBookGridBeans,
				getActivity().getApplicationContext());
		bookself.setAdapter(readedGridAdapter);

	}
}
