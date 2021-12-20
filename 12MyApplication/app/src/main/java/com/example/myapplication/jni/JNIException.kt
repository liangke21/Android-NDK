package com.example.myapplication.jni

import kotlin.jvm.Throws

class JNIException {

    /**
     * 异常处理
     */
    external fun nativeInvokeJavaException()

    /**
     * 抛出异常
     */
    @Throws(IllegalAccessException::class)
    external fun nativeThrowJavaException()

    /**
     * 有异常的方法
     */
     fun operation():Int{

        return 2/0;
    }
}