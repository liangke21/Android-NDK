package com.muc.store.model

import com.muc.store.liveData.DownloadState

data class AppModel(
    var state:DownloadState,
    var progress:Int,
    var id: Int,
    var icon: String,
    var name: String,
    var packageName:String,
    var version: String,
    var size: String,
    var data: String,
    var url: String
)