#include <jni.h>
#include <string>

extern "C" JNIEXPORT jstring JNICALL
Java_com_muc_myapplication_MainActivity_stringFromJNI(
        JNIEnv *env,
        jobject /* this */) {
    std::string hello = "Hello from C++";
    return env->NewStringUTF(hello.c_str());
}

#define JAVA_CLASS "com/muc/myapplication/load/JNIDyNamIcLoad"

jstring getMessage(JNIEnv *env, jobject /* this */) {

    return env->NewStringUTF("我是动态注册");
}

jint plus(JNIEnv *env, jobject /* this */, int x, int y) {

    return x + y;
}

static JNINativeMethod gMethod[] = {
        {"stringFromJNI", "()Ljava/lang/String;", (void *) getMessage},
        {"sum",           "(II)I",                (void *) plus}
};

int registerNativeMethods(JNIEnv *env, const char *name, JNINativeMethod *methods, jint nMethods) {

    jclass js;
    js = env->FindClass(name);

    if (js == nullptr) {
        return JNI_FALSE;
    }

    if (env->RegisterNatives(js, methods, nMethods) < 0) {
        return JNI_FALSE;
    }

    return JNI_TRUE;
}


//动态注册
extern "C" JNIEXPORT int JNICALL JNI_OnLoad(JavaVM *vm, void *reserved) {
    JNIEnv *env;
    if (vm->GetEnv(reinterpret_cast<void **>(&env), JNI_VERSION_1_6) != JNI_OK) {
        return JNI_FALSE;
    }


    registerNativeMethods(env, JAVA_CLASS, gMethod, 2);

    return JNI_VERSION_1_6;
}