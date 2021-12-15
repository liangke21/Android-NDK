package com.muc.myapplication.jni

import com.muc.myapplication.entity.Entity

class Callback {
/*
    companion object {
        // Used to load the 'myapplication' library on application startup.
        init {
            System.loadLibrary("myapplication")
        }
    }*/
    external fun functionCallback(entity: Entity)

    external fun staticFunctionCallback(entity: Entity)
}