package com.muc.store.fragment

import android.content.Intent
import com.muc.store.annotations.LayoutBinder
import com.muc.store.R
import com.muc.wide.base.BaseFragment
import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import com.muc.store.activity.DownloadManagerActivity
import com.muc.store.activity.SettingActivity
import com.muc.store.activity.UninstallAppActivity
import com.muc.store.activity.UpdateAppActivity
import com.muc.store.annotations.ViewBinder

@LayoutBinder(R.layout.fragment_management)
class ManagementFragment : BaseFragment() {
    @ViewBinder(R.id.management_ll_setting)
    private lateinit var mSetting: LinearLayout

    @ViewBinder(R.id.management_ll_uninstall_app)
    private lateinit var mUninstall: LinearLayout

    @ViewBinder(R.id.management_ll_download)
    private lateinit var mDownload: LinearLayout

    @ViewBinder(R.id.management_ll_update_app)
    private lateinit var mUpdate: LinearLayout
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mSetting.setOnClickListener {
            startActivity(Intent(context, SettingActivity::class.java))
        }
        mUninstall.setOnClickListener {
            startActivity(Intent(context, UninstallAppActivity::class.java))
        }
        mUpdate.setOnClickListener {
            startActivity(Intent(context, UpdateAppActivity::class.java))
        }
        mDownload.setOnClickListener {
            startActivity(Intent(context, DownloadManagerActivity::class.java))
        }
    }
}