package com.example.localphotoreader.ui;

import com.example.localphotoreader.R;
import com.example.localphotoreader.adapter.NetworkAdapter;
import com.example.localphotoreader.data.DataConfig;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.GridView;

public class MyNetworkActivity extends Activity{
    private static final String TAG = null;
    GridView mGridView = null;
    NetworkAdapter mAdapter = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("Network");
        setContentView(R.layout.network_layout);
        
        findView();
        fillData();
    }

    private void findView() {
        mGridView = (GridView)findViewById(R.id.network_gridview);
    }
    

    private void fillData() {
        mAdapter = new NetworkAdapter(this, 0, DataConfig.ALL_IMAGE_URLS);
        mGridView.setAdapter(mAdapter);
    }
    
    @Override
    protected void onPause() {
        super.onPause();
        mAdapter.fluchCache();
    }
    
    @Override
    protected void onDestroy() {
        super.onDestroy();
        mAdapter.cancelAllTasks();
    }
}
