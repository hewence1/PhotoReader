package com.example.localphotoreader.adapter;

import java.util.ArrayList;

import com.example.localphotoreader.R;
import com.example.localphotoreader.model.MyPhoto;
import com.example.localphotoreader.utils.ImageLoader;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

public class ViewPagerAdapter extends PagerAdapter{
    Context mContext = null;
    ArrayList<MyPhoto> mList = null;
    
    public ViewPagerAdapter(Context context, ImageLoader imageLoader, ArrayList<MyPhoto> list){
        mContext = context;
        mList = list;
    }
    
    @Override
    public int getCount() {
       // return DataConfig.ALL_IMAGE_IDS.length;
        return Integer.MAX_VALUE;
    }

    @Override
    public boolean isViewFromObject(View arg0, Object arg1) {
        return arg0 == arg1;
    }
    
    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        View view = (View)object;
        container.removeView(view);
    }
    
    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        int index = position % mList.size();
       
        View view = null;
        view = LayoutInflater.from(mContext).inflate(R.layout.details, null);
        view.setTag(index);
        ImageView imageView = (ImageView)view.findViewById(R.id.image);
       // imageView.setImageResource(id);
        Bitmap bitmap = ImageLoader.decodeSampledBitmapFromResource(mList.get(index).getPath(),
                400, 300);
        if (null != bitmap){
            imageView.setImageBitmap(bitmap);
        }else{
            imageView.setImageResource(R.drawable.empty_photo);
        }
        
        container.addView(view);

        return view;
    }

}
