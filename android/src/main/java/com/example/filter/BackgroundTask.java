package com.example.filter;


import android.graphics.Bitmap;
import android.os.AsyncTask;
import com.zomato.photofilters.SampleFilters;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

public class BackgroundTask extends AsyncTask<Bitmap, Integer, List<byte[]>> {
    private List<ThumbnailItem> filterThumbs = new ArrayList<ThumbnailItem>();
    private List<byte[]> processedThumbs = new ArrayList<byte[]>();


    @Override
    protected List<byte[]> doInBackground(Bitmap... bitmaps) {

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

        t1.image = bitmaps[0];
        t2.image = bitmaps[0];
        t3.image = bitmaps[0];
        t4.image = bitmaps[0];
        t5.image = bitmaps[0];
        t6.image = bitmaps[0];
        t7.image = bitmaps[0];
        t8.image = bitmaps[0];
        t9.image = bitmaps[0];
        t10.image = bitmaps[0];
        t11.image = bitmaps[0];
        t12.image = bitmaps[0];
        t13.image = bitmaps[0];
        t14.image = bitmaps[0];
        t15.image = bitmaps[0];
        t16.image = bitmaps[0];
        t17.image = bitmaps[0];

//            clearThumbs()
        filterThumbs = new ArrayList();

        filterThumbs.add(t1);// Original Image

//        t4.filter = SampleFilters.getAweStruckVibeFilter();
//        filterThumbs.add(t4);

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

            thumb.image = resize(thumb.image, 640, 640);
            thumb.image = thumb.filter.processFilter(thumb.image);

            processedThumbs.add(convert(thumb.image));
        }
        return  processedThumbs;

    }
    private static byte[] convert(Bitmap thumb) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        thumb.compress(Bitmap.CompressFormat.PNG, 80, stream);
        return stream.toByteArray();
    }
    private static Bitmap resize( Bitmap imaged, int maxWidth, int maxHeight) {
        Bitmap image = imaged;
        if (maxHeight > 0 && maxWidth > 0) {
            int width = image.getWidth();
            int height = image.getHeight();
            float ratioBitmap = (float) width / (float) height ;
            float ratioMax = (float) maxWidth / (float) maxHeight;
            int finalWidth = maxWidth;
            int finalHeight = maxHeight;
            if (ratioMax > 1) {
                finalWidth = Math.round(((float) maxHeight* ratioBitmap));
            } else {
                finalHeight = Math.round(((float) maxWidth / ratioBitmap));
            }
            return image = Bitmap.createScaledBitmap(image, finalWidth, finalHeight, false);
        }
        return image;
    }
}