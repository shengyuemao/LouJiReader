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
		int bookurlClumn = c.getColumnIndex("bookurl");
		int isRecommendClumn = c.getColumnIndex("isrecommend");
		int bookFilePathClumn = c.getColumnIndex("booklocalpath");
		int bookImageClumn = c.getColumnIndex("bookimage") ;

		BookBean bookBean = new BookBean();
		bookBean.setBookcontent(c.getString(bookcontentClumn));
		bookBean.setBookid(c.getInt(bookidClumn));
		bookBean.setBooktitle(c.getString(booktitleClumn));
		bookBean.setBookurl(c.getString(bookurlClumn));
		bookBean.setBooklocalpath(c.getString(bookFilePathClumn));
		bookBean.setIsrecommend(c.getInt(isRecommendClumn));
		bookBean.setBookimage(c.getString(bookImageClumn));

		return bookBean;
	}

	@Override
	public ContentValues deconstruct(BookBean t)
	{
		ContentValues values = new ContentValues();
		values.put("booktitle", t.getBooktitle());
		values.put("bookcontent", t.getBookcontent());
		values.put("bookurl", t.getBookurl());
		values.put("isrecommend", t.getIsrecommend());
		values.put("booklocalpath", t.getBooklocalpath());
		values.put("bookimage", t.getBookimage());
		return values;
	}

}
