package com.muc.store.viewHolder

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.google.android.material.button.MaterialButton
import com.muc.store.R
import com.muc.wide.base.BaseViewHolder

class DownloadManagerViewHolder(itemView: View) : BaseViewHolder(itemView) {

    var id: TextView = itemView.findViewById(R.id.item_download_manager_tv_id)
    var name: TextView = itemView.findViewById(R.id.item_download_manager_tv_name)
    var icon: ImageView = itemView.findViewById(R.id.item_download_manager_tv_icon)
    var storage: TextView = itemView.findViewById(R.id.item_download_manager_tv_storage)
    var delete: TextView = itemView.findViewById(R.id.item_download_manager_btn_delete)
    var operation: MaterialButton = itemView.findViewById(R.id.item_download_manager_btn_download)
}