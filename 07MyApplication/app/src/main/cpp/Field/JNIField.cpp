//
// Created by 13967 on 2021/12/14.
//

#include "JNIField.h"
#include <jni.h>

extern "C"
JNIEXPORT void JNICALL
Java_com_muc_myapplication_JNIField_setField(JNIEnv *env, jobject thiz, jobject animal) {

    jclass cls = env->GetObjectClass(animal); //获取实体类

    jfieldID fID = env->GetFieldID(cls, "name", "Ljava/lang/String;");//获取变量名称 和类型

    jstring string = env->NewStringUTF("满大人"); //定义jstring参数

    env->SetObjectField(animal, fID, string); //修改参数


}

extern "C"
JNIEXPORT void JNICALL
Java_com_muc_myapplication_JNIField_getStaticField(JNIEnv *env, jobject thiz, jobject animal) {
    jclass cls = env->GetObjectClass(animal);

    jfieldID num = env->GetStaticFieldID(cls, "num", "I");

    jint it = env->GetStaticIntField(cls, num);

    env->SetStaticIntField(cls, num, ++it);

}

extern "C"
JNIEXPORT void JNICALL
Java_com_muc_myapplication_JNIField_staticField(JNIEnv *env, jclass clazz) {

    jfieldID fid = env->GetStaticFieldID(clazz, "num", "I");

    jint num = env->GetStaticIntField(clazz, fid);

    env->SetStaticIntField(clazz, fid, ++num);

}