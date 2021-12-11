package com.muc.store.fragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.liulishuo.filedownloader.FileDownloader
import com.muc.store.R
import com.muc.store.adapter.AppAdapter
import com.muc.store.annotations.LayoutBinder
import com.muc.store.annotations.ViewBinder
import com.muc.store.config.AppConfig
import com.muc.store.model.AppModel
import com.muc.store.util.HttpUtils
import com.muc.wide.base.BaseFragment
import org.json.JSONObject
import com.liulishuo.filedownloader.BaseDownloadTask

import com.liulishuo.filedownloader.FileDownloadListener

import android.view.LayoutInflater
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.google.android.material.button.MaterialButton
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.muc.store.liveData.DownloadLiveData
import com.muc.store.liveData.DownloadState
import com.muc.store.model.DownloadManagerModel
import com.muc.store.util.ApkUtil
import okhttp3.internal.wait
import java.io.File
import java.lang.Exception


@LayoutBinder(R.layout.fragment_main)
class MainFragment : BaseFragment(), Observer<HashMap<String, DownloadManagerModel>> {
    @ViewBinder(R.id.main_rv)
    private lateinit var mRecyclerView: RecyclerView

    @ViewBinder(R.id.main_srl)
    private lateinit var mSwipeRefreshLayout: SwipeRefreshLayout
    private lateinit var mAdapter: AppAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        DownloadLiveData.observe(viewLifecycleOwner, this)
        mRecyclerView.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        mRecyclerView.addItemDecoration(
            DividerItemDecoration(
                context,
                LinearLayoutManager.VERTICAL
            )
        )

        mSwipeRefreshLayout.setOnRefreshListener {
            if (mAdapter.isDownloading) {
                mSwipeRefreshLayout.isRefreshing = false
                return@setOnRefreshListener
            }
            mAdapter.update("main.php")
        }
        mAdapter = AppAdapter(requireActivity()).apply {
            setCallBack {
                mSwipeRefreshLayout.isRefreshing = false
            }
        }
        mRecyclerView.adapter = mAdapter
        mAdapter.update("main.php")

    }

    override fun onDestroy() {
        super.onDestroy()
        mAdapter.paAll()
    }

    override fun onChanged(t: HashMap<String, DownloadManagerModel>?) {
        if (t != null) {
            for ((key, value) in t) {
                try {
                    val operation: MaterialButton = mRecyclerView.getChildAt(value.position)
                        .findViewById(R.id.item_main_app_btn_download)
                    when (value.state) {
                        DownloadState.CONNECTION -> {
                            operation.text = "连接中"
                        }
                        DownloadState.DOWNLOADING -> {
                            operation.text = "${value.progress}%"
                        }
                        DownloadState.COMPLETE -> {

                        }
                        DownloadState.ERROR -> {}
                        DownloadState.PAUSE -> {
                            operation.text = "继续"
                        }
                        DownloadState.INSTALL -> {
                            operation.text = "安装"
                        }
                        DownloadState.OPEN -> {
                            operation.text = "打开"
                        }
                        DownloadState.NOTDOWNLOAD -> {
                            operation.text = "下载"
                        }

                    }

                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        }
    }

}