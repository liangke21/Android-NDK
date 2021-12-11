package com.muc.store.model

import android.graphics.drawable.Drawable

data class UninstallAppModel(
    var icon: Drawable,
    var name: String,
    var size:String,
    var version: String,
    var packageName: String
)