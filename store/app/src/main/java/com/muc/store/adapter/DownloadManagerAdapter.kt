package com.muc.store.adapter

import android.annotation.SuppressLint
import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.liulishuo.filedownloader.FileDownloader
import com.liulishuo.filedownloader.util.FileDownloadUtils
import com.muc.store.R
import com.muc.store.activity.DownloadManagerActivity
import com.muc.store.config.AppConfig
import com.muc.store.liveData.DownloadLiveData
import com.muc.store.liveData.DownloadState
import com.muc.store.model.DownloadManagerModel
import com.muc.store.util.ApkUtil
import com.muc.store.util.NetWorkUtil
import com.muc.store.util.ToastUtil
import com.muc.store.viewHolder.DownloadManagerViewHolder
import com.muc.wide.Utils.ConfigUtils
import com.muc.wide.base.BaseAdapter
import java.io.File

class DownloadManagerAdapter(var activity: DownloadManagerActivity) :
    BaseAdapter<DownloadManagerViewHolder, DownloadManagerModel>() {

    private var mCallBack: (() -> Unit)? = null

    var mList: ArrayList<String> = ArrayList()

    init {
        val packages: List<PackageInfo> = activity.packageManager.getInstalledPackages(0)
        packages.forEach {
            mList.add(it.packageName)
        }
        getList().clear()
        var mFile = File(AppConfig.getDataApkPath()).listFiles()
        DownloadLiveData.value?.forEach {
            Log.e("key", it.key)
        }
        mFile.forEach {
//            DownloadLiveData.value?
            Log.e("key", it.name.replace(".apk", ""))
            getList().add(
                DownloadLiveData.value?.get(
                    it.name.substring(
                        0,
                        it.name.indexOf(".")
                    )
                )!!
            )
        }
        notifyDataSetChanged()
//        mCallBack?.invoke()
    }

    fun setCallBack(listener: (() -> Unit)) {
        mCallBack = listener
    }

    override fun getListData(list: ArrayList<DownloadManagerModel>) {

    }

    override fun onViewHolderCreate(parent: ViewGroup): DownloadManagerViewHolder {
        return DownloadManagerViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_download_manager, parent, false)
        )
    }

    @SuppressLint("SetTextI18n")
    override fun onViewHolderBind(holder: DownloadManagerViewHolder, model: DownloadManagerModel) {

        holder.id.text = (holder.layoutPosition + 1).toString()
        holder.name.text = model.model.name
        Glide.with(holder.itemView.context).load(model.model.icon).asBitmap()
            .into(holder.icon)

        holder.delete.setOnClickListener {
            try {
                if (File(AppConfig.getDataApkPath() + model.model.name + ".apk").exists()) {
                    File(AppConfig.getDataApkPath() + model.model.name + ".apk").delete()
                }
                if (File(FileDownloadUtils.getTempPath(AppConfig.getDataApkPath() + model.model.name + ".apk")).exists()) {
                    File(FileDownloadUtils.getTempPath(AppConfig.getDataApkPath() + model.model.name + ".apk")).delete()
                }
                if (FileDownloader.getImpl() != null) {

                    FileDownloader.getImpl().pause(model.downloadListener)
                    activity.sendLiveData(
                        DownloadManagerModel(
                            holder.layoutPosition,
                            model.downloadListener,
                            DownloadState.NOTDOWNLOAD,
                            0,
                            model.model
                        )
                    )
                }
                getList().removeAt(getPosition(model.position))
                notifyItemRemoved(holder.layoutPosition)


            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
        holder.operation.setOnClickListener {
            when (holder.operation.text.toString()) {
                "安装" -> {
                    ApkUtil.installApp(
                        activity,
                        File(AppConfig.getDataApkPath() + model.model.name + ".apk")
                    )
                }
                "下载" -> {
                    if (NetWorkUtil.checkNetWorkType(activity) == "MOBILE" && !ConfigUtils(AppConfig.getAppConfigPath()).getBoolean(
                            "ALLOW_MOBILE_DOWNLOAD"
                        )

                    ) {
                        ToastUtil.show("已禁止移动数据下载")
                        return@setOnClickListener
                    }
                    FileDownloader.getImpl().create(model.model.url)
                        .setPath(AppConfig.getDataApkPath() + model.model.name + ".apk")
                        .setListener(model.downloadListener)
                        .start()
                    /**
                     * 增加一条下载记录
                     */
                    ConfigUtils(AppConfig.getDownloadRecordConfig()).updateConf(
                        "record",
                        ConfigUtils(AppConfig.getDownloadRecordConfig()).getArrayList("record")
                            .apply {
                                put(model.model.name)
                            }
                    )
                    activity.sendLiveData(
                        DownloadManagerModel(
                            holder.layoutPosition,
                            model.downloadListener,
                            DownloadState.DOWNLOADING,
                            0,
                            model.model
                        )
                    )
                }
                "继续" -> {
                    FileDownloader.getImpl().create(model.model.url)
                        .setPath(AppConfig.getDataApkPath() + model.model.name + ".apk")
                        .setListener(model.downloadListener)
                        .start()

                }
                "打开" -> {
                    val packageManager: PackageManager = activity.getPackageManager()
                    val intent = packageManager.getLaunchIntentForPackage(model.model.packageName)
                    activity.startActivity(intent)
                }
                /**
                 * 暂停
                 */
                else -> {
                    if (FileDownloader.getImpl() != null) {
                        FileDownloader.getImpl().pause(model.downloadListener)
                    }
                    activity.sendLiveData(
                        DownloadManagerModel(
                            holder.layoutPosition,
                            model.downloadListener,
                            DownloadState.PAUSE,
                            model.progress,
                            model.model
                        )
                    )
                }
            }

        }

    }

    override fun getEmptyMessage(): String {
        return "暂无下载任务"
    }

    fun getPosition(position: Int): Int {
        for (index in 1..getList().size) {
            if (getList()[index - 1].position == position) {
                return index - 1
            }
        }
        return 0
    }

    fun existItem(name: String): Boolean {
        getList().forEach {
            if (name == it.model.name) {
                return true
            }
        }
        return false
    }
}