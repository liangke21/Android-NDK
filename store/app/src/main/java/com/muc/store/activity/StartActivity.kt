package com.muc.store.activity

import android.os.Bundle
import com.muc.store.R
import com.muc.store.annotations.LayoutBinder
import com.muc.wide.base.BaseActivity
import java.util.*

@LayoutBinder(R.layout.activity_start)
class StartActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Timer().schedule(object : TimerTask() {
            override fun run() {
                finish()
            }
        }, 1000)
    }
}