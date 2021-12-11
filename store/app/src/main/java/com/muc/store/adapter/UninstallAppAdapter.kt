package com.muc.store.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import com.muc.store.R
import com.muc.store.model.UninstallAppModel
import com.muc.store.viewHolder.UninstallAppViewHolder
import com.muc.wide.base.BaseActivity
import com.muc.wide.base.BaseAdapter
import com.tencent.bugly.crashreport.common.info.AppInfo

import android.util.Log
import androidx.core.app.ActivityCompat.startActivityForResult

import android.content.Intent
import android.content.pm.*
import android.net.Uri
import android.os.RemoteException
import androidx.core.app.ActivityCompat
import com.muc.store.config.RequestCodeConfig
import com.muc.store.util.FileUtils
import android.content.pm.PackageStats

import android.content.pm.IPackageStatsObserver

import android.content.pm.PackageManager
import java.lang.Exception
import java.lang.reflect.Method


class UninstallAppAdapter(var activity: BaseActivity) :
    BaseAdapter<UninstallAppViewHolder, UninstallAppModel>() {
    @SuppressLint("QueryPermissionsNeeded")
    override fun getListData(list: ArrayList<UninstallAppModel>) {
        val packages: List<PackageInfo> = activity.packageManager.getInstalledPackages(0)
        val method: Method =
            PackageManager::class.java.getMethod(
                "getPackageSizeInfo", *arrayOf(
                    String::class.java,
                    IPackageStatsObserver::class.java
                )
            )
        packages.forEach {
            if (it.applicationInfo.flags and ApplicationInfo.FLAG_SYSTEM === 0) { //非系统应用

                method.invoke(
                    activity.packageManager,
                    it.packageName,
                    object : IPackageStatsObserver.Stub() {
                        override fun onGetStatsCompleted(
                            pStats: PackageStats,
                            succeeded: Boolean
                        ) {
                            activity.runOnUiThread {
                                addItem(
                                    UninstallAppModel(
                                        it.applicationInfo.loadIcon(activity.packageManager),
                                        it.applicationInfo.loadLabel(activity.packageManager) as String,
                                        FileUtils.getSize(pStats.cacheSize + pStats.dataSize + pStats.codeSize),
                                        it.versionName,
                                        it.packageName
                                    )

                                )
                            }


                        }
                    })
            }
        }
    }


    override fun onViewHolderCreate(parent: ViewGroup): UninstallAppViewHolder {
        return UninstallAppViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_uninstall_app, parent, false)
        )
    }

    @SuppressLint("SetTextI18n")
    override fun onViewHolderBind(holder: UninstallAppViewHolder, model: UninstallAppModel) {
        holder.apply {
            holder.id.text = (holder.layoutPosition+1).toString()
            icon.setImageDrawable(model.icon)
            name.text = model.name
            version.text = "版本:${model.version}"
            size.text = "大小:${model.size}"
            uninstall.setOnClickListener {
                val packageName: String = model.packageName
                val packageURI: Uri = Uri.parse("package:$packageName")
                val uninstallIntent = Intent(Intent.ACTION_DELETE, packageURI)
                activity.startActivityForResult(
                    uninstallIntent,
                    RequestCodeConfig.UNINSTALL_APP_CODE
                )
            }
        }
    }

    override fun getEmptyMessage(): String {
        return "暂未安装任何应用"
    }

}