
package com.example.localphotoreader.model;

import java.util.ArrayList;

import com.example.localphotoreader.utils.ImageLoader;
import com.example.localphotoreader.utils.LogUtils;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;

public class MyPhotoDir {
    private String DirPath;

    private ArrayList<MyPhoto> mList = new ArrayList<MyPhoto>();

    private Drawable mThumb;

    private String Name;
    
    public MyPhotoDir(String dirPath){
        DirPath = dirPath;
    }
    public String getDirPath() {
        return DirPath;
    }
    
    public void setDirPath(String dirPath) {
        DirPath = dirPath;
    }

    public ArrayList<MyPhoto> getmList() {
        return mList;
    }

    public void setmList(ArrayList<MyPhoto> mList) {
        this.mList = mList;
    }

    public int getCount() {
        return mList.size();
    }

    public Drawable getmThumb() {
        if (null == mThumb){
            mThumb = createThumb(400 , 400);
        }
        return mThumb;
    }
    
    /**
     * Éú³ÉËõÂÔÍ¼
     */
    @SuppressWarnings("deprecation")
    private Drawable createThumb(int width , int height) {
        Bitmap output = Bitmap.createBitmap(width, height,
                Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(output);
        canvas.drawARGB(0x99, 0xff,0xff, 0x00);
        final Paint paint = new Paint();
        final int padding = width / 10;
        Bitmap bit0 , bit1 = null , bit2 = null , bit3 = null;
        if (mList.size() >= 4){
            bit0 = ImageLoader.decodeSampledBitmapFromResource(mList.get(0).getPath(), width, height);
            bit1 = ImageLoader.decodeSampledBitmapFromResource(mList.get(1).getPath(), width, height);
            bit2 = ImageLoader.decodeSampledBitmapFromResource(mList.get(2).getPath(), width, height);
            bit3 = ImageLoader.decodeSampledBitmapFromResource(mList.get(3).getPath(), width, height);
        }else if (mList.size() == 3){
            bit0 = ImageLoader.decodeSampledBitmapFromResource(mList.get(0).getPath(), width, height);
            bit1 = ImageLoader.decodeSampledBitmapFromResource(mList.get(1).getPath(), width, height);
            bit2 = ImageLoader.decodeSampledBitmapFromResource(mList.get(2).getPath(), width, height);
        }else if (mList.size() == 2){
            bit0 = ImageLoader.decodeSampledBitmapFromResource(mList.get(0).getPath(), width, height);
            bit1 = ImageLoader.decodeSampledBitmapFromResource(mList.get(1).getPath(), width, height);
        }else if (mList.size() == 1){
            bit0 = ImageLoader.decodeSampledBitmapFromResource(mList.get(0).getPath(), width, height);
        }else{
            return null;
        }
        
        
        canvas.drawBitmap(bit0, new Rect(0, 0, bit0.getWidth(), bit0.getHeight()),
                new Rect(padding / 2, padding / 2, width / 2, height / 2), paint);
        if (null != bit1){
            canvas.drawBitmap(bit1, new Rect(0, 0, bit1.getWidth(), bit1.getHeight()),
                    new Rect(width / 2, padding / 2, width - padding / 2, height / 2), paint); 
        }
        if (null != bit2){
            canvas.drawBitmap(bit2, new Rect(0, 0, bit2.getWidth(), bit2.getHeight()),
                    new Rect(padding / 2, height / 2, width / 2, height - padding / 2), paint);
        }
        if (null != bit3){
            canvas.drawBitmap(bit3, new Rect(0, 0, bit3.getWidth(), bit3.getHeight()),
                    new Rect(width / 2, height / 2, width - padding / 2, height - padding / 2), paint);
        }
        
        return new BitmapDrawable(output);
    }
    
   public void setmThumb(Drawable mThumb) {
        this.mThumb = mThumb;
    }

    public String getName() {
        if (TextUtils.isEmpty(Name)){
            int index = DirPath.lastIndexOf("/");
            Name = DirPath.substring(index + 1);
        }
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public void add(MyPhoto photo) {
        mList.add(photo);
    }
    
    @Override
    public boolean equals(Object o) {
        LogUtils.w("equals Object o");
        if (this == o){
            LogUtils.w("equals Object this == o return  true");
            return true;
        }
        if (o instanceof MyPhotoDir){
            MyPhotoDir dir = (MyPhotoDir)o;
            String pathDir = dir.getDirPath();
            LogUtils.w("equals Object dir.getDirPath() = " + pathDir + "this.getDirPath() = " + this.getDirPath());
            if (pathDir != null  && pathDir.equals(this.getDirPath())){
                LogUtils.w("equals Object path.eques path  return  true");
                return true;
            }
        }
        LogUtils.w("equals Object return false");
        return false;
    }

}
