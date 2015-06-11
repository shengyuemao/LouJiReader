package com.louji.bean;

/**
 * 书籍模型类
 * 
 * @author 盛月茂
 *
 */
public class BookBean
{

	private int imageId;
	private String imageFilePath;
	private String imageUrl;
	private String bookTitle;
	private String bookInfo;
	private String bookUrl;
	private String bookFilePath;
	private int  isrecommend;

	public int getIsrecommend()
	{
		return isrecommend;
	}

	public void setIsrecommend(int isrecommend)
	{
		this.isrecommend = isrecommend;
	}

	public BookBean()
	{
		super();
		// TODO Auto-generated constructor stub
	}

	public BookBean(int imageId, String imageFilePath, String imageUrl,
			String bookTitle, String bookInfo, String bookUrl,
			String bookFilePath)
	{
		super();
		this.imageId = imageId;
		this.imageFilePath = imageFilePath;
		this.imageUrl = imageUrl;
		this.bookTitle = bookTitle;
		this.bookInfo = bookInfo;
		this.bookUrl = bookUrl;
		this.bookFilePath = bookFilePath;
	}

	public int getImageId()
	{
		return imageId;
	}

	public void setImageId(int imageId)
	{
		this.imageId = imageId;
	}

	public String getImageFilePath()
	{
		return imageFilePath;
	}

	public void setImageFilePath(String imageFilePath)
	{
		this.imageFilePath = imageFilePath;
	}

	public String getImageUrl()
	{
		return imageUrl;
	}

	public void setImageUrl(String imageUrl)
	{
		this.imageUrl = imageUrl;
	}

	public String getBookTitle()
	{
		return bookTitle;
	}

	public void setBookTitle(String bookTitle)
	{
		this.bookTitle = bookTitle;
	}

	public String getBookInfo()
	{
		return bookInfo;
	}

	public void setBookInfo(String bookInfo)
	{
		this.bookInfo = bookInfo;
	}

	public String getBookUrl()
	{
		return bookUrl;
	}

	public void setBookUrl(String bookUrl)
	{
		this.bookUrl = bookUrl;
	}

	public String getBookFilePath()
	{
		return bookFilePath;
	}

	public void setBookFilePath(String bookFilePath)
	{
		this.bookFilePath = bookFilePath;
	}

}
