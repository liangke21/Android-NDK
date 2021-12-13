//
// Created by 13967 on 2021/12/13.
//

#include "android_debug.h"
#include <jni.h>
/*
 * 嘟嘟哒哒哒多
 */
extern "C"
JNIEXPORT jstring JNICALL
Java_com_muc_myapplication_ConversionString_callNativeIntString(JNIEnv *env, jobject thiz,
                                                                jstring str) {

    const char *s = env->GetStringUTFChars(str, nullptr);

    LOGD("JNI JAVA string is %s", s);
    env->ReleaseStringUTFChars(str, s);//销毁
    return env->NewStringUTF("this is C++ style string");

}

extern "C"
JNIEXPORT void JNICALL
Java_com_muc_myapplication_ConversionString_StringModule(JNIEnv *env, jobject thiz, jstring j_str) {

    // j_str 为 "java string"
    const char *s = env->GetStringUTFChars(j_str, nullptr);
    LOGD("JNI JAVA is 打印j_str %s",  s);
    LOGD("JNI JAVA is 打印指针 %d", (int) s);

    char buf[128];

    int len = env->GetStringLength(j_str);
    LOGD("JNI JAVA  j_str length is %d", len);
    env->GetStringUTFRegion(j_str, 0, len - 1, buf);

    LOGD("JNI JAVA is %s", buf);//这里打印为乱码

    LOGD("JNI JAVA 打印buf指针 %d", (int) &buf);

    LOGD("JNI JAVA 打印buf[0]指针 %d", (int) &buf[0]);

    LOGD("JNI JAVA 打印buf[1]指针 %d", (int) &buf[1]);

    env->ReleaseStringUTFChars(j_str, s);
}