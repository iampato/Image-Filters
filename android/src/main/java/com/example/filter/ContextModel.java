package com.example.filter;

import android.content.Context;
import android.graphics.Bitmap;


public class ContextModel {
    public Bitmap image;
    public Context context;

    ContextModel(Bitmap image,Context context){
        this.context = context;
        this.image = image;
    }
}
