package com.muc.store.viewHolder

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.google.android.material.button.MaterialButton
import com.muc.store.R
import com.muc.wide.base.BaseViewHolder

class UninstallAppViewHolder(itemView: View) : BaseViewHolder(itemView) {
     var id: TextView = itemView.findViewById(R.id.item_uninstall_app_tv_id)
     var icon: ImageView = itemView.findViewById(R.id.item_uninstall_app_iv_icon)
     var name: TextView = itemView.findViewById(R.id.item_uninstall_app_tv_name)
     var version: TextView = itemView.findViewById(R.id.item_uninstall_app_tv_version)
     var size: TextView = itemView.findViewById(R.id.item_uninstall_app_tv_size)
     var uninstall: MaterialButton = itemView.findViewById(R.id.item_uninstall_app_tv_uninstall)
}