package com.muc.store.viewHolder

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.google.android.material.button.MaterialButton
import com.muc.store.R
import com.muc.wide.base.BaseViewHolder

class AppViewHolder(itemView: View) : BaseViewHolder(itemView) {
    var id: TextView = itemView.findViewById(R.id.item_main_app_tv_id)
    var icon: ImageView = itemView.findViewById(R.id.item_main_app_tv_icon)
    var name: TextView = itemView.findViewById(R.id.item_main_app_tv_name)
    var version: TextView = itemView.findViewById(R.id.item_main_app_tv_version)
    var data: TextView = itemView.findViewById(R.id.item_main_app_tv_date)
    var size: TextView = itemView.findViewById(R.id.item_main_app_tv_size)
    var download: MaterialButton = itemView.findViewById(R.id.item_main_app_btn_download)
}