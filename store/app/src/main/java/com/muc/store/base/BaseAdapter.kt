package com.muc.wide.base

import android.app.Activity
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.muc.store.R
import com.muc.store.base.EmptyViewHolder

abstract class BaseAdapter<VH : BaseViewHolder, M> : RecyclerView.Adapter<BaseViewHolder>() {
    val VIEW_TYPE_EMPTY = -1
    private var headerCount = 0
    private var mList: ArrayList<M> = ArrayList()
    private var mOnClickListener: ((M) -> Unit)? = null
    private var mOnLongClickListener: ((M) -> Unit)? = null
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        return if (viewType == VIEW_TYPE_EMPTY) {
            EmptyViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_empty_view, parent, false) as ViewGroup) as VH
        } else onViewHolderCreate(parent)
    }

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        if (getItemViewType(position) == VIEW_TYPE_EMPTY) {
            val emptyViewHolder = holder as EmptyViewHolder
            emptyViewHolder.tv.text = getEmptyMessage()
            if (getEmptyMessage()=="暂无下载任务"){
                emptyViewHolder.pb.visibility = View.GONE
            }
            return
        }
        onViewHolderBind(holder as VH, mList[position])
        holder.apply {
            if (!itemView.hasOnClickListeners()) {
                itemView.setOnClickListener {
                    mOnClickListener?.invoke(mList[position])
                }
            }
            itemView.setOnLongClickListener {
                mOnLongClickListener?.invoke(mList[position])
                false
            }
        }
    }

    override fun getItemCount(): Int {
        headerCount = getHeaderCount()
        val count: Int = mList.size
        if (headerCount <= count) {
            if (count == 0 || count - headerCount == 0) {
                return headerCount + 1
            }
        }
        return count
    }

    override fun getItemViewType(position: Int): Int {
        val count: Int = mList.size
        if (headerCount >= 0) {
            if (count - headerCount == 0 && position == headerCount) {
                //确定position是否是空视图项
                return VIEW_TYPE_EMPTY
            }
        }
        return 0
    }


    open fun getHeaderCount(): Int {
        return 0
    }

    /**
     * 设置点击事件
     */
    fun setOnItemClickListener(listener: (M) -> Unit) {
        mOnClickListener = listener
    }

    fun getOnItemClickListener(): ((M) -> Unit)? {
        return mOnClickListener
    }


    /**
     * 设置长按事件
     */
    fun setOnLongItemClickListener(listener: (M) -> Unit) {
        mOnLongClickListener = listener
    }

    fun getOnLongItemClickListener(): ((M) -> Unit)? {
        return mOnLongClickListener
    }


    /**
     * 在第一个位置添加Item
     */
    fun addFirstItem(model: M) {
        mList.add(0, model)
        notifyItemInserted(0)
    }

    /**
     * 加入Item
     */

    open fun addItem(model: M) {
        mList.add(model)
        notifyItemInserted(mList.size)
    }

    fun addAll(list: List<M>) {
        mList.addAll(list)
        notifyItemRangeChanged(0, mList.size)
    }

    /**
     * 删除Item
     */

    open fun removeItem(position: Int) {
        notifyItemRemoved(position)
        mList.removeAt(position)
        if (mList.size == 0) {
            mList.clear()
        }
        notifyItemRangeChanged(position, mList.size)
    }

    fun getList(): ArrayList<M> {
        return mList
    }


    fun update() {
//        WLog.e("之前" + mList.size.toString())
        mList.clear()
        notifyDataSetChanged()
//        WLog.e("之后" + mList.size.toString())

        getListData(mList)

    }


    abstract fun getListData(list: ArrayList<M>)

    abstract fun onViewHolderCreate(parent: ViewGroup): VH

    abstract fun onViewHolderBind(holder: VH, model: M)

    abstract fun getEmptyMessage(): String

    open fun onClickItemEvent() {}

    open fun onLongClickEvent() {}
}