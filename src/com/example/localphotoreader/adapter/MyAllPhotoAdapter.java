
package com.example.localphotoreader.adapter;

import java.util.ArrayList;

import com.example.localphotoreader.R;
import com.example.localphotoreader.model.MyPhoto;
import com.example.localphotoreader.ui.PhotoDailog;
import com.example.localphotoreader.utils.ImageLoader;
import com.example.localphotoreader.utils.LogUtils;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

public class MyAllPhotoAdapter extends BaseAdapter {
    protected static final String TAG = "MyAllPhoteAdapter";

    Context mContext = null;

    ArrayList<MyPhoto> mList = null;

    public MyAllPhotoAdapter(ArrayList<MyPhoto> list, Context context) {
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
    public View getView(final int position, View view, ViewGroup parent) {
        ViewHolder mHolder = null;
        if (null == view) {
            view = LayoutInflater.from(mContext).inflate(R.layout.photo_list, null);
            mHolder = new ViewHolder();
            mHolder.mImage = (ImageView)view.findViewById(R.id.mImage);
            mHolder.mCheckBox = (CheckBox)view.findViewById(R.id.mCheckBox);
            mHolder.mText = (TextView)view.findViewById(R.id.mText);
            mHolder.mButton = (Button)view.findViewById(R.id.mButton);
            mHolder.mImage.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    LogUtils.i(TAG, "mHolder.mImage is clicked");
                }
            });
            mHolder.mCheckBox.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    LogUtils.i(TAG, "mHolder.mCheckBox is clicked");
                }
            });
            mHolder.mText.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    LogUtils.i(TAG, "mHolder.mText is clicked");
                }
            });
            mHolder.mButton.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    showDetailInfo(position);
                }
            });
            view.setTag(mHolder);
        } else {
            mHolder = (ViewHolder)view.getTag();
        }
        mHolder.mText.setText(mList.get(position).getDisplayName());
        MyPhotoAsyncTask myPhotoAsyncTask = new MyPhotoAsyncTask(mHolder.mImage);
        myPhotoAsyncTask.execute(mList.get(position).getPath());
        return view;
    }
    
    //  显示图片的一些相关信息
    private void showDetailInfo(int position) {
        MyPhoto photo = mList.get(position);
        PhotoDailog mDailog = new PhotoDailog(mContext, R.style.dialog , photo);
        mDailog.show();
    }

    public class ViewHolder {
        public ImageView mImage = null;

        public CheckBox mCheckBox = null;

        public TextView mText = null;

        public Button mButton = null;
    }

    public class MyPhotoAsyncTask extends AsyncTask<String, Void, Bitmap> {
        ImageView mImageView = null;

        public MyPhotoAsyncTask(ImageView imageView) {
            mImageView = imageView;
        }

        @Override
        protected Bitmap doInBackground(String... path) {
            Bitmap bitmap = ImageLoader.decodeSampledBitmapFromResource(path[0], 90, 90);
            return bitmap;
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            if (null != mImageView && null != bitmap) {
                mImageView.setImageBitmap(bitmap);
            }
        }

    }

}
