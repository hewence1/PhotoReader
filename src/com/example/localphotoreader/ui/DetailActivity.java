
package com.example.localphotoreader.ui;

import com.example.localphotoreader.R;
import com.example.localphotoreader.adapter.ViewPagerAdapter;
import com.example.localphotoreader.data.DataConfig;
import com.example.localphotoreader.utils.DepthPageTransformer;
import com.example.localphotoreader.utils.ImageLoader;
import com.example.localphotoreader.utils.LogUtils;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.widget.TextView;
import android.app.Activity;
import android.content.Intent;

public class DetailActivity extends Activity {
    protected static final String TAG = "LocalphotoReader";
    ViewPager mViewPager = null;
    TextView mTextView = null;
    ImageLoader  mImageLoader = null;
    int mCurPosition = 0;
    int index = 0 ;
    int size = 0;
    
    /** û��ʹ��decodeSampledBitmapFromResource֮ǰ��ʹ���ڴ���10.203M  13��ͼƬ */
    /** û��ʹ��decodeSampledBitmapFromResource֮ǰ��ʹ���ڴ���30.391M  48��ͼƬ */
    /** û��ʹ����ʹ��decodeSampledBitmapFromResource(1024,720)�ڴ���24.61M  48��ͼƬ */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        Intent it = getIntent();
        if (null != it){
            mCurPosition = it.getIntExtra("position", mCurPosition);
            index = it.getIntExtra("index", mCurPosition);
        }
        mImageLoader = new ImageLoader();
        initViews();
    }

    private void initViews() {
        mViewPager = (ViewPager)findViewById(R.id.view_pager);
        mViewPager.setAdapter(new ViewPagerAdapter(DetailActivity.this, mImageLoader,
                DataConfig.mPhotoDirList.get(index).getmList()));
        size = DataConfig.mPhotoDirList.get(index).getmList().size();
        mViewPager.setCurrentItem(mCurPosition);
        mViewPager.setOnPageChangeListener(new OnPageChangeListener() {
            
            @Override
            public void onPageSelected(int position ) {
                LogUtils.i(TAG , "onPageSelected  position = " + position);
                mTextView.setText( (position  %  size + 1) + "/" +  size);
                mCurPosition = position;
            }
            
            @Override
            public void onPageScrolled(int position, float arg1, int arg2) {
            }
            
            @Override
            public void onPageScrollStateChanged(int arg0) {
                
            }
        });
       // mViewPager.setPageTransformer(true, new ZoomOutPageTransformer()) ;
        mViewPager.setPageTransformer(true, new DepthPageTransformer()) ;
        mTextView = (TextView)findViewById(R.id.text);
        mTextView.setText((mCurPosition + 1) + "/" +  size);
    }
    
    @Override
    protected void onPause() {
        super.onPause();
    }
    
    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
    
    @Override
    public void onBackPressed() {
        LogUtils.i(TAG , "onPageSelected setResult ");
        Intent intent = new Intent();
        intent.putExtra("position", mCurPosition);
        setResult(RESULT_OK, intent);
        super.onBackPressed();
    }
}
