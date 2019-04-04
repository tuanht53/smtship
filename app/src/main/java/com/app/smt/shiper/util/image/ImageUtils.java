package com.app.smt.shiper.util.image;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;

import java.io.File;
import java.io.FileOutputStream;

public class ImageUtils {

    private static final int IMAGE_MAX_SIZE = 300000; // 300k

    public static boolean checkSizeFileOver(File file) {
        double bytes = file.length();
        if (bytes > IMAGE_MAX_SIZE) {
            return true;
        } else {
            return false;
        }
    }

    public static File resizeFile(File file) {
        try {

            float bytes = file.length();

            float scale = IMAGE_MAX_SIZE / bytes;
            Bitmap bitmap = BitmapFactory.decodeFile(file.getAbsolutePath());

            int width = bitmap.getWidth();
            int height = bitmap.getHeight();

            Matrix matrix = new Matrix();
            matrix.postScale(scale, scale);

            ExifInterface ei = new ExifInterface(file.getAbsolutePath());
            int orientation = ei.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);
            int degree = 0;

            switch (orientation) {
                case ExifInterface.ORIENTATION_NORMAL:
                    degree = 0;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_90:
                    degree = 90;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_180:
                    degree = 180;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_270:
                    degree = 270;
                    break;
                case ExifInterface.ORIENTATION_UNDEFINED:
                    degree = 0;
                    break;
                default:
                    degree = 90;
            }

            matrix.postRotate(degree);

            // recreate the new Bitmap
            Bitmap resizedBitmap = Bitmap.createBitmap(bitmap, 0, 0, width, height, matrix, true);
            bitmap.recycle();

            FileOutputStream os = new FileOutputStream(file);
            resizedBitmap.compress(Bitmap.CompressFormat.JPEG, 100, os);
            os.flush();
            os.close();

            resizedBitmap.recycle();
            System.gc();

            return file;
        } catch (Exception e) {
            e.printStackTrace();
            return file;
        }
    }

}
