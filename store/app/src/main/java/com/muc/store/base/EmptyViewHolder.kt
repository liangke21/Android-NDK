package com.muc.store.base

import android.view.ViewGroup
import com.muc.wide.base.BaseViewHolder
import android.view.LayoutInflater
import android.view.View
import android.widget.ProgressBar
import android.widget.TextView
import com.muc.store.R

class EmptyViewHolder(itemView: View) : BaseViewHolder(itemView) {
    var pb: ProgressBar = itemView.findViewById(R.id.empty_pb)
    var tv: TextView = itemView.findViewById(R.id.empty_tv)
}