package com.louji.vedio;

public class VedioList
{

	private VedioInfo first;

	public VedioList()
	{
		this.first = null;
	}

	public VedioInfo getFirst()
	{
		return this.first;
	}

	public void setFirst(VedioInfo first)
	{
		this.first = first;
	}

	public boolean isEmpty()
	{
		return first == null;
	}

	public VedioListIterator getIterator()
	{
		return new VedioListIterator(this);
	}

}
