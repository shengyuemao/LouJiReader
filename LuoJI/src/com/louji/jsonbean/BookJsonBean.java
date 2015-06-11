package com.louji.jsonbean;

public class BookJsonBean
{

	private int bookid;
	private String booktitle;
	private String bookcontent;
	private String bookimage;
	private String bookurl;
	private int isrecommend;

	public BookJsonBean()
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
