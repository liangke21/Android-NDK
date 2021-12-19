package com.example.myapplication.jni

class Quote {


    /**
     * 全局引用
     */
    external fun globalReference():String

    /**
     * 局部引用
     */
    external fun localReference():String


    /**
     * 弱应用
     */
    external fun weakApplication()
}