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

    private MethodChannel channel;

    private ExecutorService executor;
    private FiltersDelegate delegate;
    private FlutterPluginBinding pluginBinding;

    public FilterPlugin() {
        System.loadLibrary("NativeImageProcessor");
    }

    public static void registerWith(PluginRegistry.Registrar registrar) {
        if (registrar.activity() == null) {
            // If a background flutter view tries to register the plugin, there will be no activity from the registrar,
            // we stop the registering process immediately because the FilterPlugin requires an activity.
            return;
        }
        FilterPlugin plugin = new FilterPlugin();
        FiltersDelegate delegate = plugin.setupActivity(registrar.activity());
        plugin.channel = new MethodChannel(registrar.messenger(), "filter");
        plugin.channel.setMethodCallHandler(plugin);
    }

    @Override
    public void onAttachedToEngine(@NonNull FlutterPluginBinding binding) {
        pluginBinding = binding;
    }

    @Override
    public void onDetachedFromEngine(@NonNull FlutterPluginBinding binding) {
    }

    @Override
    public void onMethodCall(@NonNull MethodCall call, @NonNull MethodChannel.Result result) {
        if (call.method.equals("generateFilters")) {
            delegate.generateFiltersThumb(call, result);
        } else if (call.method.equals("final_output")) {
            delegate.generateOneFilter(call, result);
        } else {
            result.notImplemented();
        }
    }

    public FiltersDelegate setupActivity(Activity activity) {
        delegate = new FiltersDelegate(activity);
        return delegate;
    }


    @Override
    public void onAttachedToActivity(@NonNull ActivityPluginBinding binding) {
        // V2 embedding setup for activity listeners.
        setupActivity(binding.getActivity());
        MethodChannel channel = new MethodChannel(this.pluginBinding.getBinaryMessenger(), "filter");
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
        delegate = null;
    }
}