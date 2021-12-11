package com.muc.store.activity

import android.os.Bundle
import android.widget.ImageView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.google.android.material.button.MaterialButton
import com.muc.store.R
import com.muc.store.annotations.LayoutBinder
import com.muc.store.annotations.ViewBinder
import com.muc.wide.base.BaseActivity

@LayoutBinder(R.layout.activity_update_app)
class UpdateAppActivity : BaseActivity() {
    @ViewBinder(R.id.update_app_btn_back)
    private lateinit var mBackButton: ImageView

    @ViewBinder(R.id.update_app_btn_srl)
    private lateinit var mSwipeRefreshLayout: SwipeRefreshLayout
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBackButton.setOnClickListener {
            finish()
        }
        mSwipeRefreshLayout.setOnRefreshListener {
            mSwipeRefreshLayout.isRefreshing = false
        }
    }
}