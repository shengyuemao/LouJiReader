package com.louji.dbbuilder;

import android.content.ContentValues;
import android.database.Cursor;

import com.louji.dbbean.BookBean;


public class BookDatabaseBuilder extends DatabaseBuilder<BookBean>
{

	@Override
	public BookBean build(Cursor c)
	{
		int bookidClumn = c.getColumnIndex("bookid");
		int booktitleClumn = c.getColumnIndex("booktitle");
		int bookcontentClumn = c.getColumnIndex("bookcontent");
		int bookimageClumn = c.getColumnIndex("bookimage");
		int bookurlClumn = c.getColumnIndex("bookurl");
		int isRecommendClumn = c.getColumnIndex("isrecommend");
		int bookFilePathClumn = c.getColumnIndex("booklocalpath");
		
		BookBean bookBean = new BookBean();
		bookBean.setBookcontent(c.getString(bookcontentClumn));
		bookBean.setBookid(c.getInt(bookidClumn));
		bookBean.setBookimage(c.getString(bookimageClumn));
		bookBean.setBooktitle(c.getString(booktitleClumn));
		bookBean.setBookurl(c.getString(bookurlClumn));
		bookBean.setBooklocalpath(c.getString(bookFilePathClumn));
		bookBean.setIsrecommend(c.getInt(isRecommendClumn));
		
		return bookBean;
	}

	@Override
	public ContentValues deconstruct(BookBean t)
	{
		ContentValues values = new ContentValues();
		values.put("bookid", t.getBookid());
		values.put("booktitle", t.getBooktitle());
		values.put("bookcontent", t.getBookcontent());
		values.put("bookimage", t.getBookimage());
		values.put("bookurl", t.getBookurl());
		values.put("isrecommend", t.getIsrecommend());
		values.put("booklocalpath", t.getBooklocalpath());
		return null;
	}

}
