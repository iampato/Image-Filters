package com.example.filter;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;

import com.zomato.photofilters.SampleFilters;
import com.zomato.photofilters.imageprocessors.Filter;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import android.util.Log;

//public class BackgroundTask extends AsyncTask<ContextModel, Integer, List<String>> {
//    private List<ThumbnailItem> filterThumbs = new ArrayList<ThumbnailItem>();
//    private List<String> processedThumbs = new ArrayList<String>();

public class BackgroundTask {
    ContextModel contextModel;
    private static List<Filter> filtersAvailable = new ArrayList<Filter>();
    private static List<ThumbnailItem> filterThumbs = new ArrayList<ThumbnailItem>();
    private static List<byte[]> processedThumbs = new ArrayList<byte[]>();

    BackgroundTask(ContextModel contextModel) {
        this.contextModel = contextModel;
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

    //    @Override
    public List<byte[]> doInBackground() {

        Bitmap bitmap = this.contextModel.image;
        Context context = this.contextModel.context;
        ThumbnailItem t1 = new ThumbnailItem();
        ThumbnailItem t2 = new ThumbnailItem();
        ThumbnailItem t3 = new ThumbnailItem();
        ThumbnailItem t4 = new ThumbnailItem();
        ThumbnailItem t5 = new ThumbnailItem();
        ThumbnailItem t6 = new ThumbnailItem();
        ThumbnailItem t7 = new ThumbnailItem();
        ThumbnailItem t8 = new ThumbnailItem();
        ThumbnailItem t9 = new ThumbnailItem();
        ThumbnailItem t10 = new ThumbnailItem();
        ThumbnailItem t11 = new ThumbnailItem();
        ThumbnailItem t12 = new ThumbnailItem();
        ThumbnailItem t13 = new ThumbnailItem();
        ThumbnailItem t14 = new ThumbnailItem();
        ThumbnailItem t15 = new ThumbnailItem();
        ThumbnailItem t16 = new ThumbnailItem();
        ThumbnailItem t17 = new ThumbnailItem();

        t1.image = bitmap;
        t2.image = bitmap;
        t3.image = bitmap;
        t4.image = bitmap;
        t5.image = bitmap;
        t6.image = bitmap;
        t7.image = bitmap;
        t8.image = bitmap;
        t9.image = bitmap;
        t10.image = bitmap;
        t11.image = bitmap;
        t12.image = bitmap;
        t13.image = bitmap;
        t14.image = bitmap;
        t15.image = bitmap;
        t16.image = bitmap;
        t17.image = bitmap;

            clearThumbs();
        filterThumbs = new ArrayList();

        filterThumbs.add(t1);// Original Image

        t4.filter = SampleFilters.getAweStruckVibeFilter();
        filterThumbs.add(t4);

        t13.filter = SampleFilters.getClarendon();
        filterThumbs.add(t13);

        t14.filter = SampleFilters.getOldManFilter();
        filterThumbs.add(t14);

        t12.filter = SampleFilters.getMarsFilter();
        filterThumbs.add(t12);

        t16.filter = SampleFilters.getRiseFilter();
        filterThumbs.add(t16);

        t17.filter = SampleFilters.getAprilFilter();
        filterThumbs.add(t17);

        t7.filter = SampleFilters.getAmazonFilter();
        filterThumbs.add(t7);

        t2.filter = SampleFilters.getStarLitFilter();
        filterThumbs.add(t2);

        t6.filter = SampleFilters.getNightWhisperFilter();
        filterThumbs.add(t6);

        t5.filter = SampleFilters.getLimeStutterFilter();
        filterThumbs.add(t5);

        t15.filter = SampleFilters.getHaanFilter();
        filterThumbs.add(t15);

        t3.filter = SampleFilters.getBlueMessFilter();
        filterThumbs.add(t3);

        t8.filter = SampleFilters.getAdeleFilter();
        filterThumbs.add(t8);

        t9.filter = SampleFilters.getCruzFilter();
        filterThumbs.add(t9);

        t10.filter = SampleFilters.getMetropolis();
        filterThumbs.add(t10);

        t11.filter = SampleFilters.getAudreyFilter();
        filterThumbs.add(t11);

        for (ThumbnailItem thumb : filterThumbs) {
            thumb.image = resize(thumb.image, 150, 150);
            thumb.image = thumb.filter.processFilter(thumb.image);
            processedThumbs.add(convert(thumb.image));
        }
        return processedThumbs;

    }

    public void clearThumbs() {
        filterThumbs = new ArrayList<>();
        processedThumbs = new ArrayList<>();
    }
    private static byte[] convert(Bitmap thumb) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        thumb.compress(Bitmap.CompressFormat.PNG, 80, stream);
        return stream.toByteArray();
    }

    private String createTemporaryImageFile(Context context, Bitmap bitmap) {

        File directory = context.getCacheDir();
        String name = "image_filter_" + UUID.randomUUID().toString();
        try {
            File f = File.createTempFile(name, ".jpg", directory);
            FileOutputStream fos = new FileOutputStream(f);

            fos.write(convert(bitmap));
            fos.flush();
            fos.close();
            return f.getPath();
        } catch (IOException e) {
            Log.i("Debug", e.toString());
            return null;
        }
    }

    private Bitmap resize(Bitmap imaged, int maxWidth, int maxHeight) {
        Bitmap image = imaged;

        if (maxHeight > 0 && maxWidth > 0) {
            int width = image.getWidth();
            int height = image.getHeight();
            float ratioBitmap = (float) width / (float) height;
            float ratioMax = (float) maxWidth / (float) maxHeight;
            int finalWidth = maxWidth;
            int finalHeight = maxHeight;
            if (ratioMax > 1) {
                finalWidth = Math.round(((float) maxHeight * ratioBitmap));
            } else {
                finalHeight = Math.round(((float) maxWidth / ratioBitmap));
            }
            return image = Bitmap.createScaledBitmap(image, finalWidth, finalHeight, false);
        }
        return image;
    }

    public String finalOutputFilter(int index,Bitmap bitmap){
       System.out.println("Index: "+index);
       Filter filter = filtersAvailable.get(index);
       System.out.println("final filter: "+filter.toString());
       Bitmap processed =  SampleFilters.getMetropolis().processFilter(bitmap);
       System.out.println("Done processing final");
       return createTemporaryImageFile(this.contextModel.context,processed);
    }
}