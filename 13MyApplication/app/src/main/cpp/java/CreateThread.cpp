//
// Created by 13967 on 2021/12/21.
//

#include "CreateThread.h"
#include "../android_debug.h"
#include <jni.h>
#include <pthread.h>
#include <unistd.h>

#include <thread>


void *createThread(void *) {
    LOGD("线程创建成功");
    LOGD("打印进程名字: %d, 打印线程名字 %d", getpid(),std::this_thread::get_id());
    return nullptr;
}

extern "C"
JNIEXPORT void JNICALL
Java_com_example_myapplication_jni_MyThread_createNativeThread(JNIEnv *env, jobject thiz) {
    pthread_t handles;

    int isThread = pthread_create(&handles, nullptr, createThread, nullptr);

    if (isThread == 0) {
        LOGD("线程创建成功2");
    } else {
        LOGD("线程创建失败");
    }

}

struct ThreadRunArgs {
    int id;
    int name;
};

void *createThreadToPassInParameters(void *arg) {

    ThreadRunArgs *args = static_cast<ThreadRunArgs *>(arg);

    LOGD("打印 id %d", args->id);

    LOGD("打印 name %d", args->name);
    LOGD("打印进程名字: %d, 打印线程名字 %d", getpid(),std::this_thread::get_id());
    pthread_exit(nullptr);
}

extern "C"
JNIEXPORT void JNICALL
Java_com_example_myapplication_jni_MyThread_createNativeThreadWithArgs(JNIEnv *env, jobject thiz) {
    pthread_t handles2;
    ThreadRunArgs *th = new ThreadRunArgs();
    th->id = 1;
    th->name = 2;
    int isThread = pthread_create(&handles2, nullptr, createThreadToPassInParameters, th);

    if (isThread == 0) {
        LOGD("线程创建成功2");
    } else {
        LOGD("线程创建失败");
    }
}

void *threadHang(void *arg) {
    ThreadRunArgs *args = static_cast<ThreadRunArgs *>(arg);
    LOGD("打印进程pid: %d, 打印线程id %d", getpid(),std::this_thread::get_id());
    struct timeval beginTime; //声明时间
    gettimeofday(&beginTime, nullptr);//获取时间

    sleep(333);//耗时操作

    struct timeval endTime;//声明时间
    gettimeofday(&endTime, nullptr);//获取时间


    LOGD("拢共耗时  %ld", endTime.tv_sec - beginTime.tv_sec);
    return reinterpret_cast<void *>(args->name);
}

/*
 * 线程挂起
 */
extern "C"
JNIEXPORT void JNICALL
Java_com_example_myapplication_jni_MyThread_joinNativeThread(JNIEnv *env, jobject thiz) {
    pthread_t handles2;
    ThreadRunArgs *th = new ThreadRunArgs();
    th->id = 1;
    th->name = 2;
    pthread_create(&handles2, nullptr, threadHang, th);

    void *ret = nullptr;

    pthread_join(handles2, &ret); //返回挂起函数的参数

    LOGD("线程返回的参数是 %d", ret);
}