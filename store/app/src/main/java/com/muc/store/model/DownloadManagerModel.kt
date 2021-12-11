package com.muc.store.model

import com.liulishuo.filedownloader.FileDownloadListener
import com.muc.store.liveData.DownloadState

data class DownloadManagerModel(
    var position:Int,
    var downloadListener: FileDownloadListener?,
    var state: DownloadState,
    val progress: Int,
    var model: AppModel
)