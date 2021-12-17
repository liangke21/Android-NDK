#include <jni.h>
#include <algorithm>
#include <pthread.h>
#include "../jvm/Jvm.h"

//
// Created by 13967 on 2021/12/15.
//
void *threadCallback(void *);

static jclass threadJClass;
static jmethodID threadJMethodID;
static jobject threadJObject;
extern "C"
JNIEXPORT void JNICALL
Java_com_muc_myapplication_jni_EventInterfaceCallback_eventInterfaceCallback(JNIEnv *env, jobject thiz, jobject native_interface_callback) {
    jclass clazz = env->GetObjectClass(native_interface_callback);
    jmethodID mid = env->GetMethodID(clazz, "interfaceCallback", "()V");
    env->CallVoidMethod(native_interface_callback, mid);
}

extern "C"
JNIEXPORT void JNICALL
Java_com_muc_myapplication_jni_EventInterfaceCallback_eventThreadInterfaceCallback(JNIEnv *env, jobject thiz, jobject native_thread_interface_callback) {
    threadJObject = env->NewGlobalRef(native_thread_interface_callback);
    threadJClass = env->GetObjectClass(native_thread_interface_callback);
    threadJMethodID = env->GetMethodID(threadJClass, "threadInterfaceCallback", "()V");


    pthread_t newThread;
    pthread_create(&newThread, nullptr, threadCallback, nullptr);
}
void *threadCallback(void *) {
    JavaVM *gvm = getJvm();
    JNIEnv *env = nullptr;

    if (gvm->AttachCurrentThread(&env, nullptr) == 0) {
        env->CallVoidMethod(threadJObject, threadJMethodID);
        gvm->DetachCurrentThread();
    }
    return nullptr;
}