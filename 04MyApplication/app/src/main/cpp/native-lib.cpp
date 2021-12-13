#include <jni.h>
#include <string>
#include "android_debug.h"
extern "C" JNIEXPORT jstring JNICALL
Java_com_muc_myapplication_MainActivity_stringFromJNI(
        JNIEnv* env,
        jobject /* this */) {
    std::string hello = "Hello from C++";
    return env->NewStringUTF(hello.c_str());
}
/*
 * 用于以二进制形式表示 T 实例的位数
 */
template < typename T >
int sizeBITS() {

    return sizeof(typeid(T).name())*8;
}


extern "C"
JNIEXPORT jint JNICALL
Java_com_muc_myapplication_MainActivity_callNativeInt(JNIEnv *env, jobject thiz, jint sum) {
    LOGD("JAVA jint value is %d 实例字节 : %d 实例位数 : %d", sum,sizeof(jint), sizeBITS<jint>());
    int c_sum=sum;
    return c_sum;
}
extern "C"
JNIEXPORT jshort JNICALL
Java_com_muc_myapplication_MainActivity_callNativeShort(JNIEnv *env, jobject thiz, jshort sh) {
     LOGD("JAVA jshort value is %d 实例字节 : %d 实例位数 : %d", sh,sizeof(jshort),sizeBITS<jshort>());

     return sh;
}
extern "C"
JNIEXPORT jchar JNICALL
Java_com_muc_myapplication_MainActivity_callNativeChar(JNIEnv *env, jobject thiz, jchar ch) {
      LOGD("JAVA jchar value is %d 实例字节 : %d 实例位数 : %d",ch,sizeof(jchar),sizeof(jchar)*8);
      return ch;
}
extern "C"
JNIEXPORT jbyte JNICALL
Java_com_muc_myapplication_MainActivity_callNativeByte(JNIEnv *env, jobject thiz, jbyte b) {
       LOGD("JAVA jbyte value is %d 实例字节 : %d 实例位数 : %d",b,sizeof(jbyte),sizeof(jbyte)*8);
       return b;
}
extern "C"
JNIEXPORT jfloat JNICALL
Java_com_muc_myapplication_MainActivity_callNativeFloat(JNIEnv *env, jobject thiz, jfloat f) {
        LOGD("JAVA float value is %f 实例字节 : %d 实例位数 : %d",f,sizeof(jfloat),sizeof(jfloat)*8);
        return f;
}
extern "C"
JNIEXPORT jlong JNICALL
Java_com_muc_myapplication_MainActivity_callNativeLong(JNIEnv *env, jobject thiz, jlong l) {
        LOGD("jAVA jlong value is %lld 实例字节 : %d 实例位数 %d",l,sizeof(jlong),sizeof(jlong)*8);
        return l;
}
extern "C"
JNIEXPORT jdouble JNICALL
Java_com_muc_myapplication_MainActivity_callNativeDouble(JNIEnv *env, jobject thiz, jdouble d) {
        LOGD("JAVA jdouble value is %f 实例字节 : %d 实例位数 %d",d,sizeof(jdouble),sizeof(jdouble)*8);
        return d;
}
extern "C"
JNIEXPORT jboolean JNICALL
Java_com_muc_myapplication_MainActivity_callNativeBoolean(JNIEnv *env, jobject thiz, jboolean b) {
        LOGD("JAVA jboolean value is %d 实例字节 : %d 实例位数 %d",b,sizeof(jboolean),sizeof(jboolean)*8);
        return b;
}