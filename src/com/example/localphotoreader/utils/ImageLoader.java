package com.example.localphotoreader.utils;

import java.lang.ref.SoftReference;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.LruCache;

public class ImageLoader {
    
    public static ImageLoader mImageLoader = null;
    private static LruCache<String , Bitmap> mMemCache;
    public ImageLoader(){
        int maxMemory = (int)Runtime.getRuntime().maxMemory();
        int cacheSize = maxMemory / 8;
        mMemCache = new LruCache<String, Bitmap>(cacheSize){
            @Override
            protected int sizeOf(String key, Bitmap value) {
                return value.getByteCount();
            }
        };
    }
    
    public static ImageLoader getInstance(){
        if (null == mImageLoader){
            mImageLoader = new ImageLoader();
        }
        
        return mImageLoader;
    }
    
    public Bitmap getBitmapFromMemoryCache(String key){
        return mMemCache.get(key);
    }
    
    public void addBitmapFromMemoryCache(String key , Bitmap bitmap){
        if (null == getBitmapFromMemoryCache(key)){
            mMemCache.put(key, bitmap);
        }
    }
    
    @SuppressWarnings("unchecked")
    public  Bitmap decodeSampledBitmapFromResource(Resources res, int resId,
            int reqWidth, int reqHeight) {
        // ��һ�ν�����inJustDecodeBounds����Ϊtrue������ȡͼƬ��С
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(res, resId, options);
        // �������涨��ķ�������inSampleSizeֵ
        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);
        // ʹ�û�ȡ����inSampleSizeֵ�ٴν���ͼƬ
        options.inJustDecodeBounds = false;
        
        return (Bitmap)new SoftReference(BitmapFactory.decodeResource(res, resId, options)).get();
    }
    
    /** ���û������  options.inSampleSize = 1;  ��ֱ��ʹ��setImageBitmap ʹ���ڴ����һ�� */
    public  Bitmap decodeSampledBitmapFromResource(Resources res, int resId
            ) {
        // ��һ�ν�����inJustDecodeBounds����Ϊtrue������ȡͼƬ��С
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(res, resId, options);
        // �������涨��ķ�������inSampleSizeֵ
        options.inSampleSize = 1;
        // ʹ�û�ȡ����inSampleSizeֵ�ٴν���ͼƬ
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeResource(res, resId, options);
    }

    public static int calculateInSampleSize(BitmapFactory.Options options,
            int reqWidth, int reqHeight) {
        // ԴͼƬ�ĸ߶ȺͿ��
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;
        if (height > reqHeight || width > reqWidth) {
            // �����ʵ�ʿ�ߺ�Ŀ���ߵı���
            final int heightRatio = Math.round((float) height / (float) reqHeight);
            final int widthRatio = Math.round((float) width / (float) reqWidth);
            // ѡ���͸�����С�ı�����ΪinSampleSize��ֵ���������Ա�֤����ͼƬ�Ŀ�͸�
            // һ��������ڵ���Ŀ��Ŀ�͸ߡ�
            inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;
        }
        return inSampleSize;
    }
    
    public static Bitmap decodeSampledBitmapFromResource(String pathName,
            int reqWidth, int reqHeight) {
        // ��һ�ν�����inJustDecodeBounds����Ϊtrue������ȡͼƬ��С
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(pathName, options);
        // �������涨��ķ�������inSampleSizeֵ
        options.inSampleSize = calculateInSampleSize(options, reqWidth , reqHeight);
        // ʹ�û�ȡ����inSampleSizeֵ�ٴν���ͼƬ
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeFile(pathName, options);
    }

}
