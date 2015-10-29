package com.louji.cartoon;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONObject;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.louji.adapter.CartoonRecommandAdapter;
import com.louji.base.R;
import com.louji.bean.BookBean;
import com.louji.bean.CartoonBean;
import com.louji.contacts.Contacts;
import com.louji.db.DatabaseFactory;
import com.louji.http.JsonHttpResponseHandler;
import com.louji.httputil.HttpUtil;
import com.louji.jsonbean.BookJsonBean;
import com.louji.jsonbean.CartoonJsonBean;
import com.louji.util.Logger;
import com.louji.widgets.ReboundScrollView;
import com.louji.widgets.RecommandCartoonLinearLayout;
import com.louji.widgets.RecommandCartoonLinearLayout.OnClickItemListener;
import com.louji.widgets.RecycleView;
import com.louji.widgets.RecycleView.OnHotCartoonListener;
import com.yalantis.taurus.PullToRefreshView;
import com.yalantis.taurus.PullToRefreshView.OnRefreshListener;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class CartoonFragment extends Fragment
{
	private RecommandCartoonLinearLayout recommandCartoon;
	private RecycleView recycleView;
	private List<CartoonBean> cartoonBeans;
	private List<CartoonBean> hotCartoonBeans;
	private List<CartoonJsonBean> hotCartoonJsonBeans;
	private PullToRefreshView pullToRefreshView;
	private CartoonRecommandAdapter recommandAdapter;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState)
	{
		View root = inflater.inflate(R.layout.fragment_cartoon, null);

		recycleView = (RecycleView) root
				.findViewById(R.id.cartoonfragment_viewpager);
		recommandCartoon = (RecommandCartoonLinearLayout) root
				.findViewById(R.id.cartoonfragment_list);
		pullToRefreshView = (PullToRefreshView) root
				.findViewById(R.id.recommendfragment_refresh);
		initData();
		initListener();
		return root;
	}

	@Override
	public void onResume()
	{
		// TODO Auto-generated method stub
		super.onResume();

	}

	private void initData()
	{
		cartoonBeans = new ArrayList<CartoonBean>();
		hotCartoonBeans = new ArrayList<CartoonBean>();
		hotCartoonJsonBeans = new ArrayList<CartoonJsonBean>();
		// 获取热门列表
		HttpUtil.get(Contacts.HOT_CARTOON_URL, new JsonHttpResponseHandler()
		{

			@Override
			public void onSuccess(int statusCode, Header[] headers,
					JSONArray response)
			{
				// TODO Auto-generated method stub
				Logger.i(response.toString());
				hotCartoonBeans = parserNetData(response);
				int count = hotCartoonBeans.size();
				List<String> imageUrls = new ArrayList<String>();
				for (int i = 0; i < count; i++)
				{
					imageUrls.add(hotCartoonBeans.get(i).getImageurl());
				}

				recycleView.setImage_counts(count);// 设置参展图片个数
				recycleView.setAutoPlay(true);// 设置为自动播放
				recycleView.setCartoonBeans(hotCartoonJsonBeans);// 添加数据
				recycleView.setImageUrls(imageUrls);// 设置图片的url集
				recycleView.setOnHotCartoonListener(onHotCartoonListener());// 设置点击事件
				super.onSuccess(statusCode, headers, response);
			}
			// 点击事件
			private OnHotCartoonListener onHotCartoonListener()
			{
				return new OnHotCartoonListener()
				{

					@Override
					public void onClicked(CartoonJsonBean cartoonBean)
					{
						// TODO Auto-generated method stub
						Intent intent = new Intent(getActivity(),
								CartoonViewPagerActivity.class);
						intent.putExtra("picUrl", cartoonBean.getCartoon_url());
						getActivity().startActivity(intent);
					}
				};
			}

			@Override
			public void onFailure(int statusCode, Header[] headers,
					Throwable throwable, JSONArray errorResponse)
			{
				// TODO Auto-generated method stub
				super.onFailure(statusCode, headers, throwable, errorResponse);
			}

		});

		// 获取推荐列表
		HttpUtil.get(Contacts.RECOMMEND_CARTOON_URL,
				new JsonHttpResponseHandler()
				{

					@Override
					public void onSuccess(int statusCode, Header[] headers,
							JSONArray response)
					{
						cartoonBeans = parserNetData(response);
						recommandAdapter = new CartoonRecommandAdapter(
								getActivity(), cartoonBeans);
						recommandCartoon.setBaseAdapter(recommandAdapter);
						super.onSuccess(statusCode, headers, response);
					}

					@Override
					public void onFailure(int statusCode, Header[] headers,
							Throwable throwable, JSONArray errorResponse)
					{
						// TODO Auto-generated method stub
						super.onFailure(statusCode, headers, throwable,
								errorResponse);
					}

				});

	}

	private void initListener()
	{
		pullToRefreshView.setOnRefreshListener(new OnRefreshListener()
		{

			@Override
			public void onRefresh()
			{
				// TODO Auto-generated method stub

			}
		});

		recommandCartoon.setOnClickItemListener(new OnClickItemListener()
		{

			@Override
			public void onSelected(View view, int position)
			{
				// TODO Auto-generated method stub

			}
		});
	}

	/**
	 * 解析网络数据
	 * 
	 * @param response
	 */
	private List<CartoonBean> parserNetData(JSONArray response)
	{

		List<CartoonBean> cartoonBeans = new ArrayList<CartoonBean>();

		Gson gson = new Gson();
		List<CartoonJsonBean> cartoonJsonBeans = gson.fromJson(
				response.toString(), new TypeToken<List<CartoonJsonBean>>()
				{
				}.getType());
		int count = cartoonJsonBeans.size();
		Logger.i(count + "");
		for (int i = 0; i < count; i++)
		{
			CartoonJsonBean cartoonJsonBean = cartoonJsonBeans.get(i);
			CartoonBean cartoonBean = new CartoonBean();
			cartoonBean.setImagecontent(cartoonJsonBean.getCartoon_content());
			cartoonBean.setImageurl(cartoonJsonBean.getCartoon_image());
			cartoonBean.setImagetitle(cartoonJsonBean.getCartoon_titile());
			cartoonBean.setIsrecommand(cartoonJsonBean.getCartoon_remmand());
			cartoonBeans.add(cartoonBean);
		}
		hotCartoonJsonBeans = cartoonJsonBeans; // 赋值给原始版本的轮播图数据modle
		return cartoonBeans;
	}

}
