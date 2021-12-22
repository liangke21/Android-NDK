//
// Created by 13967 on 2021/12/22.
//

#include "MutexAndConditionVariables.h"
#include "../android_debug.h"
#include <jni.h>
#include <pthread.h>


pthread_mutex_t mutex; //互斥变量  对某个临界区进行加锁,使得任意时刻只有一个线程能够执行,可以通过解锁让当前线程退出临界区,以便其他线程可以继续执行临界区的代码.这样就实现了多线程之间的互斥
pthread_cond_t cond; //条件变量  是线程之间的唤醒和释放

pthread_t waitHandle;//线程等待
pthread_t notifyHandle;//线程通知

int flag = 0;

void *waitThread(void *) {
    LOGD("wait lock");
    pthread_mutex_lock(&mutex);//将尝试锁定互斥体

    while (flag == 0) {//等于0就一直阻塞
        LOGD("线程等待中.....");
        pthread_cond_wait(&cond, &mutex);//阻塞调用线程，直到发出指定条件的信号
        //通过wait阻塞住了所以没有一直打印
    }

    LOGD("wait unlock");
    pthread_mutex_unlock(&mutex);//将解锁一个互斥体
    pthread_exit(0);
}

void *notifyThread(void *) {
    LOGD("notify lock");
    pthread_mutex_lock(&mutex);//将尝试锁定互斥体

    flag = 1;

    pthread_mutex_unlock(&mutex);//将解锁一个互斥体

    pthread_cond_signal(&cond);//该例程用于发出信号（或唤醒）正在等待条件变量的另一个线程
    LOGD("signal...");
    LOGD("notify unlock");
    pthread_exit(0);
}

/*
 * 等待线程
 *
 *
 */
extern "C"
JNIEXPORT void JNICALL
Java_com_example_myapplication_jni_MutexAndConditionVariables_mutexVariables(JNIEnv *env, jobject thiz) {
    pthread_mutex_init(&mutex, nullptr);//互斥初始化
    pthread_cond_init(&cond, nullptr);//条件变量初始化

    pthread_create(&waitHandle, nullptr, waitThread, nullptr);

}

/*
 * 唤醒线程
 */
extern "C"
JNIEXPORT void JNICALL
Java_com_example_myapplication_jni_MutexAndConditionVariables_conditionVariables(JNIEnv *env, jobject thiz) {

    pthread_create(&notifyHandle, nullptr, notifyThread, nullptr);
}