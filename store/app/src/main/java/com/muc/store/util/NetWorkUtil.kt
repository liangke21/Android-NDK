package com.muc.store.util

import android.app.Activity
import android.content.Context
import com.muc.wide.base.BaseActivity
import android.net.ConnectivityManager
import android.net.NetworkInfo
import com.muc.store.util.NetWorkUtil

class NetWorkUtil {
    companion object {
        fun checkNetWorkType(activity: Activity): String {
            val connectivityManager = activity
                .getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val networkInfo = connectivityManager.activeNetworkInfo
            if (networkInfo != null && networkInfo.isConnected) {
                val type = networkInfo.type
                return if (type == ConnectivityManager.TYPE_MOBILE) {
                    "MOBILE"
                } else {
                    "WIFI"
                }
            }
            return "INVALID"
        }
    }

}