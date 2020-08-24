package com.example.filter

import android.graphics.Bitmap
import android.os.AsyncTask
import com.zomato.photofilters.SampleFilters
import java.io.ByteArrayOutputStream
import java.util.*

class BackgroundTask : AsyncTask<Bitmap?, Int?, List<ByteArray>>() {
    private var filterThumbs: MutableList<ThumbnailItem?> = ArrayList()
    private val processedThumbs: MutableList<ByteArray> = ArrayList()
    override fun doInBackground(vararg bitmaps: Bitmap): List<ByteArray> {
        val t1 = ThumbnailItem()
        val t2 = ThumbnailItem()
        val t3 = ThumbnailItem()
        val t4 = ThumbnailItem()
        val t5 = ThumbnailItem()
        val t6 = ThumbnailItem()
        val t7 = ThumbnailItem()
        val t8 = ThumbnailItem()
        val t9 = ThumbnailItem()
        val t10 = ThumbnailItem()
        val t11 = ThumbnailItem()
        val t12 = ThumbnailItem()
        val t13 = ThumbnailItem()
        val t14 = ThumbnailItem()
        val t15 = ThumbnailItem()
        val t16 = ThumbnailItem()
        val t17 = ThumbnailItem()
        t1.image = bitmaps[0]
        t2.image = bitmaps[0]
        t3.image = bitmaps[0]
        t4.image = bitmaps[0]
        t5.image = bitmaps[0]
        t6.image = bitmaps[0]
        t7.image = bitmaps[0]
        t8.image = bitmaps[0]
        t9.image = bitmaps[0]
        t10.image = bitmaps[0]
        t11.image = bitmaps[0]
        t12.image = bitmaps[0]
        t13.image = bitmaps[0]
        t14.image = bitmaps[0]
        t15.image = bitmaps[0]
        t16.image = bitmaps[0]
        t17.image = bitmaps[0]
        //            clearThumbs()
        filterThumbs = ArrayList<Any?>()
        filterThumbs.add(t1) // Original Image
        //        t4.filter = SampleFilters.getAweStruckVibeFilter();
//        filterThumbs.add(t4);
        t13.filter = SampleFilters.getClarendon()
        filterThumbs.add(t13)
        t14.filter = SampleFilters.getOldManFilter()
        filterThumbs.add(t14)
        t12.filter = SampleFilters.getMarsFilter()
        filterThumbs.add(t12)
        t16.filter = SampleFilters.getRiseFilter()
        filterThumbs.add(t16)
        t17.filter = SampleFilters.getAprilFilter()
        filterThumbs.add(t17)
        t7.filter = SampleFilters.getAmazonFilter()
        filterThumbs.add(t7)
        t2.filter = SampleFilters.getStarLitFilter()
        filterThumbs.add(t2)
        t6.filter = SampleFilters.getNightWhisperFilter()
        filterThumbs.add(t6)
        t5.filter = SampleFilters.getLimeStutterFilter()
        filterThumbs.add(t5)
        t15.filter = SampleFilters.getHaanFilter()
        filterThumbs.add(t15)
        t3.filter = SampleFilters.getBlueMessFilter()
        filterThumbs.add(t3)
        t8.filter = SampleFilters.getAdeleFilter()
        filterThumbs.add(t8)
        t9.filter = SampleFilters.getCruzFilter()
        filterThumbs.add(t9)
        t10.filter = SampleFilters.getMetropolis()
        filterThumbs.add(t10)
        t11.filter = SampleFilters.getAudreyFilter()
        filterThumbs.add(t11)
        for (thumb in filterThumbs) {
            thumb!!.image = resize(thumb.image, 640, 640)
            thumb.image = thumb.filter.processFilter(thumb.image)
            processedThumbs.add(convert(thumb.image))
        }
        System.gc()
        return processedThumbs
    }

    companion object {
        private fun convert(thumb: Bitmap): ByteArray {
            val stream = ByteArrayOutputStream()
            thumb.compress(Bitmap.CompressFormat.PNG, 80, stream)
            return stream.toByteArray()
        }

        private fun resize(imaged: Bitmap, maxWidth: Int, maxHeight: Int): Bitmap {
            var image = imaged
            if (maxHeight > 0 && maxWidth > 0) {
                val width = image.width
                val height = image.height
                val ratioBitmap = width.toFloat() / height.toFloat()
                val ratioMax = maxWidth.toFloat() / maxHeight.toFloat()
                var finalWidth = maxWidth
                var finalHeight = maxHeight
                if (ratioMax > 1) {
                    finalWidth = Math.round(maxHeight.toFloat() * ratioBitmap)
                } else {
                    finalHeight = Math.round(maxWidth.toFloat() / ratioBitmap)
                }
                return Bitmap.createScaledBitmap(image, finalWidth, finalHeight, false).also { image = it }
            }
            return image
        }
    }

    override fun doInBackground(vararg p0: Bitmap?): List<ByteArray> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}