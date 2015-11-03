package com.louji.slidingmenu;

import it.neokree.materialnavigationdrawer.MaterialNavigationDrawer;
import it.neokree.materialnavigationdrawer.elements.MaterialAccount;
import it.neokree.materialnavigationdrawer.elements.listeners.MaterialAccountListener;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.Toast;

import com.louji.base.R;
import com.louji.bookshelf.BookListFragment;
import com.louji.cartoon.CartoonFragment;
import com.louji.util.Logger;
import com.louji.vedio.VideoListFragment;

/**
 * 主页面
 * 
 * @author 盛月茂
 *
 */
@SuppressWarnings("rawtypes")
public class MenuActivity extends MaterialNavigationDrawer
		implements
			MaterialAccountListener
{

	@Override
	public void onAccountOpening(MaterialAccount account)
	{
		// TODO Auto-generated method stub
		Logger.i("this is onAccountOpening");

	}

	@Override
	public void onChangeAccount(MaterialAccount newAccount)
	{
		// TODO Auto-generated method stub
		Logger.i("this is onChangeAccount");

	}

	@Override
	public void init(Bundle savedInstanceState)
	{
		// TODO Auto-generated method stub
		CreateAccounts();

		// set listener
		this.setAccountListener(this);

		// create sections
		CreateSections();

		// create bottom section
		CreateBottomSection();

		this.getToolbar().setOnMenuItemClickListener(onMenuItemClick);
	}

	/**
	 * 创建客户
	 */
	private void CreateAccounts()
	{
		MaterialAccount account = new MaterialAccount(this.getResources(),
				"NeoKree", "shengshidaxia@163.com", R.drawable.photo,
				R.drawable.bamboo);
		this.addAccount(account);

	}

	/**
	 * 创建底部布局
	 */
	private void CreateBottomSection()
	{
		this.addBottomSection(
				newSection("设置", R.drawable.ic_settings_black_24dp,
						new Intent(this, SettingsActivity.class)));
	}

	/**
	 * 创建主布局
	 */
	@SuppressWarnings("unchecked")
	private void CreateSections()
	{
		this.addSection(
				newSection("书单", R.drawable.bookmark, new BookListFragment()));
		this.addSection(newSection("漫画", R.drawable.bookfirends,
				new CartoonFragment()));
		this.addSection(
				newSection("视频", R.drawable.bookfoot, new VideoListFragment()));
		this.addSection(
				newSection("云书架", R.drawable.bookyun, new BookListFragment()));
		this.addSection(
				newSection("书城", R.drawable.bookcity, new BookListFragment()));
		this.addSection(newSection("阅读圈", R.drawable.bookround,
				new BookListFragment()));
	}

	/**
	 * 顶部栏点击事件
	 */
	private Toolbar.OnMenuItemClickListener onMenuItemClick = new Toolbar.OnMenuItemClickListener()
	{
		@Override
		public boolean onMenuItemClick(MenuItem menuItem)
		{
			String msg = "";
			switch (menuItem.getItemId())
			{
				case R.id.action_edit :
					msg += "Click edit";
					break;
				case R.id.action_share :
					msg += "Click share";
					break;
				case R.id.action_settings :
					msg += "Click setting";
					break;
			}

			if (!msg.equals(""))
			{
				Toast.makeText(MenuActivity.this, msg, Toast.LENGTH_SHORT)
						.show();
			}
			return true;
		}
	};

}
