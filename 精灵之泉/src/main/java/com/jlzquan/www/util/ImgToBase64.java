package com.jlzquan.www.util;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;

import java.io.ByteArrayOutputStream;


/**
 * des： bitmap转string
 * author： ZhangJieBo
 * date： 2017/7/7 0007 上午 9:37
 */
public class ImgToBase64 {
    public static String toBase64(String pathName){
        Bitmap bitmap = BitmapFactory.decodeFile(pathName, getBitmapOption(1)); //将图片的长和宽缩小味原来的1/2
        String base64ImgStr = convertIconToString(bitmap).toString().trim();
        return base64ImgStr;
    }

    private static BitmapFactory.Options getBitmapOption(int inSampleSize) {
        System.gc();
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inPurgeable = true;
        options.inSampleSize = inSampleSize;
        return options;
    }

    /**
     * 图片转成string
     *
     * @param bitmap
     * @return
     */
    public static String convertIconToString(Bitmap bitmap) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();// outputstream
        bitmap.compress(Bitmap.CompressFormat.JPEG, 50, baos);
        byte[] appicon = baos.toByteArray();// 转为byte数组
        return Base64.encodeToString(appicon, Base64.DEFAULT);

    }
}
