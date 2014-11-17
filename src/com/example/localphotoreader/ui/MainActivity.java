package com.example.localphotoreader.ui;


import com.example.localphotoreader.R;
import com.example.localphotoreader.adapter.MyPhoteDirAdapter;
import com.example.localphotoreader.data.DataConfig;
import com.example.localphotoreader.model.MyPhoto;
import com.example.localphotoreader.model.MyPhotoDir;
import com.example.localphotoreader.utils.ImageLoader;
import com.example.localphotoreader.utils.LogUtils;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;

public class MainActivity extends Activity{
    private final int REQUEST_MY_LIST = 111;
    private MyPhoteDirAdapter mAdapter = null;
    public static ImageLoader  mImageLoader = null;
    private GridView mGridView = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
        findData();
    }
    
    /**
     * 找到本地的所有的图片
     */
    @SuppressLint("InlinedApi")
    private void findData() {
        mImageLoader = new ImageLoader();
        Cursor c = getContentResolver().query(DataConfig.PHOTO_URI, null, null, null, null);
        if (null != c && c.moveToFirst()){
            do{
                MyPhoto myPhoto = new MyPhoto();
                String path = c.getString(c.getColumnIndex(MediaStore.Images.Media.DATA));
                int index = path.lastIndexOf("/");
                if (!TextUtils.isEmpty(path)){
                    myPhoto.setPath(path);
                }
                String pathDir = path.substring(0, index);
                if (null != pathDir && path.length() >= 10){
                    myPhoto.setPathDir(pathDir);
                    joinItemToDir(myPhoto);
                }
                String displayName = c.getString(c.getColumnIndex(MediaStore.Images.Media.DISPLAY_NAME));
                if (!TextUtils.isEmpty(displayName)){
                    myPhoto.setDisplayName(displayName);
                }
                String type = c.getString(c.getColumnIndex(MediaStore.Images.Media.MIME_TYPE));
                if (!TextUtils.isEmpty(type)){
                    myPhoto.setType(type);
                }
                String title = c.getString(c.getColumnIndex(MediaStore.Images.Media.TITLE));
                if (!TextUtils.isEmpty(title)){
                    myPhoto.setTitle(title);
                }
                int size = c.getInt(c.getColumnIndex(MediaStore.Images.Media.SIZE));
                if (size > 0){
                    myPhoto.setSize(size);
                }
                int width = c.getInt(c.getColumnIndex(MediaStore.Images.Media.WIDTH));
                if (width > 0){
                    myPhoto.setWidth(width);
                }
                int height = c.getInt(c.getColumnIndex(MediaStore.Images.Media.HEIGHT));
                if (height > 0){
                    myPhoto.setHeight(height);
                }
            }while(c.moveToNext());
        }
        LogUtils.i("DataConfig.mPhotoDirList.size() = " + DataConfig.mPhotoDirList.size());
        for (MyPhotoDir dir : DataConfig.mPhotoDirList){
            LogUtils.i("dir =====> path = " + dir.getDirPath() + "size = " + dir.getCount());
        }
        AddNetWorkDir();
        mAdapter = new MyPhoteDirAdapter(DataConfig.mPhotoDirList, MainActivity.this);
        mGridView.setAdapter(mAdapter);
        mGridView.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View view, int position, long arg3) {
                LogUtils.i("onItemClick position = " + position);
                if (position == mAdapter.getCount() - 1){
                    Intent it = new Intent(MainActivity.this , MyNetworkActivity.class);
                    startActivity(it);
                }else{
                    Intent it = new Intent(MainActivity.this , MyListActivity.class);
                    it.putExtra("index", position);
                    startActivityForResult(it, REQUEST_MY_LIST);
                }
            }
        });
    }

    /**
     * Add a network dir
     */
   private void AddNetWorkDir() {
       MyPhotoDir dir = new MyPhotoDir(DataConfig.NetWorkPath);
       if (!DataConfig.mPhotoDirList.contains(dir)){
           dir.setmThumb(getResources().getDrawable(R.drawable.network));
           DataConfig.mPhotoDirList.add(dir);
       }
      
        
    }

private void joinItemToDir(MyPhoto photo) {
        String stringDir = photo.getPathDir();
        MyPhotoDir mPhotoDir = null;
        if (!TextUtils.isEmpty(stringDir)){
            if (null == (mPhotoDir = findPhotoDir(stringDir)) ){  // 不存在这个目录
                mPhotoDir = new MyPhotoDir(stringDir);
                DataConfig.mPhotoDirList.add(mPhotoDir);
            } 
            mPhotoDir.add(photo);
        }
    }
    
    
    /**
     * @return  是否存在这个图片目录
     * 不存在的话就返回null
     */
    private MyPhotoDir findPhotoDir(String pathDir) {
        for (MyPhotoDir dir : DataConfig.mPhotoDirList){
            if (dir.getDirPath().equals(pathDir)){
                return dir;
            }
        }
        return null;
    }

    /**
     * 初始化控件
     */
    private void initViews() {
        mGridView = (GridView)findViewById(R.id.mGridView);
    }
    
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        LogUtils.i("requestCode = " + requestCode + "resultCode = " + resultCode + "data = " + data);
        if (resultCode == RESULT_OK &&  requestCode == REQUEST_MY_LIST){
            if (null != data){
            }
            
        }
    }
    
}
