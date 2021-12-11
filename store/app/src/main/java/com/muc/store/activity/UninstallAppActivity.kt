package com.muc.store.activity

import android.content.Intent
import com.muc.wide.base.BaseActivity
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.muc.store.R
import com.muc.store.adapter.UninstallAppAdapter
import com.muc.store.annotations.LayoutBinder
import com.muc.store.annotations.ViewBinder
import com.muc.store.config.RequestCodeConfig
import com.muc.store.util.ToastUtil
import android.os.Environment

import android.os.StatFs
import android.widget.ProgressBar
import android.widget.TextView
import com.muc.store.util.FileUtils
import java.util.*


@LayoutBinder(R.layout.activity_uninstall_app)
class UninstallAppActivity : BaseActivity() {
    @ViewBinder(R.id.uninstall_app_btn_back)
    private lateinit var mBackButton: View

    @ViewBinder(R.id.uninstall_app_rv)
    private lateinit var mRecyclerView: RecyclerView

    @ViewBinder(R.id.uninstall_app_srl)
    private lateinit var mSwipeRefreshLayout: SwipeRefreshLayout

    private lateinit var mAdapter: UninstallAppAdapter

    @ViewBinder(R.id.uninstall_app_tv_total)
    private lateinit var mState: TextView

    @ViewBinder(R.id.uninstall_app_pb)
    private lateinit var mProgressBar: ProgressBar
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBackButton.setOnClickListener {
            finish()
        }
        mRecyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        mAdapter = UninstallAppAdapter(this)
        mAdapter.update()
        mRecyclerView.adapter = mAdapter
        mSwipeRefreshLayout.setOnRefreshListener {
            mAdapter.update()
            mSwipeRefreshLayout.isRefreshing = false
        }
        mRecyclerView.addItemDecoration(
            DividerItemDecoration(
                this,
                LinearLayoutManager.VERTICAL
            )
        )
        queryStorage()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            RequestCodeConfig.UNINSTALL_APP_CODE -> {
                mAdapter.update()
                mAdapter.notifyDataSetChanged()
            }
        }
    }

    fun queryStorage() {
        val statFs = StatFs(Environment.getExternalStorageDirectory().path)
        val totalSize = getUnit(statFs.totalBytes.toFloat())
        val availableSize = getUnit(statFs.availableBytes.toFloat())
        mState.text = "可用:$availableSize 总共:$totalSize"
        mProgressBar.setProgress(((statFs.availableBytes*100)/statFs.totalBytes).toInt())
    }

    private val units = arrayOf("B", "KB", "MB", "GB", "TB")

    /**
     * 单位转换
     */
    private fun getUnit(size: Float): String {
        var size = size
        var index = 0
        while (size > 1024 && index < 4) {
            size = size / 1024
            index++
        }
        return java.lang.String.format(Locale.getDefault(), " %.2f %s", size, units[index])
    }

}