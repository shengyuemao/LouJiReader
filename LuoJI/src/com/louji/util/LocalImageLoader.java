package com.louji.util;

import java.io.InputStream;
import java.lang.reflect.Field;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.graphics.drawable.BitmapDrawable;
import android.support.v4.util.LruCache;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;

import com.nostra13.universalimageloader.core.assist.ImageSize;
import com.nostra13.universalimageloader.core.assist.MemoryCacheUtil;
import com.nostra13.universalimageloader.core.assist.ViewScaleType;
import com.nostra13.universalimageloader.core.download.ImageDownloader;
import com.nostra13.universalimageloader.utils.ImageSizeUtils;
import com.nostra13.universalimageloader.utils.IoUtils;

/**
 * 
 * 本地drawable下图片加载<BR>
 * 为了节省内存，对一些大本地图片的加载进行特殊处理
 * 
 * @author c00211122
 * @version [V1.0, 2013-7-11]
 */
public final class LocalImageLoader
{
    private static final String TAG = "LocalImageLoader";
    
    private static LocalImageLoader instance;
    
    private static final int MAX_HEIGHT = 1280;
    
    private static final int MAX_WIDTH = 720;
    
    /**
     * 内存缓存大小，8M
     */
    private static final int MEMORY_CACHE_SIZE = 8 * 1024 * 1024;
    
    private LruCache<String, Bitmap> mCache = new LruCache<String, Bitmap>(
        MEMORY_CACHE_SIZE);
    
    private LocalImageLoader()
    {
        super();
    }
    
    /**
     * 
     * 获取单例<BR>
     * 
     * @return 单例
     */
    public static synchronized LocalImageLoader getInstance()
    {
        if (instance == null)
        {
            instance = new LocalImageLoader();
        }
        return instance;
    }
    
    /**
     * 
     * 显示本地图片<BR>
     * 
     * @param view 视图控件
     * @param resId 图片资源id
     * @param displayer 处理图片显示逻辑
     * @param <T> 控件类型
     */
    public <T extends View> void displayImage(T view, int resId,
        IBitmapDisplayer<T> displayer)
    {
        Bitmap bitmap = readBitmap(view, resId, null);
        IBitmapDisplayer<T> d = displayer;
        if (d == null)
        {
            d = new IBitmapDisplayer<T>()
            {
                @Override
                public void display(T view, Bitmap bitmap)
                {
                    if (view instanceof ImageView)
                    {
                        ImageView imageView = (ImageView) view;
                        imageView.setImageBitmap(bitmap);
                    }
                    else
                    {
                        view.setBackgroundDrawable(new BitmapDrawable(bitmap));
                    }
                }
            };
        }
        d.display(view, bitmap);
    }
    
    /**
     * 
     * 显示本地图片<BR>
     * 
     * @param view 视图控件
     * @param resId 图片资源id
     * @param 图片尺寸
     */
    public void displayImage(View view, int resId)
    {
        displayImage(view, resId, null);
    }
    
    private ImageSize defineImageSize(View view, int resId)
    {
        InputStream is =
            view.getContext().getResources().openRawResource(resId);
        ImageSize imageSize = defineImageSizeAndRotation(is);
        return imageSize;
    }
    
    private int getViewFieldValue(Object object, String fieldName)
    {
        int value = 0;
        if (!(object instanceof ImageView))
        {
            return value;
        }
        
        try
        {
            Field field = ImageView.class.getDeclaredField(fieldName);
            field.setAccessible(true);
            int fieldValue = (Integer) field.get(object);
            if (fieldValue > 0 && fieldValue < Integer.MAX_VALUE)
            {
                value = fieldValue;
            }
        }
        catch (Exception e)
        {
            //do nothing
            Log.e(TAG, "getViewFieldValue, ", e);
        }
        return value;
    }
    
    /**
     * 获取缓存的Bitmap<BR>
     * [功能详细描述]
     * 
     * @param cacheKey Bitmap缓存键值
     * @return Bitmap
     */
    public Bitmap getBitmapByCacheKey(String cacheKey)
    {
        return mCache.get(cacheKey);
    }
    
    /**
     * 缓存Bitmap<BR>
     * [功能详细描述]
     * 
     * @param cacheKey Bitmap缓存键值
     * @param bitmap 位图对象
     */
    public void cacheBitmap(String cacheKey, Bitmap bitmap)
    {
        mCache.put(cacheKey, bitmap);
    }
    
