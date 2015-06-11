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
 * ͼƬ���ؿ��<BR>
 * 
 * @author c00211122
 * @version [V1.0, 2013-6-3]
 */
public final class OnlineImageLoader
{
    
    private static ImageLoaderConfiguration sImageLoaderConfig;
    
    private static DiscCacheAware sDiscCacheAware;
    
   
    /**
     * ͼƬѹ������1-100��ֵԽ������Խ��
     */
    private static final int COMPRESS_QUALITY = 100;
    
    /**
     * ���̻����С
     */
    private static final int DISC_CACHE_SIZE = 50 * 1024 * 1024;
    
    /**
     * �ڴ滺���С
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
     * ��ʾͼƬ<BR>
     * 
     * @param uri ͼƬ��ַ
     * @param imageView ͼƬ�ؼ�
     */
    public static void displayImage(String uri, ImageView imageView)
    {
        uri = wrapImgUrl(uri);
        ImageLoader.getInstance().displayImage(uri, imageView);
    }
    
    
    /**
     * ��ʾͼƬ<BR>
     * 
     * @param uri ͼƬ��ַ
     * @param imageView ͼƬ�ؼ�
     * @param builder ͼƬѡ�����
     */
    public static void displayImage(String uri, ImageView imageView,
        DisplayImageOptions.Builder builder)
    {
        DisplayImageOptions options = builder.build();
        uri = wrapImgUrl(uri);
        ImageLoader.getInstance().displayImage(uri, imageView, options);
    }
    
    /**
     * ��ʾͼƬ<BR>
     * 
     * @param uri ͼƬ��ַ
     * @param imageView ͼƬ�ؼ�
     * @param builder ͼƬѡ�����
     * @param defaultImgResId Ĭ��ͼƬ
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
     * ��ʾͼƬ<BR>
     * 
     * @param uri ͼƬ��ַ
     * @param imageView ͼƬ�ؼ�
     * @param builder ͼƬѡ�����
     * @param listener ����ͼƬ״̬������
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
     * ��ʾͼƬ<BR>
     * 
     * @param uri ͼƬ��ַ
     * @param imageView ͼƬ�ؼ�
     * @param builder ͼƬѡ�����
     * @param defaultImgResId Ĭ��ͼƬ
     * @param discount �ۿ�
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
     * ��ʾͼƬ<BR>
     * 
     * @param uri ͼƬ��ַ
     * @param imageView ͼƬ�ؼ�
     * @param defaultImgResId Ĭ��ͼƬ
     */
    public static void displayImage(String uri, ImageView imageView,
        int defaultImgResId)
    {
        displayImage(uri, imageView, defaultImgResId, 1);
    }
       
    /**
     * ��ʾͼƬ<BR>
     * 
     * @param uri ͼƬ��ַ
     * @param imageView ͼƬ�ؼ�
     * @param defaultImgResId Ĭ��ͼƬ
     * @param discount �ۿ�
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
     * ��ʾͼƬ<BR>
     * 
     * @param uri ͼƬ��ַ
     * @param imageView ͼƬ�ؼ�
     * @param defaultImgResId Ĭ��ͼƬ
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
     * ��ȡĬ�ϵ�ͼƬѡ�����<BR>
     * Ĭ������ͼƬ�ڴ滺���ͼƬSD������
     * 
     * @return ͼƬѡ�����
     */
    public static DisplayImageOptions.Builder getDefaultDisplayImageOptionsBuilder()
    {
        return new DisplayImageOptions.Builder().cacheInMemory().cacheOnDisc();
    }
    
    /**
     * ��ȡ���̻���<BR>
     * 
     * @return ���̻���
     */
    public static DiscCacheAware getDiscCacheAware()
    {
        return sDiscCacheAware;
    }
    
    /**
     * 
     * ��ȡĬ�ϵ�ͼƬѡ�����<BR>
     * Ĭ������ͼƬ�ڴ滺�滺��
     * 
     * @return ͼƬѡ�����
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
     * ����ͼƬ��disc
     * ��ʱֻ��offline stream �õ�
     *  
     * @param uri ͼƬuri·��
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
     * ͼƬ������<BR>
     * ��ͼƬ���н��룬������Ҫ���ڴ�����Ż�
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
                //�����ʽ���2��
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
