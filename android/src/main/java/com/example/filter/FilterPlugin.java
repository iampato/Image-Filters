package com.example.filter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import androidx.annotation.NonNull;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

import io.flutter.embedding.engine.plugins.FlutterPlugin;
import io.flutter.plugin.common.MethodCall;
import io.flutter.plugin.common.MethodChannel;
import io.flutter.plugin.common.PluginRegistry;

public class FilterPlugin implements FlutterPlugin, MethodChannel.MethodCallHandler {

    private Context context;
    private MethodChannel channel;

    public FilterPlugin() {
        System.loadLibrary("NativeImageProcessor");
    }

    public static void registerWith(PluginRegistry.Registrar registrar) {
        FilterPlugin instance = new FilterPlugin();
        instance.channel = new MethodChannel(registrar.messenger(), "filter");
        instance.context = registrar.context();
        instance.channel.setMethodCallHandler(instance);
    }

    @Override
    public void onAttachedToEngine(@NonNull FlutterPluginBinding binding) {
        channel = new MethodChannel(binding.getBinaryMessenger(), "filter");
        context = binding.getApplicationContext();
        channel.setMethodCallHandler(this);
    }

    @Override
    public void onDetachedFromEngine(@NonNull FlutterPluginBinding binding) {
        channel.setMethodCallHandler(null);
        channel = null;
    }

    @Override
    public void onMethodCall(@NonNull MethodCall call, @NonNull MethodChannel.Result result) {
        if (call.method.equals("generateFilters")) {
            String filePathFromDart = (String) call.arguments;
            File imageFile = new File(filePathFromDart);
            applyFilter(imageFile, result);
        } else {
            result.notImplemented();
        }
    }

    private void applyFilter(File image, MethodChannel.Result result) {

        if (image.exists()) {
            Bitmap bitmap = BitmapFactory.decodeFile(image.getPath(), null);
            ContextModel contextModel = new ContextModel(bitmap, context);
            try {
                List<String> response = new BackgroundTask().execute(contextModel).get();
                result.success(response);
            } catch (Exception e) {
                result.error("Execution", e.toString(), null);
            }
        } else {
            result.error("IOException", "File not exist", null);
        }
    }

}