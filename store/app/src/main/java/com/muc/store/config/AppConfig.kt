package com.muc.store.config

import java.io.File

class AppConfig {
    companion object {
        const val APP_HOST_URL = "http://120.25.243.12/api/"
        fun getDataApkPath(): String {
            return checkPath(getAppFilePath() + "apks/")
        }

        fun getAppFilePath(): String {
            return checkPath("/storage/emulated/0/Android/data/com.fjht.appmarket/files/")
        }

        fun getAppConfigPath(): String {
            return getAppFilePath() + "/app.json"
        }

        fun getSearchRecordPath(): String {
            return getAppFilePath() + "/record.json"
        }
        fun getDownloadRecordConfig(): String {
            return getAppFilePath() + "/download.json"
        }

        fun checkPath(path: String): String {
            if (!File(path).exists()) {
                File(path).mkdirs()
            }
            return path
        }
    }
}