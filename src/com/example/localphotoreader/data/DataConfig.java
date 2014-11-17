package com.example.localphotoreader.data;

import java.util.ArrayList;

import android.net.Uri;
import android.provider.MediaStore;

import com.example.localphotoreader.model.MyPhotoDir;


public class DataConfig {
    public static Uri PHOTO_URI = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
  //  public static ArrayList<MyPhoto> myArrayList = new ArrayList<MyPhoto>();
    public static ArrayList<MyPhotoDir> mPhotoDirList = new ArrayList<MyPhotoDir>(); 
    public static String NetWorkPath = "netWork"; 
    public static final String[] ALL_IMAGE_URLS= {
        "http://192.168.1.79/photo13.jpg",
        "http://192.168.1.79/photo14.jpg",
        "http://192.168.1.79/photo15.jpg",
        "http://192.168.1.79/photo16.jpg",
        "http://192.168.1.79/photo17.jpg",
        "http://192.168.1.79/photo18.jpg",
        "http://192.168.1.79/photo19.jpg",
        "http://192.168.1.79/photo20.jpg",
        "http://192.168.1.79/photo21.jpg",
        "http://192.168.1.79/photo22.jpg",
        "http://192.168.1.79/photo23.jpg",
        "http://192.168.1.79/photo24.jpg",
        "http://192.168.1.79/photo25.jpg",
        "http://192.168.1.79/photo26.jpg",
        "http://192.168.1.79/photo27.jpg",
        "http://192.168.1.79/photo28.jpg",
        "http://192.168.1.79/photo29.jpg",
        "http://192.168.1.79/photo30.jpg",
        "http://192.168.1.79/photo32.jpg",
        "http://192.168.1.79/photo33.jpg",
        "http://192.168.1.79/photo34.jpg",
        "http://192.168.1.79/photo35.jpg",
        "http://192.168.1.79/photo36.jpg",
        "http://192.168.1.79/photo37.jpg",
        "http://192.168.1.79/photo38.jpg",
        "http://192.168.1.79/photo39.jpg",
        "http://192.168.1.79/photo40.jpg",
        "http://192.168.1.79/photo41.jpg",
        "http://192.168.1.79/photo42.jpg",
        "http://192.168.1.79/photo43.jpg",
        "http://192.168.1.79/photo44.jpg",
        "http://192.168.1.79/photo45.jpg",
        "http://192.168.1.79/photo46.jpg",
        "http://192.168.1.79/photo31.jpg",
        "http://192.168.1.79/photo31.jpg",
        "http://192.168.1.79/photo31.jpg",
        "http://192.168.1.79/photo31.jpg",
    };
}
