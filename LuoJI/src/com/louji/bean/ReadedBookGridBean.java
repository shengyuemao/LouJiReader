package com.louji.bean;

public class ReadedBookGridBean
{

	private String imageUrl;
	private int imageId;
	private String imageFilePath;
	private String filePath;

	public String getFilePath()
	{
		return filePath;
	}

	public void setFilePath(String filePath)
	{
		this.filePath = filePath;
	}

	public ReadedBookGridBean()
	{
		super();
		// TODO Auto-generated constructor stub
	}

	public String getImageUrl()
	{
		return imageUrl;
	}

	public void setImageUrl(String imageUrl)
	{
		this.imageUrl = imageUrl;
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

}
