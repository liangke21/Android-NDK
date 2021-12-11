package com.muc.store.util;

import android.content.Context;
import android.util.Log;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;

public class FileUtils {
    public static String readFile(String path) {
        BufferedReader br = null;
        StringBuffer buf = new StringBuffer();
        try {
            br = new BufferedReader(new InputStreamReader(new FileInputStream(path), StandardCharsets.UTF_8));
            char[] ch = new char[1024];
            int len = 0;
            while ((len = br.read(ch)) != -1) {
                buf.append(ch, 0, len);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                }
            }
            return String.valueOf(buf);
        }
    }

    public static String getAssetsFile(Context context, String fileName) {
        String result = "";
        try {
            InputStream is = context.getAssets().open(fileName);
            int lenght = is.available();
            byte[] buffer = new byte[lenght];
            is.read(buffer);
            result = new String(buffer, "utf8");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public static boolean writeFile(String path, String content) {
        File mFile = new File(path);

        try {
            FileWriter file = new FileWriter(path);
            file.write(content);
            file.close();
            return true;
        } catch (IOException e) {
            Log.e("white", e.getMessage());
            return false;
        }
    }

    public static boolean writeFile(String path, String content, boolean mode) {
        try {
            FileWriter file = new FileWriter(path, mode);
            file.write(content);
            file.close();
            return true;
        } catch (IOException e) {
            Log.e("white", e.getMessage());
            return false;
        }
    }

    public static void deleteFile(File file) {
        if (file.isFile()) {
            file.delete();
            return;
        }
        if (file.isDirectory()) {
            File[] childFile = file.listFiles();
            if (childFile == null || childFile.length == 0) {
                file.delete();
                return;
            }
            for (File f : childFile) {
                deleteFile(f);
            }
            file.delete();
        }
    }

    public static String getFileLastTime(String path) {
        File file = new File(path);
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        long modifiedTime = file.lastModified();
        System.out.println(modifiedTime);
        Date d = new Date(modifiedTime);
        System.out.println(format.format(d));
        long newModifiedTime = System.currentTimeMillis();
        return format.format(new Date(file.lastModified()));
    }

    public static long getFolderBytes(File file) {
        long size = 0;
        File[] fileList = file.listFiles();
        for (int i = 0; i < fileList.length; i++) {
            if (fileList[i].isDirectory()) {
                size = size + getFolderBytes(fileList[i]);
            } else {
                size = size + fileList[i].length();
            }
        }
        return size;
    }

    public static String getFolderSize(File file) {
        long size;
        if (file.isFile()) {
            size = file.length();
        } else {
            size = getFolderBytes(file);
        }
        DecimalFormat df = new DecimalFormat("###.##");
        float f = ((float) size / (float) (1024 * 1024));
        if (f < 1.0) {
            float f2 = ((float) size / (float) (1024));
            return df.format(new Float(f2).doubleValue()) + "KB";
        } else {
            return df.format(new Float(f).doubleValue()) + "M";
        }

    }

    public static String getSize(Long size) {
        DecimalFormat df = new DecimalFormat("###.##");
        float f = ((float) size / (float) (1024 * 1024));
        if (f < 1.0) {
            float f2 = ((float) size / (float) (1024));
            return df.format(new Float(f2).doubleValue()) + "KB";
        } else {
            return df.format(new Float(f).doubleValue()) + "M";
        }

    }

    public static void copyFile(String oldPath, String newPath) throws IOException {
        int bytesum = 0;
        int byteread = 0;
        File oldfile = new File(oldPath);
        if (oldfile.exists()) { //文件存在时
            InputStream inStream = new FileInputStream(oldPath); //读入原文件
            FileOutputStream fs = new FileOutputStream(newPath);
            byte[] buffer = new byte[1444];
            int length;
            while ((byteread = inStream.read(buffer)) != -1) {
                bytesum += byteread; //字节数 文件大小
                System.out.println(bytesum);
                fs.write(buffer, 0, byteread);
            }
            inStream.close();
        }
    }

    public static List<File> sortFile(File file) {
        File[] files = file.listFiles();
        if (files == null) {
            return Arrays.asList(new File[]{});
        }
        Collections.sort(Arrays.asList(files), (o1, o2) -> {
            if (o1.lastModified() < o2.lastModified()) {
                return 1;
            } else {
                return -1;
            }
        });
        return Arrays.asList(files);
    }

}