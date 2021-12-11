package com.muc.store.liveData

import androidx.lifecycle.LiveData
import com.muc.store.model.DownloadManagerModel

object DownloadLiveData : MutableLiveData<HashMap<String,DownloadManagerModel>>()
open class MutableLiveData<T> : LiveData<T>() {
    public override fun postValue(value: T) {
        super.postValue(value)
    }

    public override fun setValue(value: T) {
        super.setValue(value)
    }
}