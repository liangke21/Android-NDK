#include <jni.h>
#include <string>
#include "android_debug.h"

extern "C" JNIEXPORT jstring JNICALL
Java_com_muc_myapplication_MainActivity_stringFromJNI(
        JNIEnv *env,
        jobject /* this */) {
    std::string hello = "Hello from C++";
    return env->NewStringUTF(hello.c_str());
}
extern "C"
JNIEXPORT jstring JNICALL
Java_com_muc_myapplication_MainActivity_stringArray(JNIEnv *env, jobject thiz,
                                                    jobjectArray string_array) {

    int len = env->GetArrayLength(string_array);

    LOGD("this string_array of length is %d", len);

    jstring StrArray = static_cast<jstring>(env->GetObjectArrayElement(string_array, 0));

    const char *s = env->GetStringUTFChars(StrArray, nullptr);

    LOGD("s  is %s", s);

    env->ReleaseStringUTFChars(StrArray, s);

    return env->NewStringUTF(s);
}