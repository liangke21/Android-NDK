package com.muc.store.util;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.util.Log;

import androidx.core.content.FileProvider;

import java.io.File;

public class ApkUtil {
    public static void installApp(Context context, File file) {
        try {
            if (Build.VERSION.SDK_INT >= 24) {//判读版本是否在7.0以上
                String authority = context.getApplicationContext().getPackageName() + ".fileProvider";
                Uri apkUri = FileProvider.getUriForFile(context, "com.muc.wide.fileProvider", file);//在AndroidManifest中的android:authorities值
                Intent install = new Intent(Intent.ACTION_VIEW);
                install.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                install.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);//添加这一句表示对目标应用临时授权该Uri所代表的文件
                install.setDataAndType(apkUri, "application/vnd.android.package-archive");
                context.startActivity(install);
            } else {
                Intent install = new Intent(Intent.ACTION_VIEW);
                install.setDataAndType(Uri.fromFile(file), "application/vnd.android.package-archive");
                install.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(install);
            }
        } catch (Exception e) {
            Log.e("apk", e.getMessage());
//            ToastUtils.INSTANCE.error(e.getMessage());
        }
    }
}
