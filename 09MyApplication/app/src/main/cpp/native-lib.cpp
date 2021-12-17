#include <jni.h>
#include <string>
#include "android_debug.h"
#include "jvm/Jvm.h"

extern "C" JNIEXPORT jstring JNICALL
Java_com_muc_myapplication_MainActivity_stringFromJNI(
        JNIEnv *env,
        jobject /* this */) {
    std::string hello = "Hello from C++";
    return env->NewStringUTF(hello.c_str());
}

extern "C" JNIEXPORT int JNICALL JNI_OnLoad(JavaVM *vm, void *reserved) {

    JNIEnv *env;

  if(vm->GetEnv(reinterpret_cast<void **>(&env), JNI_VERSION_1_6 )!= JNI_OK){
        return JNI_FALSE;
    }
    setJVM(vm);

    return JNI_VERSION_1_6;
}