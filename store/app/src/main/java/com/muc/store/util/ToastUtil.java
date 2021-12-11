package com.muc.store.util;

import android.widget.Toast;

import com.muc.store.MyApplication;

import java.util.Timer;
import java.util.TimerTask;

public class ToastUtil {
    public static void show(String message){
        Toast mToast = Toast.makeText(MyApplication.getInstance(), message, Toast.LENGTH_LONG);
        mToast.show();
//        final Timer timer = new Timer();
//        timer.schedule(new TimerTask() {
//            @Override
//            public void run() {
//                mToast.show();
//            }
//        }, 0, 3500);
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                mToast.cancel();
//                timer.cancel();
            }
        }, 1000 );

    }
}
