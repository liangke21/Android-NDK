package com.muc.store.fragment

import com.muc.wide.base.BaseFragment
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.tabs.TabLayout
import com.liulishuo.filedownloader.BaseDownloadTask
import com.liulishuo.filedownloader.FileDownloadListener
import com.liulishuo.filedownloader.FileDownloader
import com.muc.store.R
import com.muc.store.adapter.AppAdapter
import com.muc.store.annotations.LayoutBinder
import com.muc.store.annotations.ViewBinder
import com.muc.store.config.AppConfig
import com.muc.store.model.AppModel
import com.muc.store.util.ApkUtil
import com.muc.store.util.HttpUtils
import com.muc.store.util.NetWorkUtil
import com.muc.store.util.ToastUtil
import com.muc.wide.Utils.ConfigUtils
import org.json.JSONObject
import java.io.File

@LayoutBinder(R.layout.fragment_app)
class AppFragment : BaseFragment() {

    @ViewBinder(R.id.app_tab)
    private lateinit var mTab: TabLayout

    private var mAppDateFragment: AppDateFragment = AppDateFragment()
    private var mAppDownloadFragment: AppDownloadFragment = AppDownloadFragment()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        FileDownloader.setup(context)
        parentFragmentManager.beginTransaction().add(R.id.app_fragment, mAppDateFragment)
            .add(R.id.app_fragment, mAppDownloadFragment).commit()
        showFragment(mAppDateFragment)
        mTab.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                if (tab!!.text.toString() == "按时间排序") {
                   showFragment(mAppDateFragment)
                } else {
                   showFragment(mAppDownloadFragment)
                }

                Log.v("盖伦","你的键就是我的剑  未选")
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
                Log.v("巴卡巴卡","八嘎八嘎  未选")
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {
                Log.v("哇咔咔","蛮吉   这是")
            }

        })
        val linearLayout = mTab.getChildAt(0) as LinearLayout
        linearLayout.showDividers = LinearLayout.SHOW_DIVIDER_MIDDLE;
        linearLayout.dividerDrawable = ContextCompat.getDrawable(
            requireContext(),
            R.drawable.layout_divider_vertical
        );

    }

    fun showFragment(fragment: BaseFragment) {
        parentFragmentManager.beginTransaction().hide(mAppDateFragment).hide(mAppDownloadFragment)
            .show(fragment).commit()
    }
}