package com.louji.bookshelf;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.Header;

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
import com.louji.adapter.BookMarkAdapter;
import com.louji.adapter.BookMarkAdapter.OnDownLoadListener;
import com.louji.adapter.BookMarkAdapter.OnLineReaderListener;
import com.louji.base.R;
import com.louji.bean.BookBean;
import com.louji.http.BinaryHttpResponseHandler;
import com.louji.httputil.Binary;
import com.louji.httputil.Canstact;
import com.louji.util.FileUtil;
import com.yalantis.taurus.PullToRefreshView;
import com.yalantis.taurus.PullToRefreshView.OnRefreshListener;

public class RecommendFragment extends Fragment
{

	public static final int REFRESH_DELAY = 4000;// ˢ�¼��

	private PullToRefreshView pullToRefreshView;// �б����
	private ListView listView;// �б�

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
		bookBeans = new ArrayList<BookBean>(); // ��ʼ��bookBeans;
		for (int i = 0; i < 20; i++)
		{
			BookBean bookBean = new BookBean();
			bookBean.setBookTitle("������");
			bookBean.setBookInfo("��ONE PIECE����������������������������1999��10��20�����ձ���ʿ����̨��ʼ���ţ��������ڲ�����....");
			bookBean.setBookUrl(Canstact.URL);
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
		bookMarkAdapter.setOnLineReaderListener(new OnLineReaderListener()
		{

			@Override
			public void onReader(BookBean bookBean)
			{
				// ��ת���Ķ�����
				Bundle bundle = new Bundle();

				Intent intent = new Intent();
				intent.setClass(getActivity(), ReadActivity.class);
				intent.putExtras(bundle);
				getActivity().startActivity(intent);

			}
		});
		bookMarkAdapter.setOnDownLoadListener(new OnDownLoadListener()
		{

			@Override
			public void download(BookBean bookBean, final View v)
			{
				Toast.makeText(getActivity(), "start", Toast.LENGTH_LONG)
						.show();

				Binary binary = new Binary(getActivity());
				binary.setResponseHandlerInterface(new BinaryHttpResponseHandler()
				{

					String filePath;

					@Override
					public void onStart()
					{
						// TODO Auto-generated method stub
						super.onStart();
					}

					@Override
					public void onProgress(int bytesWritten, int totalSize)
					{
						// TODO Auto-generated method stub
						super.onProgress(bytesWritten, totalSize);
					}

					@Override
					public void onSuccess(int statusCode, Header[] headers,
							byte[] binaryData)
					{
						try
						{
							Toast.makeText(
									getActivity(),
									new String(binaryData, "gb2312")
											.subSequence(100, 1000),
									Toast.LENGTH_LONG).show();
						} catch (UnsupportedEncodingException e1)
						{
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}

						try
						{

							filePath = FileUtil.writeFiletoSdcard(
									getActivity(), binaryData);

							Toast.makeText(getActivity(), filePath,
									Toast.LENGTH_LONG).show();
							((ButtonFlat) v).setText("�Ķ�");
							v.setOnClickListener(new OnClickListener()
							{

								@Override
								public void onClick(View v)
								{
									Bundle bundle = new Bundle();
									bundle.putString("filePath", filePath);

									Intent intent = new Intent();
									intent.setClass(getActivity(),
											ReadBookActivity.class);
									intent.putExtras(bundle);
									getActivity().startActivity(intent);

								}
							});

						} catch (IOException e)
						{
							Toast.makeText(getActivity(), "û��д��",
									Toast.LENGTH_LONG).show();
							e.printStackTrace();
						}

					}

					@Override
					public void onFailure(int statusCode, Header[] headers,
							byte[] binaryData, Throwable error)
					{
						Toast.makeText(getActivity(), "error",
								Toast.LENGTH_LONG).show();

					}
				});

				binary.onRun(Canstact.URL, "", "");

			}
		});

	}

	private class RefreshListener implements OnRefreshListener
	{
		@Override
		public void onRefresh()
		{
			// ����ˢ��
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
