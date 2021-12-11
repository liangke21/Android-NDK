package com.muc.store.activity

import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.SwitchCompat
import com.muc.store.R
import com.muc.store.annotations.LayoutBinder
import com.muc.store.annotations.ViewBinder
import com.muc.store.config.AppConfig
import com.muc.store.util.FileUtils
import com.muc.wide.Utils.ConfigUtils
import com.muc.wide.base.BaseActivity
import java.io.File

@LayoutBinder(R.layout.activity_setting)
class SettingActivity : BaseActivity() {
    @ViewBinder(R.id.setting_lv_clean)
    lateinit var mClean: TextView

    @ViewBinder(R.id.setting_btn_check)
    lateinit var mCheckBtn: TextView

    @ViewBinder(R.id.setting_btn_version)
    lateinit var mVersionText: TextView

    @ViewBinder(R.id.setting_switch_allow_mobile)
    lateinit var mAllowMobileDownload: SwitchCompat

    @ViewBinder(R.id.setting_btn_back)
    lateinit var mBackButton: ImageView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBackButton.setOnClickListener { finish() }
        mClean.setOnClickListener {
            var file = File(AppConfig.getDataApkPath())
            if (!file.exists()) {
                Toast.makeText(this, "未知错误", Toast.LENGTH_LONG).show()
            }
            FileUtils.deleteFile(File(AppConfig.getDataApkPath()))
            Toast.makeText(this, "清理成功", Toast.LENGTH_LONG).show()
        }
        mCheckBtn.setOnClickListener {
            Toast.makeText(this, "暂无更新", Toast.LENGTH_LONG).show()
        }
        mVersionText.text =
            "当前版本" + packageManager.getPackageInfo("com.fjht.appmarket", 0).versionName
        if (ConfigUtils(AppConfig.getAppConfigPath()).getBoolean("ALLOW_MOBILE_DOWNLOAD")) {
            mAllowMobileDownload.isChecked = true
        }
        mAllowMobileDownload.setOnClickListener {
            ConfigUtils(AppConfig.getAppConfigPath()).updateConf(
                "ALLOW_MOBILE_DOWNLOAD",
                mAllowMobileDownload.isChecked
            )
        }
    }
}