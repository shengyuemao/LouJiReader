package com.louji.dbbean;

public class BookBean
{
	private int bookid;
	private String booktitle;
	private String bookcontent;
	private String bookimage;
	private String bookurl;
	private int isrecommend;
	
	private String booklocalpath;

	public static String getTable(){
		return "think_louji_book";
	}
	
	public String getBooklocalpath()
	{
		return booklocalpath;
	}

	public void setBooklocalpath(String booklocalpath)
	{
		this.booklocalpath = booklocalpath;
	}

	public BookBean()
	{
		super();
		// TODO Auto-generated constructor stub
	}

	public int getBookid()
	{
		return bookid;
	}

	public void setBookid(int bookid)
	{
		this.bookid = bookid;
	}

	public String getBooktitle()
	{
		return booktitle;
	}

	public void setBooktitle(String booktitle)
	{
		this.booktitle = booktitle;
	}

	public String getBookcontent()
	{
		return bookcontent;
	}

	public void setBookcontent(String bookcontent)
	{
		this.bookcontent = bookcontent;
	}

	public String getBookimage()
	{
		return bookimage;
	}

	public void setBookimage(String bookimage)
	{
		this.bookimage = bookimage;
	}

	public String getBookurl()
	{
		return bookurl;
	}

	public void setBookurl(String bookurl)
	{
		this.bookurl = bookurl;
	}

	public int getIsrecommend()
	{
		return isrecommend;
	}

	public void setIsrecommend(int isrecommend)
	{
		this.isrecommend = isrecommend;
	}

}
