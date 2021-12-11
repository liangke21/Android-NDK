package com.muc.store.fragment

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.liulishuo.filedownloader.FileDownloader
import com.muc.store.R
import com.muc.store.adapter.AppAdapter
import com.muc.store.annotations.LayoutBinder
import com.muc.store.annotations.ViewBinder
import com.muc.wide.base.BaseFragment

@LayoutBinder(R.layout.fragment_app_date)
class AppDateFragment : BaseFragment() {
    @ViewBinder(R.id.app_date_rv)
    private lateinit var mRecyclerView: RecyclerView

    @ViewBinder(R.id.app_date_srl)
    private lateinit var mSwipeRefreshLayout: SwipeRefreshLayout
    private lateinit var mAdapter: AppAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        FileDownloader.setup(context)
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
            mAdapter.update("app_date.php")
        }
        mAdapter = AppAdapter(requireActivity()).apply {
            setCallBack {
                mSwipeRefreshLayout.isRefreshing = false
            }
        }
        mRecyclerView.adapter = mAdapter
        mAdapter.update("app_date.php")
    }
}