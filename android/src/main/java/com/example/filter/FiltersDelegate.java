package com.example.filter;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.zomato.photofilters.SampleFilters;
import com.zomato.photofilters.imageprocessors.Filter;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import io.flutter.plugin.common.MethodCall;
import io.flutter.plugin.common.MethodChannel;
import io.flutter.plugin.common.PluginRegistry;

public class FiltersDelegate {
    private final Activity activity;
    private FileUtils fileUtils;
    private static List<byte[]> processedThumbs;

    private static final List<Filter> filtersAvailable = new ArrayList<Filter>();

    public FiltersDelegate(Activity activity) {
        this.activity = activity;
        processedThumbs = new ArrayList<byte[]>();
        fileUtils = new FileUtils();
        filtersAvailable.add(SampleFilters.getAweStruckVibeFilter());
        filtersAvailable.add(SampleFilters.getClarendon());
        filtersAvailable.add(SampleFilters.getOldManFilter());
        filtersAvailable.add(SampleFilters.getMarsFilter());
        filtersAvailable.add(SampleFilters.getRiseFilter());
        filtersAvailable.add(SampleFilters.getAprilFilter());
        filtersAvailable.add(SampleFilters.getAmazonFilter());
        filtersAvailable.add(SampleFilters.getStarLitFilter());
        filtersAvailable.add(SampleFilters.getNightWhisperFilter());
        filtersAvailable.add(SampleFilters.getLimeStutterFilter());
        filtersAvailable.add(SampleFilters.getHaanFilter());
        filtersAvailable.add(SampleFilters.getBlueMessFilter());
        filtersAvailable.add(SampleFilters.getAdeleFilter());
        filtersAvailable.add(SampleFilters.getCruzFilter());
        filtersAvailable.add(SampleFilters.getMetropolis());
        filtersAvailable.add(SampleFilters.getAudreyFilter());
    }

    public void generateFiltersThumb(MethodCall call, MethodChannel.Result result) {
        String filePathFromDart = (String) call.arguments;
        File srcFile = new File(filePathFromDart);
        if (!srcFile.exists()) {
            result.error("INVALID", "Image source cannot be opened", null);

        }else {

            Bitmap srcBitmap = BitmapFactory.decodeFile(srcFile.getPath(), null);
            final List<byte[]> filteredImages = doFilterProcessing(srcBitmap);
            result.success(filteredImages);
        }
    }

    public void generateOneFilter(MethodCall call, MethodChannel.Result result) {
        String path = (String) call.argument("path");
        int filterIndex = call.argument("filter");

        File srcFile = new File(path);
        try {
            if(srcFile ==null){
                result.error("INVALID", "image file does not exist", null);
            }
            Bitmap srcBitmap = BitmapFactory.decodeFile(srcFile.getAbsolutePath(), null);

            int index = filterIndex - 1;
            Filter filter = getFilterByIndex(index);
            Bitmap processedImage = filter.processFilter(fileUtils.resize(srcBitmap,640,640));
            if(processedImage == null) {
                System.out.println("The Fuck");
                result.error("INVALID", "error in processing the image", null);
            }

            String fileImageTempPath = fileUtils.createTemporaryImageFile(activity.getCacheDir(), processedImage);

            if (fileImageTempPath.equals(null) || fileImageTempPath.equals("")) {
                result.error("INVALID", "error in creating temporary file", null);
            } else {
                result.success(fileImageTempPath);
            }
        } catch (Exception e) {
            result.error("INVALID", e.toString(), null);
        }
    }

    /// doFilterProcessing
    /// parameter a bitmap object
    /// return list of bytes
    private List<byte[]> doFilterProcessing(Bitmap bitmap) {
        clearThumbs();
        processedThumbs.add(FileUtils.convert(bitmap)); // add the original image
        for (Filter filter : filtersAvailable) {
            System.out.println("Processed: "+ filtersAvailable.indexOf(filter));
            Bitmap filteredBitmap = filter.processFilter(fileUtils.resize(bitmap, 150, 150));
            processedThumbs.add(FileUtils.convert(filteredBitmap));
        }
        return processedThumbs;
    }

    public void clearThumbs() {
        processedThumbs = new ArrayList<>();
    }

    private Filter getFilterByIndex(int index) {
        Filter filter;
        switch (index) {
            case 1:
                filter = SampleFilters.getAweStruckVibeFilter();
                break;
            case 2:
                filter = SampleFilters.getClarendon();
                break;
            case 3:
                filter = SampleFilters.getOldManFilter();
                break;
            case 4:
                filter = SampleFilters.getMarsFilter();
                break;
            case 5:
                filter = SampleFilters.getRiseFilter();
                break;
            case 6:
                filter = SampleFilters.getAprilFilter();
                break;
            case 7:
                filter = SampleFilters.getAmazonFilter();
                break;
            case 8:
                filter = SampleFilters.getStarLitFilter();
                break;
            case 9:
                filter = SampleFilters.getNightWhisperFilter();
                break;
            case 10:
                filter = SampleFilters.getLimeStutterFilter();
                break;
            case 11:
                filter = SampleFilters.getHaanFilter();
                break;
            case 12:
                filter = SampleFilters.getBlueMessFilter();
                break;
            case 13:
                filter = SampleFilters.getAdeleFilter();
                break;
            case 14:
                filter = SampleFilters.getCruzFilter();
                break;
            case 15:
                filter = SampleFilters.getMetropolis();
                break;
            case 16:
                filter = SampleFilters.getAudreyFilter();
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + index);
        }
        return filter;
    }
}
