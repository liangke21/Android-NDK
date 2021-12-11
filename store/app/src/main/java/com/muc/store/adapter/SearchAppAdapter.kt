package com.muc.store.adapter

import android.app.Activity
import com.muc.store.model.AppModel
import com.muc.store.viewHolder.AppViewHolder

class SearchAppAdapter(activity: Activity) : AppAdapter(activity) {
    override fun getHeaderCount(): Int {
        return -1
    }
}