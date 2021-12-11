package com.muc.store.adapter

import android.util.Log
import com.muc.store.viewHolder.SearchRecordViewHolder
import android.view.ViewGroup
import android.view.LayoutInflater
import android.widget.LinearLayout
import com.muc.store.R
import com.muc.store.config.AppConfig
import com.muc.wide.Utils.ConfigUtils
import com.muc.wide.base.BaseAdapter


class SearchRecordAdapter : BaseAdapter<SearchRecordViewHolder, String>() {
    override fun getListData(list: ArrayList<String>) {
        var arrayList = ConfigUtils(AppConfig.getSearchRecordPath()).getArrayList("record")
        if (arrayList.length() <= 0) {
            return
        }
        Log.e("leng", arrayList.length().toString())
        for (index in 1..arrayList.length()) {
            list.add(arrayList.getString(index-1).toString())
            Log.e("arr",arrayList.getString(index-1).toString())
        }
        notifyDataSetChanged()
    }

    override fun onViewHolderCreate(parent: ViewGroup): SearchRecordViewHolder {

        return SearchRecordViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_search_record, parent, false).apply {
//                layoutParams = LinearLayout.LayoutParams()
            }
        )
    }

    override fun onViewHolderBind(holder: SearchRecordViewHolder, model: String) {
        holder.text.text = model
    }

    override fun getEmptyMessage(): String {
        return ""
    }

    override fun getHeaderCount(): Int {
        return -1
    }
}