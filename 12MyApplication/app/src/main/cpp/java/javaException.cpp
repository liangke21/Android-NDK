//
// Created by 13967 on 2021/12/20.
//

#include "javaException.h"
#include <jni.h>

/**
 * 处理异常
 */
extern "C"
JNIEXPORT void JNICALL
Java_com_example_myapplication_jni_JNIException_nativeInvokeJavaException(JNIEnv *env, jobject thiz) {
    jclass clazz = env->FindClass("com/example/myapplication/jni/JNIException");

    jmethodID mid = env->GetMethodID(clazz, "operation", "()I");//有异常的方法

    jmethodID mid2 = env->GetMethodID(clazz, "<init>", "()V");


    jobject obj = env->NewObject(clazz, mid2);//new 一个对象

    env->CallIntMethod(obj, mid);//调用方法

    jthrowable exc = env->ExceptionOccurred();//发生异常

    if (exc) {
        env->ExceptionDescribe();//打印异常信息
        env->ExceptionClear();//清除异常
    }


}

/**
 * 抛出异常
 */

extern "C"
JNIEXPORT void JNICALL
Java_com_example_myapplication_jni_JNIException_nativeThrowJavaException(JNIEnv *env, jobject thiz) {

    jclass clazz = env->FindClass("java/lang/IllegalAccessException");

    env->ThrowNew(clazz, "这里异常了肿么办");//抛出异常  的一个参数 抛出那个异常类,抛出异常参数


}