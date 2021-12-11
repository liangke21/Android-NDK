package com.muc.store.activity

import android.annotation.SuppressLint
import com.muc.wide.base.BaseActivity
import android.os.Bundle
import android.util.Log
import android.view.ViewTreeObserver
import android.widget.ImageView
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.google.android.material.button.MaterialButton
import com.muc.store.R
import com.muc.store.adapter.DownloadManagerAdapter
import com.muc.store.annotations.LayoutBinder
import com.muc.store.annotations.ViewBinder
import com.muc.store.config.AppConfig
import com.muc.store.liveData.DownloadLiveData
import com.muc.store.liveData.DownloadState
import com.muc.store.model.DownloadManagerModel
import com.muc.wide.Utils.ConfigUtils
import java.lang.Exception

@LayoutBinder(R.layout.activity_download_manager)
class DownloadManagerActivity : BaseActivity(), Observer<HashMap<String, DownloadManagerModel>>,
    ViewTreeObserver.OnGlobalLayoutListener {
    @ViewBinder(R.id.download_manager_btn_back)
    private lateinit var mBackButton: ImageView

    @ViewBinder(R.id.download_manager_btn_srl)
    private lateinit var mSwipeRefreshLayout: SwipeRefreshLayout

    @ViewBinder(R.id.download_manager_rv)
    private lateinit var mRecyclerView: RecyclerView
    var mHashMap = HashMap<String, DownloadManagerModel>()


    private lateinit var mAdapter: DownloadManagerAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        DownloadLiveData.observe(this, this)

        mBackButton.setOnClickListener {
            finish()
        }
        mRecyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        mAdapter = DownloadManagerAdapter(this)
        mRecyclerView.adapter = mAdapter
        mSwipeRefreshLayout.setOnRefreshListener {
            mSwipeRefreshLayout.isRefreshing = false
        }
        mRecyclerView.viewTreeObserver.addOnGlobalLayoutListener(this);

    }

    @SuppressLint("SetTextI18n")
    override fun onChanged(t: HashMap<String, DownloadManagerModel>?) {
        if (t != null) {
            mHashMap = t
            mHashMap.forEach {
//                Log.e("hash", it.key + it.value.state.name)
            }
            for (index in 1..mAdapter.getList().size) {
                val operation: MaterialButton =
                    mRecyclerView.getChildAt(index - 1)
                        .findViewById(R.id.item_download_manager_btn_download)
//                Log.e("当前的View", mAdapter.getList()[index - 1].model.name)
                val get = t[mAdapter.getList()[index - 1].model.name]
                Log.e("接受到的状态",get!!.state.name)
                when (get.state) {
                    DownloadState.CONNECTION -> {
                        operation.text = "连接中"
                    }
                    DownloadState.DOWNLOADING -> {
                        operation.text = "${get.progress}%"
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
            }
        }
    }

    fun sendLiveData(model: DownloadManagerModel) {
        if (mHashMap.containsKey(model.model.name)) {
            Log.e(model.model.name, "存在")
        }
        Log.e(model.model.name, DownloadLiveData.value?.get(model.model.name)?.state!!.name)
        mHashMap.put(model.model.name, model)

        DownloadLiveData.postValue(mHashMap)
        var state = DownloadLiveData.value?.get(model.model.name)?.state
        Log.e(model.model.name, state!!.name)
    }

    override fun onGlobalLayout() {
        DownloadLiveData.observe(this, this)

    }

}