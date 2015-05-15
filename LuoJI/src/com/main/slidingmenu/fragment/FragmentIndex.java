package com.main.slidingmenu.fragment;

import it.neokree.materialtabs.MaterialTab;
import it.neokree.materialtabs.MaterialTabHost;
import it.neokree.materialtabs.MaterialTabListener;

import com.main.luoji.R;
import com.main.slidingmenu.fragment.bookshelf.FragmentText;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by neokree on 24/11/14.
 */
public class FragmentIndex extends Fragment implements MaterialTabListener {

	private MaterialTabHost tabHost;
	private ViewPager pager;
	private ViewPagerAdapter adapter;

	@Override
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_bookshelf, null);

		initView(view);

		return view;
	}

	private void initView(View view) {
		tabHost = (MaterialTabHost) view
				.findViewById(R.id.fragment_bookshelf_tabHost);
		pager = (ViewPager) view.findViewById(R.id.fragment_bookshelf_pager);

		// init view pager
		adapter = new ViewPagerAdapter(getActivity()
				.getSupportFragmentManager());
		pager.setAdapter(adapter);
		pager.setOnPageChangeListener(new OnPageChangeListener() {

			@Override
			public void onPageSelected(int position) {
				tabHost.setSelectedNavigationItem(position);
			}

			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onPageScrollStateChanged(int arg0) {
				// TODO Auto-generated method stub

			}
		});

		// insert all tabs from pagerAdapter data
		for (int i = 0; i < adapter.getCount(); i++) {
			tabHost.addTab(tabHost.newTab().setText(adapter.getPageTitle(i))
					.setTabListener(this));
		}
	}

	@Override
	public void onTabSelected(MaterialTab tab) {
		// TODO Auto-generated method stub
		pager.setCurrentItem(tab.getPosition());
	}

	@Override
	public void onTabReselected(MaterialTab tab) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onTabUnselected(MaterialTab tab) {
		// TODO Auto-generated method stub

	}

	private class ViewPagerAdapter extends FragmentStatePagerAdapter {

		public ViewPagerAdapter(FragmentManager fm) {
			super(fm);
			// TODO Auto-generated constructor stub
		}

		@Override
		public Fragment getItem(int num) {
			// TODO Auto-generated method stub
			return new FragmentText();
		}

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return 3;
		}

		@Override
		public CharSequence getPageTitle(int position) {
			// TODO Auto-generated method stub
			return "Section" + position;
		}

	}

}
