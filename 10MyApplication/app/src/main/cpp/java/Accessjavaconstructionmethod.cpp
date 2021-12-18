//
// Created by 13967 on 2021/12/17.
//

#include "Accessjavaconstructionmethod.h"
#include <jni.h>

extern "C"
JNIEXPORT jobject JNICALL
Java_com_example_myapplication_jni_EntityClass_getEntity(JNIEnv *env, jobject thiz) {
    jclass clazz = env->FindClass("com/example/myapplication/entity/Entity");
    jmethodID mid = env->GetMethodID(clazz, "<init>", "(Ljava/lang/String;)V");
    jstring str = env->NewStringUTF("访问到java的构造方法");
    jobject entity = env->NewObject(clazz, mid, str);
    return entity;
}
extern "C"
JNIEXPORT jobject JNICALL
Java_com_example_myapplication_jni_EntityClass_getEntity2(JNIEnv *env, jobject thiz) {
    jclass clazz = env->FindClass("com/example/myapplication/entity/Entity");
    jmethodID mid = env->GetMethodID(clazz, "<init>", "(Ljava/lang/String;)V");
    jobject entity = env->AllocObject(clazz); //分配对象
    jstring str = env->NewStringUTF("访问到java的构造方法2");
    env->CallNonvirtualVoidMethod(entity, clazz, mid, str);
    return entity;

}