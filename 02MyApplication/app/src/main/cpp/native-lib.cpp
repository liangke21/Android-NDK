#include <jni.h>
#include <string>

#include <people.h>
//#include "people/People.h"

//#include "People/People.h"
#include <People.h>
extern "C" JNIEXPORT jstring JNICALL
Java_com_example_a02myapplication_MainActivity_stringFromJNI(
        JNIEnv* env,
        jobject /* this */) {
    People  pe;

    std::string hello = "Hello from C++";
   // return env->NewStringUTF(hello.c_str());

    return env->NewStringUTF(pe.getString().c_str());
}
extern "C"
JNIEXPORT jstring JNICALL
Java_com_example_a02myapplication_MainActivity_getString(JNIEnv *env, jobject thiz) {
    // TODO: implement getString()
}