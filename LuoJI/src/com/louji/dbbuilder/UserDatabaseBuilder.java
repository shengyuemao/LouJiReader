package com.louji.dbbuilder;

import android.content.ContentValues;
import android.database.Cursor;

import com.louji.dbbean.UserBean;

public class UserDatabaseBuilder extends DatabaseBuilder<UserBean> {

	@Override
	public UserBean build(Cursor c) {
		int usernameClumn = c.getColumnIndex("username");
		int passwordClumn = c.getColumnIndex("password");
		int emailClumn = c.getColumnIndex("email");
		int phoneClumn = c.getColumnIndex("phone");
		int photoClumn = c.getColumnIndex("photo");
		int authinfoClumn = c.getColumnIndex("authinfo");
		int infoClumn = c.getColumnIndex("info");
		int uidClumn = c.getColumnIndex("uid");
		
		UserBean UserBean = new UserBean();
		UserBean.setAuthinfo(c.getString(authinfoClumn));
		UserBean.setEmail(c.getString(emailClumn));
		UserBean.setInfo(c.getString(infoClumn));
		UserBean.setPassword(c.getString(passwordClumn));
		UserBean.setPhone(c.getString(phoneClumn));
		UserBean.setPhoto(c.getString(photoClumn));
		UserBean.setUsername(c.getString(usernameClumn));
		UserBean.setUid(c.getInt(uidClumn));
		return UserBean;
	}

	@Override
	public ContentValues deconstruct(UserBean t) {
		ContentValues values = new ContentValues();
		values.put("username", t.getUsername());
		values.put("password", t.getPassword());
		values.put("email", t.getEmail());
		values.put("phone", t.getPhone());
		values.put("photo", t.getPhoto());
		values.put("authinfo", t.getAuthinfo());
		values.put("info", t.getInfo());
		return values;
	}

}
