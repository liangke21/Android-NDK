package com.muc.wide.base

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.muc.store.annotations.LayoutBinder
import com.muc.store.annotations.ViewBinder

open class BaseFragment : Fragment() {
    private var mLayoutId: Int = -1
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        bindLayout()
        return inflater.inflate(mLayoutId, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bindView()
    }

    fun startActivity(activity: Class<*>) {
        startActivity(Intent(context, activity))
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
                        set(this@BaseFragment, requireView().findViewById(binder.value))
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