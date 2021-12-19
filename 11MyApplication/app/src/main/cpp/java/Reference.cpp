//
// Created by 13967 on 2021/12/19.
//

#include "Reference.h"
#include "../android_debug.h"
#include <jni.h>
/*
 * 全局引用
 */
extern "C"
JNIEXPORT jstring JNICALL
Java_com_example_myapplication_jni_Quote_globalReference(JNIEnv *env, jobject thiz) {
    static jclass stringClass = nullptr;
    if (stringClass == nullptr) {
        jclass clazz = env->FindClass("java/lang/String"); //定义一个String 局部方法

        stringClass = static_cast<jclass>(env->NewGlobalRef(clazz));
        //    stringClass= static_cast<jclass>(env->NewLocalRef(clazz));//不能使用局部变量JNI DETECTED ERROR IN APPLICATION: use of deleted local reference 0x75

        env->DeleteLocalRef(clazz);
    } else {
        LOGD("引用 : 使用全局变量的缓存");
    }

    jmethodID mid = env->GetMethodID(stringClass, "<init>", "(Ljava/lang/String;)V");

    jstring str = env->NewStringUTF("天下无敌C++");

    return static_cast<jstring>(env->NewObject(stringClass, mid, str));

}
/*
 * 引用局部变量
 */
extern "C"
JNIEXPORT jstring JNICALL
Java_com_example_myapplication_jni_Quote_localReference(JNIEnv *env, jobject thiz) {
    jclass clazz = env->FindClass("java/lang/String"); //定义一个String 局部方法

    jmethodID mid = env->GetMethodID(clazz, "<init>", "(Ljava/lang/String;)V");

    jstring str = env->NewStringUTF("C++天下无敌");
    //  env->DeleteLocalRef(clazz); //释放局部引用
    return static_cast<jstring>(env->NewObject(clazz, mid, str));

}
extern "C"
JNIEXPORT void JNICALL
Java_com_example_myapplication_jni_Quote_weakApplication(JNIEnv *env, jobject thiz) {
    static jclass stringClass = nullptr;
    if (stringClass == nullptr) {
        jclass clazz = env->FindClass("java/lang/String"); //定义一个String 局部方法

        stringClass = static_cast<jclass>(env->NewWeakGlobalRef(clazz));

        env->DeleteLocalRef(clazz);
    } else {
        LOGD("引用 : 使用弱引用");
    }

    jmethodID mid = env->GetMethodID(stringClass, "<init>", "(Ljava/lang/String;)V");

     LOGD("引用   弱引用");

    jboolean isGc=env->IsSameObject(stringClass,nullptr); //如果返回的是false  说明已经被gc了;

}