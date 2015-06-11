package com.louji.util;

import java.util.HashMap;
import java.util.Map;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory.Options;
import android.os.Handler;
import android.text.TextUtils;
import android.widget.ImageView;

import com.louji.base.LoujiBaseApplication;
import com.nostra13.universalimageloader.cache.disc.DiscCacheAware;
import com.nostra13.universalimageloader.core.DefaultConfigurationFactory;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageLoadingListener;
import com.nostra13.universalimageloader.core.assist.ImageSize;
import com.nostra13.universalimageloader.core.assist.MemoryCacheUtil;
import com.nostra13.universalimageloader.core.decode.BaseImageDecoder;
import com.nostra13.universalimageloader.core.decode.ImageDecodingInfo;
import com.nostra13.universalimageloader.core.display.BitmapDisplayer;
import com.nostra13.universalimageloader.utils.ImageSizeUtils;

/**
 * 
 * 图片加载框架<BR>
 * 
 * @author c00211122
 * @version [V1.0, 2013-6-3]
 */
public final class OnlineImageLoader
{
    
    private static ImageLoaderConfiguration sImageLoaderConfig;
    
    private static DiscCacheAware sDiscCacheAware;
    
   
    /**
     * 图片压缩质量1-100，值越大质量越高
     */
    private static final int COMPRESS_QUALITY = 100;
    
    /**
     * 磁盘缓存大小
     */
    private static final int DISC_CACHE_SIZE = 50 * 1024 * 1024;
    
    /**
     * 内存缓存大小
     */
    private static final int MEMORY_CACHE_SIZE = 2 * 1024 * 1024;
    
    private static Map<String, Integer> sDiscountMemParams =
        new HashMap<String, Integer>();
    
    static
    {
        Context context = LoujiBaseApplication.context;
        sImageLoaderConfig =
            new ImageLoaderConfiguration.Builder(context).discCacheExtraOptions(0,
                0,
                CompressFormat.PNG,
                COMPRESS_QUALITY, null)
                .memoryCacheSize(MEMORY_CACHE_SIZE)
                /* .memoryCache(sMemoryCache) */
                .discCacheSize(DISC_CACHE_SIZE)
                .imageDecoder(new MusicBaseImageDecoder(true))
                .build();
        sDiscCacheAware =
            DefaultConfigurationFactory.createDiscCache(context,
                DefaultConfigurationFactory.createFileNameGenerator(),
                DISC_CACHE_SIZE,
                0);
        ImageLoader.getInstance().init(sImageLoaderConfig);
    }
    
    /**
     * 显示图片<BR>
     * 
     * @param uri 图片地址
     * @param imageView 图片控件
     */
    public static void displayImage(String uri, ImageView imageView)
    {
        uri = wrapImgUrl(uri);
        ImageLoader.getInstance().displayImage(uri, imageView);
    }
    
    
    /**
     * 显示图片<BR>
     * 
     * @param uri 图片地址
     * @param imageView 图片控件
     * @param builder 图片选项构建器
     */
    public static void displayImage(String uri, ImageView imageView,
        DisplayImageOptions.Builder builder)
    {
        DisplayImageOptions options = builder.build();
        uri = wrapImgUrl(uri);
        ImageLoader.getInstance().displayImage(uri, imageView, options);
    }
    
    /**
     * 显示图片<BR>
     * 
     * @param uri 图片地址
     * @param imageView 图片控件
     * @param builder 图片选项构建器
     * @param defaultImgResId 默认图片
     */
    public static void displayImage(String uri, ImageView imageView,
        DisplayImageOptions.Builder builder, int defaultImgResId)
    {
        builder.showStubImage(defaultImgResId)
            .showImageForEmptyUri(defaultImgResId)
            .showImageOnFail(defaultImgResId);
        displayImage(uri, imageView, builder);
    }
    
    /**
     * 显示图片<BR>
     * 
     * @param uri 图片地址
     * @param imageView 图片控件
     * @param builder 图片选项构建器
     * @param listener 加载图片状态监听器
     */
    public static void displayImage(String uri, ImageView imageView,
        DisplayImageOptions.Builder builder, ImageLoadingListener listener)
    {
        DisplayImageOptions options = builder.build();
        uri = wrapImgUrl(uri);
        ImageLoader.getInstance().displayImage(uri,
            imageView,
            options,
            listener);
    }
    
    /**
     * 显示图片<BR>
     * 
     * @param uri 图片地址
     * @param imageView 图片控件
     * @param builder 图片选项构建器
     * @param defaultImgResId 默认图片
     * @param discount 折扣
     */
    public static void displayImage(String uri, ImageView imageView,
        DisplayImageOptions.Builder builder, int defaultImgResId, int discount)
    {
        String memoryCacheKey = getMemoryCacheKey(uri, imageView);
        if (memoryCacheKey != null)
        {
            synchronized (sDiscountMemParams)
            {
                sDiscountMemParams.put(memoryCacheKey, discount);
            }
        }
        builder.showStubImage(defaultImgResId)
            .showImageForEmptyUri(defaultImgResId)
            .showImageOnFail(defaultImgResId);
        displayImage(uri, imageView, builder);
    }
    
    /**
     * 显示图片<BR>
     * 
     * @param uri 图片地址
     * @param imageView 图片控件
     * @param defaultImgResId 默认图片
     */
    public static void displayImage(String uri, ImageView imageView,
        int defaultImgResId)
    {
        displayImage(uri, imageView, defaultImgResId, 1);
    }
       
