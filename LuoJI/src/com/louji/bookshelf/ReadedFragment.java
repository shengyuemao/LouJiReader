package com.louji.bookshelf;

import java.util.ArrayList;
import java.util.List;

import com.louji.adapter.ReadedGridAdapter;
import com.louji.base.R;
import com.louji.bean.ReadedBookGridBean;
import com.louji.bookread.ReadBookActivity;
import com.louji.db.DatabaseFactory;
import com.louji.dbbean.BookBean;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.Toast;

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
		bookself.setOnItemClickListener(new OnItemClickListener()
		{

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id)
			{
				ReadedBookGridBean readedBookGridBean = readedBookGridBeans
						.get(position);
				Bundle bundle = new Bundle();
				bundle.putString("filePath", readedBookGridBean.getFilePath());

				Intent intent = new Intent();
				intent.setClass(getActivity(), ReadBookActivity.class);
				intent.putExtras(bundle);
				getActivity().startActivity(intent);
			}

		});
	}

	private void initData()
	{

		List<BookBean> bookBeans = DatabaseFactory.bookData(getActivity())
				.selectMore();// 全部下载数据

		for (int i = 0; i < bookBeans.size(); i++)
		{

			Toast.makeText(getActivity(), bookBeans.get(i).getBooktitle(),
					Toast.LENGTH_LONG).show();

			ReadedBookGridBean readedBookGridBean = new ReadedBookGridBean();
			readedBookGridBean.setImageUrl(bookBeans.get(i).getBookimage());
			readedBookGridBean.setFilePath(bookBeans.get(i).getBooklocalpath());
			readedBookGridBean.setBookName(bookBeans.get(i).getBooktitle());
			readedBookGridBeans.add(readedBookGridBean);
		}

		readedGridAdapter = new ReadedGridAdapter(readedBookGridBeans,
				getActivity().getApplicationContext());
		bookself.setAdapter(readedGridAdapter);

	}
}
