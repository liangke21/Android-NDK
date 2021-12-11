package com.muc.store.adapter

import android.app.Activity
import android.content.pm.PackageInfo
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.liulishuo.filedownloader.BaseDownloadTask
import com.liulishuo.filedownloader.FileDownloadListener
import com.liulishuo.filedownloader.FileDownloader
import com.muc.store.R
import com.muc.store.config.AppConfig
import com.muc.store.model.AppModel
import com.muc.store.util.ApkUtil
import com.muc.store.util.HttpUtils
import com.muc.store.util.NetWorkUtil
import com.muc.store.util.ToastUtil
import com.muc.store.viewHolder.AppViewHolder
import com.muc.wide.Utils.ConfigUtils
import com.muc.wide.base.BaseAdapter
import org.json.JSONObject
import java.io.File

import android.content.pm.PackageManager
import com.liulishuo.filedownloader.util.FileDownloadUtils

import com.muc.store.liveData.DownloadLiveData
import com.muc.store.liveData.DownloadState
import com.muc.store.model.DownloadManagerModel


open class AppAdapter(var activity: Activity) : BaseAdapter<AppViewHolder, AppModel>() {

    var mList: ArrayList<String> = ArrayList()
    var mDownloadManagerMap: HashMap<String, DownloadManagerModel> = HashMap()

    init {
        val packages: List<PackageInfo> = activity.packageManager.getInstalledPackages(0)
        packages.forEach {
            mList.add(it.packageName)
        }
    }

    private var mListener: (() -> Unit)? = null
    private var mHashMap: HashMap<String, Int> = HashMap()
    var isDownloading = false
    override fun getListData(list: ArrayList<AppModel>) {

    }

    fun setCallBack(listener: () -> Unit) {
        mListener = listener
    }

    fun update(url: String) {
        getList().clear()
        notifyDataSetChanged()
        HttpUtils(AppConfig.APP_HOST_URL + url).get()
            .setHttpListener(object : HttpUtils.HttpLister {
                override fun onSuccess(body: String?) {
                    JSONObject(body!!).apply {
                        if (getInt("length") == 0) {
                            activity.runOnUiThread {
                                ToastUtil.show("无数据")
                                return@runOnUiThread
                            }
                        }
                        for (index in 1..getInt("length")) {
                            getList().add(
                                AppModel(
                                    DownloadState.NOTDOWNLOAD,
                                    0,
                                    index,
                                    getJSONArray("icon")[index - 1].toString(),
                                    getJSONArray("name")[index - 1].toString(),
                                    getJSONArray("package")[index - 1].toString(),
                                    getJSONArray("version")[index - 1].toString(),
                                    getJSONArray("size")[index - 1].toString(),
                                    getJSONArray("date")[index - 1].toString(),
                                    getJSONArray("url")[index - 1].toString()
                                )
                            )
                        }
                        this@AppAdapter.activity.runOnUiThread {
                            notifyDataSetChanged()
                            mListener!!.invoke()
//                            mSwipeRefreshLayout.isRefreshing = false

                        }
                    }
                }

                override fun onError(error: String?) {
                    activity.runOnUiThread {
                        ToastUtil.show(error)
                    }

                }

            })
    }