    /**
     * 显示图片<BR>
     * 
     * @param uri 图片地址
     * @param imageView 图片控件
     * @param defaultImgResId 默认图片
     * @param discount 折扣
     */
    public static void displayImage(String uri, ImageView imageView,
        int defaultImgResId, int discount)
    {
        String memoryCacheKey = getMemoryCacheKey(uri, imageView);
        if (memoryCacheKey != null)
        {
            synchronized (sDiscountMemParams)
            {
                sDiscountMemParams.put(memoryCacheKey, discount);
            }
        }
        
        DisplayImageOptions.Builder builder;
        
        builder = getDefaultDisplayImageOptionsBuilder();
        if (defaultImgResId > 0)
        {
            LocalImageLoader.getInstance().displayImage(imageView,
                defaultImgResId);
        }
        
        displayImage(uri, imageView, builder);
    }
    
    /**
     * 显示图片<BR>
     * 
     * @param uri 图片地址
     * @param imageView 图片控件
     * @param defaultImgResId 默认图片
     * @param discount 
     */
    public static void displayImage(String uri, ImageView imageView,
        int defaultImgResId, int discount, BitmapDisplayer displayer)
    {
        String memoryCacheKey = getMemoryCacheKey(uri, imageView);
        if (memoryCacheKey != null)
        {
            synchronized (sDiscountMemParams)
            {
                sDiscountMemParams.put(memoryCacheKey, discount);
            }
        }
        DisplayImageOptions.Builder builder =
            getDefaultDisplayImageOptionsBuilder().showStubImage(defaultImgResId)
                .showImageForEmptyUri(defaultImgResId)
                .showImageOnFail(defaultImgResId)
                .cacheOnDisc()
                .displayer(displayer);
        displayImage(uri, imageView, builder);
    }
    
    /**
     * 获取默认的图片选项构建器<BR>
     * 默认启动图片内存缓存和图片SD卡缓存
     * 
     * @return 图片选项构建器
     */
    public static DisplayImageOptions.Builder getDefaultDisplayImageOptionsBuilder()
    {
        return new DisplayImageOptions.Builder().cacheInMemory().cacheOnDisc();
    }
    
    /**
     * 获取磁盘缓存<BR>
     * 
     * @return 磁盘缓存
     */
    public static DiscCacheAware getDiscCacheAware()
    {
        return sDiscCacheAware;
    }
    
    /**
     * 
     * 获取默认的图片选项构建器<BR>
     * 默认启动图片内存缓存缓存
     * 
     * @return 图片选项构建器
     */
    public static DisplayImageOptions.Builder getOnMemoryDisplayImageOptionsBuilder()
    {
        return new DisplayImageOptions.Builder().cacheInMemory();
    }
    
    private static String getMemoryCacheKey(String uri, ImageView imageView)
    {
        if (uri == null)
        {
            return null;
        }
        ImageSize targetSize =
            ImageSizeUtils.defineTargetSizeForView(imageView, 0, 0);
        String memoryCacheKey = MemoryCacheUtil.generateKey(uri, targetSize);
        return memoryCacheKey;
    }
    
    private static String wrapImgUrl(String uri)
    {
        return uri;
    }
    
    /**
     * 缓存图片到disc
     * 暂时只有offline stream 用到
     *  
     * @param uri 图片uri路径
     */
    public static void loadImage(String uri, Handler handler,
        ImageLoadingListener listener)
    {
        if (TextUtils.isEmpty(uri))
        {
            return;
        }
        uri = wrapImgUrl(uri);
        DisplayImageOptions options =
            new DisplayImageOptions.Builder().cacheOnDisc()
                .handler(handler)
                .build();
        ImageLoader.getInstance().loadImage(uri, options, listener);
    }
    
    /**
     * 
     * 图片解码器<BR>
     * 对图片进行解码，根据需要对内存进行优化
     * 
     * @author c00211122
     * @version [RCS Client V100R001C03, 2013-11-13]
     */
    private static class MusicBaseImageDecoder extends BaseImageDecoder
    {
        
        public MusicBaseImageDecoder(boolean loggingEnabled)
		{
			super(loggingEnabled);
			// TODO Auto-generated constructor stub
		}

		private static final String TAG = "MusicImageLoader";
        
        @Override
        protected Options prepareDecodingOptions(ImageSize imageSize,
            ImageDecodingInfo decodingInfo)
        {
            Options decodingOptions =
                super.prepareDecodingOptions(imageSize, decodingInfo);
            decodingOptions.inPurgeable = true;
            decodingOptions.inPreferredConfig = Bitmap.Config.RGB_565;
            //            boolean discountMemoryFlag = false;
            Integer discount = 1;
            if (decodingInfo.getImageKey() != null)
            {
                synchronized (sDiscountMemParams)
                {
                    discount =
                        sDiscountMemParams.get(decodingInfo.getImageKey());
                }
            }
            if (discount == null)
            {
                discount = 1;
            }
            if (discount > 1)
            {
                //采样率降低2倍
                decodingOptions.inSampleSize *= discount;
            }
            else
            {
                //Logger.d(TAG, "normal memory:" + decodingInfo.getImageKey());
            }
            sDiscountMemParams.remove(decodingInfo.getImageKey());
            return decodingOptions;
        }
    }
}
