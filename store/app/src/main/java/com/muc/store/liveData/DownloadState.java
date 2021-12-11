package com.muc.store.liveData;

public enum DownloadState {
    CONNECTION,//连接中
    NOTDOWNLOAD,
    DOWNLOADING,//正在下载
    COMPLETE,//完成
    ERROR,//错误
    PAUSE,//暂停
    INSTALL,//安装
    OPEN,//打开
}
