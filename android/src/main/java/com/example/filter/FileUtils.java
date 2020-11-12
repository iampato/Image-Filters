package com.example.filter;

git iimport android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.util.Log;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.UUID;

public class FileUtils {

    /// createTemporaryImageFile
    /// is a method that takes two parameters a temporary cache dir and the bitmap to save the image
    /// and returns the file path of the temporary file path
    public String createTemporaryImageFile(File directory, Bitmap bitmap) {

        String name = "image_filter_" + UUID.randomUUID().toString();
        try {
            File f = File.createTempFile(name, ".jpg", directory);
            FileOutputStream fos = new FileOutputStream(f);

            fos.write(convert(bitmap));
            fos.flush();
            fos.close();
            return f.getPath();
        } catch (IOException e) {
            System.out.println("warrrras this");
            Log.i("Debug", e.toString());
            return null;
        }
    }

    public Bitmap get_Resized_Bitmap(Bitmap bmp, int newHeight, int newWidth) {
        int width = bmp.getWidth();
        int height = bmp.getHeight();
        float scaleWidth = ((float) newWidth) / width;
        float scaleHeight = ((float) newHeight) / height;
        // CREATE A MATRIX FOR THE MANIPULATION
        Matrix matrix = new Matrix();
        // RESIZE THE BIT MAP
        matrix.postScale(scaleWidth, scaleHeight);

        // "RECREATE" THE NEW BITMAP
        Bitmap newBitmap = Bitmap.createBitmap(bmp, 0, 0, width, height, matrix, false);
        return newBitmap;
    }

    public Bitmap resize(Bitmap imaged, int maxWidth, int maxHeight) {
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

    public static byte[] convert(Bitmap thumb) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        thumb.compress(Bitmap.CompressFormat.PNG, 80, stream);
        return stream.toByteArray();
    }
}
