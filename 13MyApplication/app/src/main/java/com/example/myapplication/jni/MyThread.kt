package com.example.myapplication.jni

class MyThread {
    /**
     * 创建线程
     */
    external fun createNativeThread()

    /**
     * 创建带有参数的线程
     */
    external fun createNativeThreadWithArgs()

    /**
     * 获取线程运行的结果,相当于线程挂起
     */
    external fun joinNativeThread()

}
