//
// Created by 13967 on 2021/12/22.
//

#include "ProducerAndConsumer.h"
#include "../android_debug.h"
#include <jni.h>
#include <pthread.h>
#include <queue>

using namespace std;
queue<int> data;

pthread_mutex_t mutex;
pthread_cond_t cond;

/*
 * 生产者
 */
void *producerThread(void *) {

    pthread_mutex_lock(&mutex);
    data.push(1);

    if (data.size() > 0) {//通知消费者进行消费
        LOGD("完成生产");
        pthread_cond_signal(&cond);
    }

    pthread_mutex_unlock(&mutex);
    pthread_exit(0);
}

/*
 * 消费者
 */
void *consumerThread(void *) {

    pthread_mutex_lock(&mutex);
    if (data.size() > 0) {
        LOGD("消费物品");
        data.pop();//删除首个元素
    } else {
        LOGD("没有! 等待生产....");
        pthread_cond_wait(&cond, &mutex);
    }

    pthread_mutex_unlock(&mutex);

    pthread_exit(0);
}

pthread_t producerHandle;
pthread_t consumerHandle;
/**
 * 生产者
 */
extern "C"
JNIEXPORT void JNICALL
Java_com_example_myapplication_jni_ProducerAndConsumer_producer(JNIEnv *env, jobject thiz) {
    pthread_mutex_init(&mutex, nullptr);
    pthread_cond_init(&cond, nullptr);


    pthread_create(&producerHandle, nullptr, producerThread, nullptr);
}
/**
 * 消费者
 */
extern "C"
JNIEXPORT void JNICALL
Java_com_example_myapplication_jni_ProducerAndConsumer_consumer(JNIEnv *env, jobject thiz) {

    pthread_create(&consumerHandle, nullptr, consumerThread, nullptr);
}