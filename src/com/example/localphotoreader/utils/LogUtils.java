package com.example.localphotoreader.utils;

import android.util.Log;

public class LogUtils {
    final static int DEBUG_VERBOSE = 6;
    final static int DEBUG_DEBUG = 5;
    final static int DEBUG_INFO = 4;
    final static int DEBUG_WARN = 3;
    final static int DEBUG_ERROR = 2;
    final static int DEBUG_ASSERT = 1;
    static int debugLevel = 6;
    
    private static final String TAG = "Update_Log";

    public void v(String msg){
        if (debugLevel >= DEBUG_VERBOSE){
            Log.v(TAG, "vvvv" + msg);
        }
    }
    
    public void v(String TAG , String msg){
        if (debugLevel >= DEBUG_VERBOSE){
            Log.v(TAG, "vvvv" + msg);
        }
    }
    public static void d(String msg){
        if (debugLevel >= DEBUG_DEBUG){
            Log.d(TAG, "dddd" + msg);
        }
    }
    
    public void d(String TAG , String msg){
        if (debugLevel >= DEBUG_DEBUG){
            Log.d(TAG, "dddd" + msg);
        }
    }
    
    public static void i(String msg){
        if (debugLevel >= DEBUG_INFO){
            Log.i(TAG, "iiii====" + msg);
        }
    }
    public static void i(String TAG , String msg){
        if (debugLevel >= DEBUG_INFO){
            Log.i(TAG, "iiii====" + msg);
        }
    }
    
    public static void w(String msg){
        if (debugLevel >= DEBUG_WARN){
            Log.i(TAG, "wwww" + msg);
        }
    }
    
    public static void w(String TAG,String msg){
        if (debugLevel >= DEBUG_WARN){
            Log.i(TAG, "wwww" + msg);
        }
    }
    
    public static void e(String msg){
        if (debugLevel >= DEBUG_ERROR){
            Log.i(TAG, "eeee" + msg);
        }
    }
    
    public static void e(String TAG , String msg){
        if (debugLevel >= DEBUG_ERROR){
            Log.i(TAG, "eeee" + msg);
        }
    }
    
    
    
    public static int getDebugLevel() {
        return debugLevel;
    }
    
    public static void setDebugLevel(int debugLevel) {
        LogUtils.debugLevel = debugLevel;
    }
    
    
    
}
