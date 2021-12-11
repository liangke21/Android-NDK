package com.muc.store.activity

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.EditText
import android.widget.ImageView
import android.widget.PopupWindow
import android.widget.TextView
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.muc.store.R
import com.muc.store.adapter.SearchAppAdapter
import com.muc.store.adapter.SearchRecordAdapter
import com.muc.store.annotations.LayoutBinder
import com.muc.store.annotations.ViewBinder
import com.muc.store.config.AppConfig
import com.muc.store.util.ToastUtil
import com.muc.wide.Utils.ConfigUtils
import com.muc.wide.base.BaseActivity
import org.json.JSONArray
import java.io.File
import android.view.inputmethod.EditorInfo

import android.widget.TextView.OnEditorActionListener


@LayoutBinder(R.layout.activity_search)
class SearchActivity : BaseActivity() {
    @ViewBinder(R.id.search_btn_back)
    private lateinit var mBackButton: ImageView

    @ViewBinder(R.id.search_btn_search)
    private lateinit var mSearchButton: ImageView

    @ViewBinder(R.id.search_edit)
    private lateinit var mSearchEditText: EditText

    @ViewBinder(R.id.search_rv)
    private lateinit var mRecyclerView: RecyclerView
    private lateinit var mAdapter: SearchAppAdapter

    @ViewBinder(R.id.search_srl)
    private lateinit var mSwipeRefreshLayout: SwipeRefreshLayout

    private lateinit var mPopWindow: PopupWindow

    private lateinit var mSearchAdapter: SearchRecordAdapter

    var mSearchRecyclerView: RecyclerView? = null
    var mClean: TextView? = null

    @SuppressLint("ClickableViewAccessibility")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBackButton.setOnClickListener {
            finish()
        }
        mRecyclerView.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        mRecyclerView.addItemDecoration(
            DividerItemDecoration(
                this,
                LinearLayoutManager.VERTICAL
            )
        )
        mPopWindow = PopupWindow(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
        if (!File(AppConfig.getSearchRecordPath()).exists()) {
            ConfigUtils.createConfig(AppConfig.getSearchRecordPath(), HashMap<Any, Any>().apply {
                put("record", JSONArray())
            })
        }

        mSwipeRefreshLayout.setOnRefreshListener {
//            if (mAdapter.isDownloading) {
            mSwipeRefreshLayout.isRefreshing = false
//                return@setOnRefreshListener
//            }
//            mAdapter.update("search.php?name=${mSearchEditText.text}")
        }
        mAdapter = SearchAppAdapter(this).apply {
            setCallBack {
                mSwipeRefreshLayout.isRefreshing = false
            }
        }
        mRecyclerView.adapter = mAdapter
        /**
         * 搜索按钮点击事件
         */
        mSearchButton.setOnClickListener {
            search()

        }
        mSearchAdapter = SearchRecordAdapter()
        mPopWindow.apply {
            isTouchable = true
            contentView = LayoutInflater.from(this@SearchActivity)
                .inflate(R.layout.model_popup_search, null).apply {
                    mSearchRecyclerView = findViewById(R.id.model_popup_search_rv)
                    mClean = findViewById(R.id.model_popup_search_clean)
                }
            setTouchInterceptor { _, _ -> false }
            /**
             * 清楚历史记录按钮事件
             */
            mClean?.setOnClickListener {
                mSearchAdapter.getList().clear()
                mSearchAdapter.notifyDataSetChanged()
                ConfigUtils.createConfig(
                    AppConfig.getSearchRecordPath(),
                    HashMap<Any, Any>().apply {
                        put("record", JSONArray())
                    })
                if (mPopWindow.isShowing) {
                    mPopWindow.dismiss()
                }
            }
            mSearchRecyclerView?.layoutManager =
                LinearLayoutManager(this@SearchActivity, LinearLayoutManager.VERTICAL, false)
            mSearchRecyclerView?.adapter = mSearchAdapter
            mSearchRecyclerView?.addItemDecoration(
                DividerItemDecoration(
                    this@SearchActivity,
                    LinearLayoutManager.VERTICAL
                )
            )
            /**
             * 点击Item设置文本
             */
            mSearchAdapter.setOnItemClickListener {
                mSearchEditText.setText(it)
                mPopWindow.dismiss()
            }
        }
        /**
         * 搜索框的点击事件
         */
        mSearchEditText.setOnClickListener {
            mPopWindow.showAsDropDown(mSearchEditText, 0, 10)
            mSearchAdapter.update()
            mRecyclerView.layoutParams = ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
            )
        }
        mSearchEditText.setOnEditorActionListener { v, actionId, event ->
            // 输入法中点击搜索
            if (actionId == EditorInfo.IME_ACTION_SEARCH || actionId == EditorInfo.IME_ACTION_UNSPECIFIED) {
                search()
                true
            } else false
        }
    }


    fun search() {
        if (mPopWindow.isShowing) {
            mPopWindow.dismiss()
        }
        if (mSearchEditText.text.toString().isEmpty()) {
            ToastUtil.show("请填写完整")
            return
        }
        mAdapter.update("search.php?name=${mSearchEditText.text}")
        val arrayList = ConfigUtils(AppConfig.getSearchRecordPath()).getArrayList("record")
        Log.e("leng", arrayList.length().toString())
        /**
         * 存在相同的数据为true
         */
        var isFlag: Boolean = false
        for (index in 1..arrayList.length()) {
            if (arrayList.getString(index - 1) == mSearchEditText.text.toString()) {
                isFlag = true
            }
        }
        /**
         * 只有数据不相同的情况才执行
         */
        if (!isFlag) {
            ConfigUtils(AppConfig.getSearchRecordPath()).updateConf(
                "record",
                ConfigUtils(AppConfig.getSearchRecordPath()).getArrayList("record").apply {
                    put(mSearchEditText.text.toString())
                }
            )
        }
    }
}