//
// Created by 13967 on 2021/12/15.
//

#ifndef MY_APPLICATION_JVM_H
#define MY_APPLICATION_JVM_H

#ifdef __cplusplus
extern "C" {
#endif

void setJVM(JavaVM *jvm);
JavaVM *getJvm();

#ifdef __cplusplus
}
#endif


#endif //MY_APPLICATION_JVM_