package com.louji.widgets;

import java.util.List;

import com.louji.util.FileUtil;
import com.louji.util.Logger;
import com.louji.util.NetImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.assist.ImageLoadingListener;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;

public class CartoonLinearLayout extends LinearLayout
{

	private NetImageLoader imageLoader;


	public CartoonLinearLayout(Context context)
	{
		super(context);
		// TODO Auto-generated constructor stub
	}

	public CartoonLinearLayout(Context context, AttributeSet attrs)
	{
		super(context, attrs);
		// TODO Auto-generated constructor stub

	}

	public void setCartoonListAdapter(List<PhotoView> cartoonListAdapter)
	{

		if (cartoonListAdapter != null)
		{
			imageLoader = new NetImageLoader(getContext());
			

			for (int i = 0; i < cartoonListAdapter.size(); i++)
			{
				
				PhotoView imageTouchView = cartoonListAdapter.get(i);
				LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
						LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
				imageTouchView.setLayoutParams(params);
				CartoonLinearLayout.this.addView(imageTouchView,
						LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);

				setImage(imageTouchView);// ¼ÓÔØÍ¼Æ¬

			}
		}
	}


	private void setImage(PhotoView imageTouchView)
	{
		String[] strs = imageTouchView.getTag().toString().split("/");

		Bitmap bitmap = BitmapFactory.decodeFile(
				FileUtil.getCartoonFile(strs[3], strs[4]).getAbsolutePath()
						+ "/" + strs[5]);
		if (bitmap != null)
		{
			imageTouchView.setImageBitmap(bitmap);

		} else
		{

			imageLoader.displayImage(imageTouchView.getTag() + "",
					imageTouchView, saveImage());
		}
	}

	private ImageLoadingListener saveImage()
	{
		return new ImageLoadingListener()
		{

			@Override
			public void onLoadingStarted(String imageUri, View view)
			{
				// TODO Auto-generated method stub

			}

			@Override
			public void onLoadingFailed(String imageUri, View view,
					FailReason failReason)
			{
				// TODO Auto-generated method stub

			}

			@Override
			public void onLoadingComplete(String imageUri, View view,
					Bitmap loadedImage)
			{
				// TODO Auto-generated method stub
				String[] strs = imageUri.split("/");
				for (String str : strs)
				{
					Logger.i(str);
				}

				FileUtil.saveBitmap(loadedImage, strs[5], strs[3], strs[4]);

			}

			@Override
			public void onLoadingCancelled(String imageUri, View view)
			{
				// TODO Auto-generated method stub

			}
		};
	}

}
