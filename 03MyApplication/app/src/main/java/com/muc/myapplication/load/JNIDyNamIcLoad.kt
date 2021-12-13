package com.muc.myapplication.load

class JNIDyNamIcLoad {


    companion object {
        // Used to load the 'myapplication' library on application startup.
        init {
            System.loadLibrary("myapplication")
        }
    }

    /**
     * A native method that is implemented by the 'myapplication' native library,
     * which is packaged with this application.
     */
    external fun stringFromJNI(): String

    external fun sum(x:Int ,y:Int): Int



}