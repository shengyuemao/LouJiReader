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

@SuppressWarnings("rawtypes")
public class MenuActivity extends MaterialNavigationDrawer implements MaterialAccountListener {

	@Override
	public void onAccountOpening(MaterialAccount account) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onChangeAccount(MaterialAccount newAccount) {
		// TODO Auto-generated method stub
		
	}

	@SuppressWarnings("unchecked")
	@Override
	public void init(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		MaterialAccount account = new MaterialAccount(this.getResources(),"NeoKree","shengshidaxia@163.com",R.drawable.photo,R.drawable.bamboo);
		this.addAccount(account);
		
		MaterialAccount account2 = new MaterialAccount(this.getResources(),"Hatsune Miky","hatsune.miku@example.com",R.drawable.photo2,R.drawable.mat2);
		this.addAccount(account2);
		
        // set listener
        this.setAccountListener(this);

        
        // create sections
        this.addSection(newSection("书单",R.drawable.bookmark, new FragmentIndex()));
        this.addSection(newSection("书友",R.drawable.bookfirends,new FragmentIndex()));
        this.addSection(newSection("阅历",R.drawable.bookfoot,new FragmentButton()));
        this.addSection(newSection("云书架",R.drawable.bookyun,new FragmentButton()));
        this.addSection(newSection("书城",R.drawable.bookcity,new FragmentButton()));
        this.addSection(newSection("阅读圈",R.drawable.bookround,new FragmentButton()));
        
        // create bottom section
        this.addBottomSection(newSection("设置",R.drawable.ic_settings_black_24dp,new Intent(this,SettingsActivity.class)));
        
        
        this.getToolbar().setOnMenuItemClickListener(onMenuItemClick);
	}
	
	private Toolbar.OnMenuItemClickListener onMenuItemClick = new Toolbar.OnMenuItemClickListener() {
	    @Override
	    public boolean onMenuItemClick(MenuItem menuItem) {
	      String msg = "";
	      switch (menuItem.getItemId()) {
	        case R.id.action_edit:
	          msg += "Click edit";
	          break;
	        case R.id.action_share:
	          msg += "Click share";
	          break;
	        case R.id.action_settings:
	          msg += "Click setting";
	          break;
	      }

	      if(!msg.equals("")) {
	        Toast.makeText(MenuActivity.this, msg, Toast.LENGTH_SHORT).show();
	      }
	      return true;
	    }
	  };

}
