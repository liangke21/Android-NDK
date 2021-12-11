package com.muc.wide.base

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.muc.store.annotations.LayoutBinder
import com.muc.store.annotations.ViewBinder


abstract class BaseActivity : AppCompatActivity() {
    private var mLayoutId = -1

    @SuppressLint("RestrictedApi")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bindLayout()
        setContentView(mLayoutId)
        bindView()

    }


    private fun bindView() {
        if (mLayoutId <= 0) {
            return
        }
        for (field in this.javaClass.declaredFields) {

            try {
                field.apply {
                    if (isAnnotationPresent(ViewBinder::class.java)) {
                        val binder = getAnnotation(ViewBinder::class.java)
                        isAccessible = true
                        set(this@BaseActivity, findViewById(binder.value))
                    }
                }
            } catch (e: Exception) {
                throw Exception("not found view id! " + field.name);
            }
        }
    }

    private fun bindLayout() {
        this.javaClass.apply {
            if (isAnnotationPresent(LayoutBinder::class.java)) {
                val inject: LayoutBinder = getAnnotation(LayoutBinder::class.java)
                mLayoutId = inject.value
            }
        }

    }
}