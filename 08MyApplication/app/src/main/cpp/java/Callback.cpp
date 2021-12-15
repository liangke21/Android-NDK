//
// Created by 13967 on 2021/12/15.
//

#include "Callback.h"
#include "../android_debug.h"
#include <jni.h>
#include <iostream>

extern "C"
JNIEXPORT void JNICALL
Java_com_muc_myapplication_jni_Callback_functionCallback(JNIEnv *env, jobject thiz, jobject entity) {
    jclass clazz = env->GetObjectClass(entity);
    jmethodID mid = env->GetMethodID(clazz, "functionCallback", "(I)V");
    env->CallVoidMethod(entity, mid, 2);

}
using namespace std;
extern "C"
JNIEXPORT void JNICALL
Java_com_muc_myapplication_jni_Callback_staticFunctionCallback(JNIEnv *env, jobject thiz, jobject entity) {
    jclass clazz = env->GetObjectClass(entity);
    jmethodID mid = env->GetStaticMethodID(clazz, "staticFunctionCallback", "(Ljava/lang/String;)Ljava/lang/String;");
    jstring str = env->NewStringUTF(nullptr);
    env->CallStaticObjectMethod(clazz, mid, str);


    mid = env->GetStaticMethodID(clazz, "staticFunctionCallback", "([Ljava/lang/String;I)Ljava/lang/String;");
    jclass clazzString = env->FindClass("java/lang/String");
    int size = 100;
    jobjectArray strArray = env->NewObjectArray(size, clazzString, nullptr);  //1 长度 2 数组类型 3 默认值 //创建一个数组
    jstring strItem;
    for (int i = 0; i < size; ++i) {
        strItem = env->NewStringUTF("C++天下无敌");
        env->SetObjectArrayElement(strArray, i, strItem);// 1,那个数组 2,那个下标,3,要添加的参数
    }

    env->CallStaticObjectMethod(clazz, mid, strArray, size);
}