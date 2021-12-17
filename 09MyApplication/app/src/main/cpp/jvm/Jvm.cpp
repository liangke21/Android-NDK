//
// Created by 13967 on 2021/12/15.
//

#include <jni.h>
#include "Jvm.h"
static JavaVM *gVM = nullptr;
#ifdef __cplusplus
extern "C" {
#endif
    void setJVM(JavaVM *jvm){
        gVM=jvm;
    }

    JavaVM* getJvm(){
        return gVM;
    }

#ifdef __cplusplus
}
#endif
