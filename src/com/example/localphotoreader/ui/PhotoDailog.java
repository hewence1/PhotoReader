package com.example.localphotoreader.ui;

import com.example.localphotoreader.R;
import com.example.localphotoreader.model.MyPhoto;
import com.example.localphotoreader.utils.ImageLoader;
import com.example.localphotoreader.utils.LogUtils;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

public class PhotoDailog extends Dialog{
    private String path;
    private String displayName;
    private String  type;
    private String title;
    private int size;
    private int width;
    private int height;
    private Context mContext = null;
    private MyPhoto mPhoto;
    
    private ImageView logo = null;
    private TextView mTitle = null;
    private TextView mPath = null;
    private TextView mName = null;
    private TextView mSize = null;
    private TextView mType = null;
    private TextView mWidth = null;
    private TextView mHeight = null;
    
    
    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
        LogUtils.i("PhotoDailog  setPath " + " mContext = " + mContext   + "path = " + path);
        if (null != mPath){
            LogUtils.i("PhotoDailog  setPath " + " mContext = " + mContext   + "path = " + path);
            mPath.setText(mContext.getString(R.string.path) + path);
        }
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        LogUtils.i("PhotoDailog  setDisplayName " + " mContext = " + mContext   + "displayName = " + displayName);
        this.displayName = displayName;
        if (null != mName){
            LogUtils.i("PhotoDailog  setDisplayName " + " mContext = " + mContext   + "displayName = " + displayName);
            mName.setText(mContext.getString(R.string.displayname) + displayName);
        }
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        LogUtils.i("PhotoDailog  setType " + " mContext = " + mContext   + "type = " + type);
        this.type = type;
        if (null != mType){
            mType.setText(mContext.getString(R.string.type) + type);
        }
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        LogUtils.i("PhotoDailog  setTitle " + " mContext = " + mContext   + "title = " + title);
        this.title = title;
        if (null != mTitle){
            mTitle.setText(title);
            mTitle.setTextColor(Color.RED);
        }
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
        if (null != mSize){
            mSize.setText(mContext.getString(R.string.size) + size);
        }
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
        if (null != mWidth){
            mWidth.setText(mContext.getString(R.string.width) + width);
        }
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
        if (null != mHeight){
            mHeight.setText(mContext.getString(R.string.height) + height);
        }
    }
    
    public void setLog(Bitmap log){
        if (null != logo){
            logo.setImageBitmap(log);
        }
    }

    public PhotoDailog(Context context, int theme, MyPhoto photo) {
        super(context, theme);
        LogUtils.i("PhotoDailog " + " mContext = " + mContext);
        mContext = context;
        mPhoto = photo;
    }
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.dialogview1);
        LogUtils.i("PhotoDailog " + " onCreate ");
        
        logo = (ImageView)findViewById(R.id.logo);
        mTitle = (TextView)findViewById(R.id.dialog_title);
        mPath = (TextView)findViewById(R.id.path);
        mName = (TextView)findViewById(R.id.dispalyname);
        mType = (TextView)findViewById(R.id.type);
        mWidth = (TextView)findViewById(R.id.width);
        mHeight = (TextView)findViewById(R.id.height);
        mSize = (TextView)findViewById(R.id.size);
        
        setLog(ImageLoader.decodeSampledBitmapFromResource(mPhoto.getPath(), 90, 90));
        setTitle(mPhoto.getTitle());
        setPath(mPhoto.getPath());
        setDisplayName(mPhoto.getDisplayName());
        setType(mPhoto.getType());
        setSize(mPhoto.getSize());
        setWidth(mPhoto.getWidth());
        setHeight(mPhoto.getHeight());
    }

}