    private Bitmap readBitmap(View view, int resId, ImageSize size)
    {
        ImageSize targetSize;
        
        if (size != null)
        {
            targetSize = size;
        }
        else
        {
            targetSize = defineTargetSizeForView(view, MAX_WIDTH, MAX_HEIGHT);
        }
        String uri =
            ImageDownloader.Scheme.DRAWABLE.wrap(String.valueOf(resId));
        //Logger.d(TAG, "uri:" + uri);
        String memoryCacheKey = MemoryCacheUtil.generateKey(uri, targetSize);
        Bitmap bitmap = mCache.get(memoryCacheKey);
        if (bitmap != null)
        {
            //Logger.d(TAG, "get from cache:" + bitmap);
            return bitmap;
        }
        ImageSize imageSize = defineImageSize(view, resId);
        //        ImageScaleType scaleType = ImageScaleType.IN_SAMPLE_POWER_OF_2;
        //        targetSize = targetSize.scaleDown(2);
        int scale = 1;
        ViewScaleType viewScaleType = null;
        if (view instanceof ImageView)
        {
            viewScaleType = ViewScaleType.fromImageView((ImageView) view);
        }
        else
        {
            viewScaleType = ViewScaleType.FIT_INSIDE;
        }
        if (viewScaleType == null)
        {
            viewScaleType = ViewScaleType.FIT_INSIDE;
        }
        boolean powerOf2 = true;
        //根据图片的尺寸和控件大小计算图片采样率
        scale =
            ImageSizeUtils.computeImageSampleSize(imageSize,
                targetSize,
                viewScaleType,
                powerOf2);
        
        Log.d(TAG, "scale:" + scale);
        Options decodingOptions = new Options();
        //        decodingOptions.inPreferredConfig = Bitmap.Config.RGB_565;
        decodingOptions.inSampleSize = scale;
        decodingOptions.inPurgeable = true;
        
        bitmap = readBitMap(view.getContext(), resId, decodingOptions);
        
        if (memoryCacheKey != null && bitmap != null)
        {
            mCache.put(memoryCacheKey, bitmap);
        }
        return bitmap;
    }
    
    private ImageSize defineImageSizeAndRotation(InputStream imageStream)
    {
        
        Options options = new Options();
        options.inJustDecodeBounds = true;
        try
        {
            BitmapFactory.decodeStream(imageStream, null, options);
        }
        finally
        {
            IoUtils.closeSilently(imageStream);
        }
        Log.d(TAG, "defineImageSizeAndRotation, image width:"
            + options.outWidth + ", image height:" + options.outHeight);
        return new ImageSize(options.outWidth, options.outHeight, 0);
        
    }
    
    private ImageSize defineTargetSizeForView(View view, int maxImageWidth,
        int maxImageHeight)
    {
        final DisplayMetrics displayMetrics =
            view.getContext().getResources().getDisplayMetrics();
        
        LayoutParams params = view.getLayoutParams();
        
        if (params == null)
        {
            params =
                new LayoutParams(LayoutParams.WRAP_CONTENT,
                    LayoutParams.WRAP_CONTENT);
        }
        
        int width =
            params.width == LayoutParams.WRAP_CONTENT ? 0 : view.getWidth(); // Get actual image width
        if (width <= 0)
        {
            width = params.width; // Get layout width parameter
        }
        if (width <= 0)
        {
            width = getViewFieldValue(view, "mMaxWidth"); // Check maxWidth parameter
        }
        
        if (width <= 0)
        {
            width = displayMetrics.widthPixels;
        }
        
        int height =
            params.height == LayoutParams.WRAP_CONTENT ? 0 : view.getHeight(); // Get actual image height
        if (height <= 0)
        {
            height = params.height; // Get layout height parameter
        }
        if (height <= 0)
        {
            height = getViewFieldValue(view, "mMaxHeight"); // Check maxHeight parameter
        }
        
        if (height <= 0)
        {
            height = displayMetrics.heightPixels;
        }
        //Logger.d(TAG, "target width:" + width);
        //Logger.d(TAG, "target height" + height);
        return new ImageSize(width, height);
    }
    
    private Bitmap readBitMap(Context context, int resId,
        BitmapFactory.Options opt)
    {
        //获取资源图片    
        InputStream is = null;
        try
        {
            is = context.getResources().openRawResource(resId);
            return BitmapFactory.decodeStream(is, null, opt);
        }
        catch (Exception e)
        {
            Log.e(TAG, "readBitMap, exception occurs, ", e);
            return null;
        }
        finally
        {
            if (is != null)
            {
                IoUtils.closeSilently(is);
            }
        }
    }
    
    /**
     * 
     * 处理bitmap显示<BR>
     * 
     * @author c00211122
     * @version [V1.0, 2013-7-11]
     */
    public static interface IBitmapDisplayer<T extends View>
    {
        /**
         * 
         * 把bitmap显示到控件上<BR>
         * 
         * @param view 视图控件
         * @param bitmap 图片
         */
        void display(T view, Bitmap bitmap);
    }
}
