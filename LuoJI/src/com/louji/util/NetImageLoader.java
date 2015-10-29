package com.louji.util;

import java.io.File;

import com.nostra13.universalimageloader.cache.disc.impl.UnlimitedDiscCache;
import com.nostra13.universalimageloader.cache.memory.impl.UsingFreqLimitedMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageLoadingListener;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.core.download.BaseImageDownloader;
import com.nostra13.universalimageloader.utils.StorageUtils;

import android.content.Context;
import android.graphics.Bitmap;
import android.media.ImageReader.OnImageAvailableListener;
import android.widget.ImageView;

public class NetImageLoader
{
	private Context context;
	private File cacheDir;
	public ImageLoader imageLoader = ImageLoader.getInstance();
	private DisplayImageOptions options;
	public NetImageLoader(Context context)
	{
		this.context = context;
		cacheDir = StorageUtils.getOwnCacheDirectory(context,
				"imageloader/Cache");
		config();
	}

	private void config()
	{
		ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(
				context).memoryCacheExtraOptions(1000, 1500).threadPoolSize(5)// 线程池内加载的数量
						.threadPriority(Thread.NORM_PRIORITY - 2)
						.denyCacheImageMultipleSizesInMemory()
						.memoryCache(new UsingFreqLimitedMemoryCache(
								10 * 1024 * 1024))
						.memoryCacheSize(10 * 1024 * 1024)
						.discCacheSize(100 * 1024 * 1024)
						.tasksProcessingOrder(QueueProcessingType.LIFO)
						.discCacheFileCount(100)
						.discCache(new UnlimitedDiscCache(cacheDir))// 自定义缓冲路径
						.defaultDisplayImageOptions(
								DisplayImageOptions.createSimple())
						.imageDownloader(new BaseImageDownloader(context,
								5 * 1000, 30 * 1000))// 超时时间
						.writeDebugLogs().build();// 开始构建

		options = new DisplayImageOptions.Builder().cacheInMemory(true)
				.bitmapConfig(Bitmap.Config.RGB_565)
				.imageScaleType(ImageScaleType.IN_SAMPLE_INT).build();
		imageLoader.init(config);// 全局初始化此配置

	}

	public void displayImage(String uri, ImageView imageView)
	{
		imageLoader.displayImage(uri, imageView, options);

	}

	public void displayImage(String uri, ImageView imageView,
			ImageLoadingListener imageLoadingListener)
	{
		imageLoader.displayImage(uri, imageView, options, imageLoadingListener);
	}
}
