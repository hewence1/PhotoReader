package com.example.localphotoreader.ui;

import com.example.localphotoreader.R;
import com.example.localphotoreader.adapter.MyAllPhotoAdapter;
import com.example.localphotoreader.data.DataConfig;
import com.example.localphotoreader.utils.LogUtils;

import android.app.Activity;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;

public class MyListActivity extends Activity{
    private ListView mList = null;
    private final int REQUEST_DETAIL = 110;
    private MyAllPhotoAdapter adapter = null;
    private int mCurPosition = 0;
    // 第几个文件夹
    private int index = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        Intent it = getIntent();
        if (null != it){
            index = it.getIntExtra("index", index);
        }
        LogUtils.i("MyListActivity index = " + index);
        initViews();
        
    }

    private void initViews() {
        mList = (ListView)findViewById(R.id.list);
        adapter = new MyAllPhotoAdapter(DataConfig.mPhotoDirList.get(index).getmList(),
                MyListActivity.this);
        mList.setAdapter(adapter);
        mList.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View view, int position, long arg3) {
                LogUtils.i("onItemClick position = " + position);
                Intent it = new Intent(MyListActivity.this , DetailActivity.class);
                it.putExtra("position", position);
                it.putExtra("index", index);
                mCurPosition = position;
                startActivityForResult(it, REQUEST_DETAIL);
            }
        }); 
    }
    
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        LogUtils.i("requestCode = " + requestCode + "resultCode = " + resultCode + "data = " + data);
        if (resultCode == RESULT_OK &&  requestCode == REQUEST_DETAIL){
            if (null != data){
                mCurPosition = data.getIntExtra("position", mCurPosition);
                LogUtils.i("requestCode = " + requestCode + "resultCode = " + resultCode
                        + "mCurPosition = " + mCurPosition);
                mList.setSelection(mCurPosition);
            }
            
        }
    }
    
    @Override
    public void onBackPressed() {
        LogUtils.i("" , "MyList setResult ");
        Intent intent = new Intent();
        intent.putExtra("position", mCurPosition);
        setResult(RESULT_OK, intent);
        super.onBackPressed();
    }
}
