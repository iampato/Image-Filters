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
        switch (call.method) {
            case "generateFilters":
                byte[] bytesFromDart = (byte[]) call.arguments;
                Bitmap bitmap = BitmapFactory.decodeByteArray(bytesFromDart, 0, bytesFromDart.length);
                applyFilter(bitmap, result);
                break;
            case "final_output":
                byte[] finalBytes = (byte[]) call.arguments;
                createTemporaryImageFile(finalBytes, result);
                break;
        }
    }

    private void applyFilter(Bitmap bitmap, MethodChannel.Result result) {
        try {
            List<byte[]> response = new BackgroundTask().execute(bitmap).get();
            result.success(response);
        } catch (Exception e) {
            result.error("Execution", e.toString(), null);
        }
    }

    private void createTemporaryImageFile(byte[] imageBytes, MethodChannel.Result result) {

        File directory = context.getCacheDir();
        String name = "image_crop_" + UUID.randomUUID().toString();
        try {


            File f = File.createTempFile(name, ".jpg", directory);
            FileOutputStream fos = new FileOutputStream(f);

            fos.write(imageBytes);
            fos.flush();
            fos.close();
            result.success(f.getPath());
        } catch (IOException e) {
            result.error("DIRECTORY", e.toString(), null);
        }
    }
}
