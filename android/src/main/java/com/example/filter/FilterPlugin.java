package com.example.filter;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import androidx.annotation.NonNull;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import io.flutter.embedding.engine.plugins.FlutterPlugin;
import io.flutter.embedding.engine.plugins.activity.ActivityAware;
import io.flutter.embedding.engine.plugins.activity.ActivityPluginBinding;
import io.flutter.plugin.common.MethodCall;
import io.flutter.plugin.common.MethodChannel.Result;
import io.flutter.plugin.common.MethodChannel;
import io.flutter.plugin.common.PluginRegistry;

public class FilterPlugin implements FlutterPlugin, MethodChannel.MethodCallHandler, ActivityAware {

    private Context context;
    private MethodChannel channel;

    private Activity activity;
    private ExecutorService executor;
    private FlutterPluginBinding pluginBinding;

    public FilterPlugin() {
        System.loadLibrary("NativeImageProcessor");
    }

    public static void registerWith(PluginRegistry.Registrar registrar) {
        if (registrar.activity() == null) {
            // If a background flutter view tries to register the plugin, there will be no activity from the registrar,
            // we stop the registering process immediately because the ImagePicker requires an activity.
            return;
        }
        FilterPlugin instance = new FilterPlugin();
        instance.activity = registrar.activity();
        instance.channel = new MethodChannel(registrar.messenger(), "filter");
        instance.context = registrar.context();
        instance.channel.setMethodCallHandler(instance);
    }

    @Override
    public void onAttachedToEngine(@NonNull FlutterPluginBinding binding) {
        pluginBinding = binding;
        context = binding.getApplicationContext();
    }

    @Override
    public void onDetachedFromEngine(@NonNull FlutterPluginBinding binding) {
    }

    @Override
    public void onMethodCall(@NonNull MethodCall call, @NonNull MethodChannel.Result result) {
        if (call.method.equals("generateFilters")) {
            String filePathFromDart = (String) call.arguments;
            applyFilter(filePathFromDart, result);
        } else if (call.method.equals("final_output")) {
            String path = (String) call.argument("path");
            int filter = call.argument("filter");

            try {
                File srcFile = new File(path);
                Bitmap srcBitmap = BitmapFactory.decodeFile(srcFile.getAbsolutePath(), null);
                ContextModel contextModel = new ContextModel(srcBitmap, context);
                BackgroundTask backgroundTask = new BackgroundTask(contextModel);
                String filename = backgroundTask.finalOutputFilter(filter, srcBitmap);
                if (filename.equals(null) || filename.equals("")) {
                    result.error("INVALID", "error in creating temporary file", null);
                } else {
                    result.success(filename);
                }
            } catch (Exception e) {
                result.error("INVALID", e.toString(), null);
            }

        } else {
            result.notImplemented();
        }
    }

    private synchronized void io(@NonNull Runnable runnable) {
        if (executor == null) {
            executor = Executors.newCachedThreadPool();
        }
        executor.execute(runnable);
    }

    private void ui(@NonNull Runnable runnable) {
        activity.runOnUiThread(runnable);
    }

    private void applyFilter(final String path, final Result result) {
        io(new Runnable() {
            @Override
            public void run() {
                File srcFile = new File(path);
                if (!srcFile.exists()) {
                    ui(new Runnable() {
                        @Override
                        public void run() {
                            result.error("INVALID", "Image source cannot be opened", null);
                        }
                    });
                    return;
                }

                Bitmap srcBitmap = BitmapFactory.decodeFile(path, null);
                if (srcBitmap == null) {
                    ui(new Runnable() {
                        @Override
                        public void run() {
                            result.error("INVALID", "Image source cannot be decoded", null);
                        }
                    });
                    return;
                }

                try {
                    ContextModel contextModel = new ContextModel(srcBitmap, context);
                    BackgroundTask backgroundTask = new BackgroundTask(contextModel);
                    System.out.println("Started processing: " + System.currentTimeMillis());
                    final List<byte[]> response = backgroundTask.doInBackground();
                    System.out.println("Done processing: " + System.currentTimeMillis());
                    ui(new Runnable() {
                        @Override
                        public void run() {
                            result.success(response);
                        }
                    });
                } finally {
                    srcBitmap.recycle();
                }
            }
        });
    }

    @Override
    public void onAttachedToActivity(@NonNull ActivityPluginBinding binding) {
        // V2 embedding setup for activity listeners.
        MethodChannel channel = new MethodChannel(this.pluginBinding.getBinaryMessenger(), "filter");
        this.activity = binding.getActivity();
        channel.setMethodCallHandler(this);
    }

    @Override
    public void onDetachedFromActivityForConfigChanges() {

    }

    @Override
    public void onReattachedToActivityForConfigChanges(@NonNull ActivityPluginBinding binding) {
        onAttachedToActivity(binding);
    }

    @Override
    public void onDetachedFromActivity() {

    }
}