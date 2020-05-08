package com.example.filter

import android.content.BroadcastReceiver
import android.graphics.BitmapFactory
import androidx.annotation.NonNull
import io.flutter.embedding.engine.plugins.FlutterPlugin
import io.flutter.plugin.common.MethodCall
import io.flutter.plugin.common.MethodChannel
import io.flutter.plugin.common.MethodChannel.MethodCallHandler
import io.flutter.plugin.common.MethodChannel.Result
import io.flutter.plugin.common.PluginRegistry.Registrar


/** FilterPlugin */
public class FilterPlugin : FlutterPlugin, MethodCallHandler {

    init {
        System.loadLibrary("NativeImageProcessor")
    }

    private val filtersChangeReceiver: BroadcastReceiver? = null

    override fun onAttachedToEngine(@NonNull flutterPluginBinding: FlutterPlugin.FlutterPluginBinding) {
        val channel = MethodChannel(flutterPluginBinding.getFlutterEngine().getDartExecutor(), "filter")
        channel.setMethodCallHandler(FilterPlugin());
    }

    companion object {
        @JvmStatic
        fun registerWith(registrar: Registrar) {
            val channel = MethodChannel(registrar.messenger(), "filter")
            channel.setMethodCallHandler(FilterPlugin())
        }
    }

    override fun onMethodCall(@NonNull call: MethodCall, @NonNull result: Result) {
         if (call.method.equals("generateFilters")) {
            val bytesFromDart = call.arguments as ByteArray
            val imageBitmap = BitmapFactory.decodeByteArray(bytesFromDart, 0, bytesFromDart!!.size)

            var response = BackgroundTask().execute(imageBitmap).get()
            result.success(response)

        } else {
            result.notImplemented()
        }
    }

    override fun onDetachedFromEngine(@NonNull binding: FlutterPlugin.FlutterPluginBinding) {
    }
}
