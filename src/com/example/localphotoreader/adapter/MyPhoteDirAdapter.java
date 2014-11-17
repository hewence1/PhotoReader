package com.example.localphotoreader.adapter;

import java.util.ArrayList;

import com.example.localphotoreader.R;
import com.example.localphotoreader.model.MyPhotoDir;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class MyPhoteDirAdapter extends BaseAdapter{
    Context mContext = null;
    ArrayList<MyPhotoDir> mList = null;
    
    public MyPhoteDirAdapter(ArrayList<MyPhotoDir> list , Context context){
        mList = list;
        mContext = context;
    }
    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public Object getItem(int arg0) {
        return mList.get(arg0);
    }

    @Override
    public long getItemId(int arg0) {
        return arg0;
    }
    
     ViewHolder mHolder = null;
    @Override
    public View getView(int position, View view, ViewGroup parent) {
        ViewHolder mHolder = null;
        if (null == view){
            view = LayoutInflater.from(mContext).inflate(R.layout.layout_photodir, null);
            mHolder = new ViewHolder();
            mHolder.mImage = (ImageView)view.findViewById(R.id.photo_thumb);
            mHolder.mText = (TextView)view.findViewById(R.id.photo_name);
            view.setTag(mHolder);
        }else{
            mHolder = (ViewHolder)view.getTag();
        }
        mHolder.mImage.setImageDrawable(mList.get(position).getmThumb());
        mHolder.mText.setText(mList.get(position).getName());
        return view;
    }
    
    public class ViewHolder{
        public ImageView mImage = null;
        public TextView mText = null;
    }

}
