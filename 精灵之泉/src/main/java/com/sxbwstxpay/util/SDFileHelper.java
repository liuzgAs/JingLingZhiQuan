package com.sxbwstxpay.util;

public class SDFileHelper {
//    private Context context;
//
//    public SDFileHelper() {
//    }
//
//    public SDFileHelper(Context context) {
//        super();
//        this.context = context;
//    }     //Glide保存图片
//
//    public void savePicture(final String fileName, String url) {
//        GlideApp.with(context)
//                .asBitmap().
//                .load(url)
//                .toBytes()
//                .into(new SimpleTarget<byte[]>() {
//                    @Override
//                    public void onResourceReady(byte[] bytes, GlideAnimation<? super byte[]> glideAnimation) {
//                        try {
//                            savaFileToSD(fileName, bytes);
//                        } catch (Exception e) {
//                            e.printStackTrace();
//                        }
//                    }
//                });
//    }   //往SD卡写入文件的方法
//
//    public void savaFileToSD(String filename, byte[] bytes) throws Exception {
//        //如果手机已插入sd卡,且app具有读写sd卡的权限
//        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
//            String filePath = Environment.getExternalStorageDirectory().getCanonicalPath() + "/精灵之泉";
//            File dir1 = new File(filePath);
//            if (!dir1.exists()) {
//                dir1.mkdirs();
//            }
//            filename = filePath + "/" + filename;       //这里就不要用openFileOutput了,那个是往手机内存中写数据的
//            FileOutputStream output = new FileOutputStream(filename);
//            output.write(bytes);       //将bytes写入到输出流中
//            output.close();       //关闭输出流
////            Toast.makeText(context, "图片已成功保存到" + filePath, Toast.LENGTH_SHORT).show();
//            LogUtil.LogShitou("SDFileHelper--savaFileToSD", "" + filename);
//            // 最后通知图库更新
//            context.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE,
//                    Uri.fromFile(new File(filename))));
//        } else {
//            Toast.makeText(context, "SD卡不存在或者不可读写", Toast.LENGTH_SHORT).show();
//        }
//    }
}