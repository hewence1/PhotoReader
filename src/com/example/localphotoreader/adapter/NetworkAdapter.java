package com.example.localphotoreader.adapter;


import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileDescriptor;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashSet;

import com.example.localphotoreader.R;

import libcore.io.DiskLruCache;
import libcore.io.DiskLruCache.Snapshot;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.os.AsyncTask;
import android.os.Environment;
import android.support.v4.util.LruCache;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

public class NetworkAdapter extends ArrayAdapter<String>{
    private static final String TAG = "NetworkAdapter";
    private HashSet<BitmapWorkerTask> taskCollection; 
    private Context mContxt = null;
    private LruCache<String , Bitmap>  memCache ;
    private DiskLruCache  mDiskLruCache;
    
    public NetworkAdapter(Context context, int textViewResourceId,
           String[] objects) {
        super(context,  textViewResourceId, objects);
        mContxt = context;
        
        taskCollection = new HashSet<BitmapWorkerTask>();
        int maxSize = (int)Runtime.getRuntime().maxMemory();
        memCache = new LruCache<String, Bitmap>(maxSize / 8){
            @Override
            protected int sizeOf(String key, Bitmap value) {
                return value.getRowBytes() * value.getHeight();
            }
        };
        
        try {
            File cache = getDiskCacheDir(context , "thumb");
            mDiskLruCache = DiskLruCache.open(cache, getCurVersion(), 1, 10 * 1024 * 1024);
            if (!cache.exists()){
                cache.mkdirs();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    @Override
    public View getView(int position, View view, ViewGroup parent) {
        final String urlString = getItem(position);
        if (null == view){
            view = LayoutInflater.from(mContxt).inflate(R.layout.network_detail, null);
        }         
        ImageView mImageView = (ImageView)view.findViewById(R.id.network_image);
        mImageView.setTag(urlString);
        mImageView.setImageResource(R.drawable.empty_photo);
        loadBitmap(mImageView , urlString);
        
        return view;
    }
    
    /**
     * 加载Url对应的图片到imageView
     * @param imageView
     * @param url
     */
    private void loadBitmap(ImageView imageView, String url) {
        try {
            Bitmap  bitmap = getBitmapForMemery(url);
            if (null == bitmap){
                BitmapWorkerTask task = new BitmapWorkerTask(imageView);
                taskCollection.add(task);
                task.execute(url);
            }else{
                imageView.setImageBitmap(bitmap);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private Bitmap getBitmapForMemery(String url) {
        return memCache.get(url);
    }

    /**
     * 
     * @return  get the current Version of Application
     */
    private int getCurVersion() {
        
        try {  
            PackageInfo info = mContxt.getPackageManager().getPackageInfo(mContxt.getPackageName(),  
                    0);  
            return info.versionCode;  
        } catch (NameNotFoundException e) {  
            e.printStackTrace();  
        }  
        return 1;  
    }



    /**
     * 
     * @param context
     * @param string
     * @return  得到cache的路径
     */
    private File getDiskCacheDir(Context context, String name) {
        String cachePath;  
        cachePath = context.getCacheDir().getPath();  
        
        return new File(cachePath + File.separator + name);  
    }




    public class BitmapWorkerTask extends AsyncTask<String, Void, Bitmap>{
        private String imageUrl ;
        private ImageView mImageView = null;
        public BitmapWorkerTask(ImageView imageView) {
            mImageView = imageView;
        }

        @Override
        protected Bitmap doInBackground(String... params) {
            imageUrl = params[0];
            FileDescriptor fileDescriptor = null;
            FileInputStream fileInputStream = null;
            Snapshot snapshot = null;
            try {
                final String key = hashKeyForDisk(imageUrl);
                snapshot = mDiskLruCache.get(key);
                if (null == snapshot) {
                    DiskLruCache.Editor editor = mDiskLruCache.edit(key);
                    if (null != editor) {
                        OutputStream outputStream = editor.newOutputStream(0);
                        if (downloadUrlToStream(imageUrl, outputStream)) {
                            editor.commit();
                        } else {
                            editor.abort();
                        }
                    }
                    snapshot = mDiskLruCache.get(key);
                }

                if (null != snapshot) {
                    fileInputStream = (FileInputStream)snapshot.getInputStream(0);
                    fileDescriptor = fileInputStream.getFD();
                }
                Bitmap bitmap = null;
                if (null != fileDescriptor) {
                    BitmapFactory.Options options = new Options();
                    options.inSampleSize = 4;
                    bitmap = BitmapFactory.decodeFileDescriptor(fileDescriptor , null , options);
                }
                if (null != bitmap) {
                    addBitmapToMemoryCache(imageUrl, bitmap);
                }

                return bitmap;
            } catch (Exception e) {
               e.printStackTrace();
            }finally{
                Log.d(TAG , "fileDescriptor = " + fileDescriptor + "fileInputStream = " + fileInputStream);
                if (fileDescriptor == null && fileInputStream != null){
                    try {
                        fileInputStream.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
            
            return null;
        }
        
        @Override
        protected void onPostExecute(Bitmap result) {
            Log.d(TAG , "onPostExecute  result = " + result + "mImageView = " + mImageView);
            if (null != result  &&  null != mImageView){
                mImageView.setImageBitmap(result);
            }
            taskCollection.remove(this);
        }
    }
    
    /** 
     * 使用MD5算法对传入的key进行加密并返回。 
     */  
    public String hashKeyForDisk(String key) {  
        String cacheKey;  
        try {  
            final MessageDigest mDigest = MessageDigest.getInstance("MD5");  
            mDigest.update(key.getBytes());  
            cacheKey = bytesToHexString(mDigest.digest());  
        } catch (NoSuchAlgorithmException e) {  
            cacheKey = String.valueOf(key.hashCode());  
        }  
        return cacheKey;  
    }  
    
    public void addBitmapToMemoryCache(String imageUrl, Bitmap bitmap) {
        if (null == getBitmapForMemery(imageUrl)){
            memCache.put(imageUrl, bitmap);
        }
        
    }

    /** 
     * 建立HTTP请求，并获取Bitmap对象。 
     *  
     * @param imageUrl 
     *            图片的URL地址 
     * @return 解析后的Bitmap对象 
     */  
    private boolean downloadUrlToStream(String urlString, OutputStream outputStream) {  
        Log.i(TAG , "downloadUrlToStream  urlString = " + urlString);
        HttpURLConnection urlConnection = null;  
        BufferedOutputStream out = null;  
        BufferedInputStream in = null;  
        try {  
            final URL url = new URL(urlString);  
            urlConnection = (HttpURLConnection) url.openConnection();  
            urlConnection.setConnectTimeout(10 * 1000);
            urlConnection.setReadTimeout(10 * 1000);
            in = new BufferedInputStream(urlConnection.getInputStream(), 8 * 1024);  
            out = new BufferedOutputStream(outputStream, 8 * 1024);  
            int b;  
            while ((b = in.read()) != -1) {  
                out.write(b);  
            }  
            Log.i(TAG , "downloadUrlToStream  return true");
            return true;  
        } catch (final IOException e) {  
            e.printStackTrace();  
        } finally {  
            if (urlConnection != null) {  
                urlConnection.disconnect();  
            }  
            try {  
                if (out != null) {  
                    out.close();  
                }  
                if (in != null) {  
                    in.close();  
                }  
            } catch (final IOException e) {  
                e.printStackTrace();  
            }  
        }  
        Log.i(TAG , "downloadUrlToStream  return false");
        return false;  
    }  
 

    private String bytesToHexString(byte[] bytes) {  
        StringBuilder sb = new StringBuilder();  
        for (int i = 0; i < bytes.length; i++) {  
            String hex = Integer.toHexString(0xFF & bytes[i]);  
            if (hex.length() == 1) {  
                sb.append('0');  
            }  
            sb.append(hex);  
        }  
        return sb.toString();  
    }  
    
    /** 
     * 取消所有正在下载或等待下载的任务。 
     */  
    public void cancelAllTasks() {  
        if (taskCollection != null) {  
            for (BitmapWorkerTask task : taskCollection) {  
                task.cancel(false);  
            }  
        }  
    }  
    
    /** 
     * 将缓存记录同步到journal文件中。 
     */  
    public void fluchCache() {  
        if (mDiskLruCache != null) {  
            try {  
                mDiskLruCache.flush();  
            } catch (IOException e) {  
                e.printStackTrace();  
            }  
        }  
    } 
    
    
}
