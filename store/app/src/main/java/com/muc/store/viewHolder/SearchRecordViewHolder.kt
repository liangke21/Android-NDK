package com.muc.store.viewHolder

import android.view.View
import android.widget.TextView
import com.muc.store.R
import com.muc.wide.base.BaseViewHolder

class SearchRecordViewHolder(itemView: View):BaseViewHolder(itemView){
    var text:TextView = itemView.findViewById(R.id.item_search_record_text)
}