    override fun onViewHolderCreate(parent: ViewGroup): AppViewHolder {
        return AppViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_main_app, parent, false)

        )
    }

    override fun onViewHolderBind(holder: AppViewHolder, model: AppModel) {
        var downloadId = 0
        holder.id.text = model.id.toString()
        Log.e("e", model.icon)
        Glide.with(holder.itemView.context).load(model.icon).asBitmap()
            .into(holder.icon)
        holder.name.text = model.name
        holder.version.text = "版本:" + model.version
        holder.size.text = "大小:" + model.size
        holder.data.text = "发布时间:" + model.data

        var mDownloadListener = object : FileDownloadListener() {
            override fun pending(
                task: BaseDownloadTask,
                soFarBytes: Int,
                totalBytes: Int
            ) {
            }

            override fun connected(
                task: BaseDownloadTask,
                etag: String,
                isContinue: Boolean,
                soFarBytes: Int,
                totalBytes: Int
            ) {
                sendLiveData(
                    DownloadManagerModel(
                        holder.layoutPosition,
                        this,
                        DownloadState.CONNECTION,
                        0,
                        model
                    )
                )
            }

            override fun progress(
                task: BaseDownloadTask,
                soFarBytes: Int,
                totalBytes: Int
            ) {
                if (File(AppConfig.getDataApkPath() + model.name + ".apk").exists() ||
                    File(FileDownloadUtils.getTempPath(AppConfig.getDataApkPath() + model.name + ".apk")).exists()
                ){
                    isDownloading = true
                    sendLiveData(
                        DownloadManagerModel(
                            holder.layoutPosition,
                            this,
                            DownloadState.DOWNLOADING,
                            (soFarBytes * 100 / totalBytes),
                            model
                        )
                    )
                }else{
                    sendLiveData(
                        DownloadManagerModel(
                            holder.layoutPosition,
                            this,
                            DownloadState.NOTDOWNLOAD,
                            0,
                            model
                        )
                    )
                }

            }

            override fun blockComplete(task: BaseDownloadTask) {

            }

            override fun retry(
                task: BaseDownloadTask,
                ex: Throwable,
                retryingTimes: Int,
                soFarBytes: Int
            ) {
            }

            override fun completed(task: BaseDownloadTask) {
                holder.download.text = "安装"
                isDownloading = false
                sendLiveData(
                    DownloadManagerModel(
                        holder.layoutPosition,
                        this,
                        DownloadState.COMPLETE,
                        100,
                        model
                    )
                )
            }

            override fun paused(task: BaseDownloadTask, soFarBytes: Int, totalBytes: Int) {
                isDownloading = false
                if (File(AppConfig.getDataApkPath() + model.name + ".apk").exists() ||
                    File(FileDownloadUtils.getTempPath(AppConfig.getDataApkPath() + model.name + ".apk")).exists()
                )
                    sendLiveData(
                        DownloadManagerModel(
                            holder.layoutPosition,
                            this,
                            DownloadState.PAUSE,
                            (soFarBytes * 100 / totalBytes),
                            model
                        )
                    ) else {
                    sendLiveData(
                        DownloadManagerModel(
                            holder.layoutPosition,
                            this,
                            DownloadState.NOTDOWNLOAD,
                            0,
                            model
                        )
                    )
                }
            }

            override fun error(task: BaseDownloadTask, e: Throwable) {
                isDownloading = false
                ToastUtil.show(e.message)

            }

            override fun warn(task: BaseDownloadTask) {
                isDownloading = false
            }
        }
        /**
         * 判断安装包是否下载完
         */
        when {

            /**
             * 还没下载完
             */
            File(AppConfig.getDataApkPath() + model.name + ".apk.temp").exists() -> {
                sendLiveData(
                    DownloadManagerModel(
                        holder.layoutPosition,
                        mDownloadListener,
                        DownloadState.PAUSE,
                        0,
                        model
                    )
                )
            }
            /**
             * 下载完成
             */
            File(AppConfig.getDataApkPath() + model.name + ".apk").exists() -> {
                sendLiveData(
                    DownloadManagerModel(
                        holder.layoutPosition,
                        null,
                        DownloadState.INSTALL,
                        0,
                        model
                    )
                )
            }
            /**
             * 打开
             */
            mList.contains(model.packageName) -> {
                sendLiveData(
                    DownloadManagerModel(holder.layoutPosition, null, DownloadState.OPEN, 0, model)
                )
            }
            /**
             * 下载
             */
            else -> {
                sendLiveData(
                    DownloadManagerModel(
                        holder.layoutPosition,
                        null,
                        DownloadState.NOTDOWNLOAD,
                        0,
                        model
                    )
                )
            }

        }

        holder.download.setOnClickListener {

            when (holder.download.text.toString()) {

                "安装" -> {
                    ApkUtil.installApp(
                        activity,
                        File(AppConfig.getDataApkPath() + model.name + ".apk")
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
                    downloadId = FileDownloader.getImpl().create(model.url)
                        .setPath(AppConfig.getDataApkPath() + model.name + ".apk")
                        .setListener(mDownloadListener)
                        .start()
                    mHashMap.put(model.name, downloadId)
                    /**
                     * 增加一条下载记录
                     */
                    ConfigUtils(AppConfig.getDownloadRecordConfig()).updateConf(
                        "record",
                        ConfigUtils(AppConfig.getDownloadRecordConfig()).getArrayList("record")
                            .apply {
                                put(model.name)
                            }
                    )
                    sendLiveData(
                        DownloadManagerModel(
                            holder.layoutPosition,
                            mDownloadListener,
                            DownloadState.DOWNLOADING,
                            0,
                            model
                        )
                    )
                }
                "继续" -> {
                    downloadId = FileDownloader.getImpl().create(model.url)
                        .setPath(AppConfig.getDataApkPath() + model.name + ".apk")
                        .setListener(mDownloadListener)
                        .start()
                    mHashMap.put(model.name, downloadId)

                }
                "打开" -> {
                    val packageManager: PackageManager = activity.packageManager
                    val intent = packageManager.getLaunchIntentForPackage(model.packageName)
                    activity.startActivity(intent)
                }
                else -> {
                    if (FileDownloader.getImpl() != null) {
                        FileDownloader.getImpl().pause(mDownloadListener)
                    }
                    sendLiveData(
                        DownloadManagerModel(
                            holder.layoutPosition,
                            mDownloadListener,
                            DownloadState.PAUSE,
                            0, model
                        )
                    )
                }
            }

        }
    }

    override fun getEmptyMessage(): String {
        return "这里什么都没有"
    }

    fun paAll() {
        FileDownloader.getImpl().pauseAll()
    }


    fun sendLiveData(model: DownloadManagerModel) {
        mDownloadManagerMap.put(model.model.name, model)
        DownloadLiveData.postValue(mDownloadManagerMap)
    }


}