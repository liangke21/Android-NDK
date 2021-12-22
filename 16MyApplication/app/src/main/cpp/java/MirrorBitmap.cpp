#include <jni.h>
#include<android/bitmap.h>
#include <cstring>
#include "../android_debug.h"
//
// Created by 13967 on 2021/12/22.

//<editor-fold desc="" >
jobject generateBitmap(JNIEnv *env,uint32_t width, uint32_t height);
//</editor-fold>
extern "C"
JNIEXPORT jobject JNICALL
Java_com_example_myapplication_jni_MirrorBitmap_mirrorBitmapL(JNIEnv *env, jobject thiz, jobject bitmap) {
    AndroidBitmapInfo bitmapInfo;
    AndroidBitmap_getInfo(env, bitmap, &bitmapInfo);
    LOGD("位图的高: %d", bitmapInfo.height);
    LOGD("位图的宽: %d", bitmapInfo.width);

    void *bitmapPixels;
    AndroidBitmap_lockPixels(env, bitmap, &bitmapPixels);//获取像素
    uint32_t newWidth = bitmapInfo.width;
    uint32_t newHeight = bitmapInfo.height;

    uint32_t *newBitmapPixels = new uint32_t[newWidth * newHeight];//位图镜像容器

    int whereToGet = 0;
    for (int i = 0; i < newHeight; ++i) {//位图镜像循环
        for (int j = newWidth - 1; j >= 0; j--) {
            uint32_t pixel = ((uint32_t *) bitmapPixels)[whereToGet++];
            newBitmapPixels[newWidth * i + j] = pixel;
        }
    }

    AndroidBitmap_unlockPixels(env, bitmap);

    jobject newBitmap = generateBitmap(env,newWidth,newHeight);//空位图

    void * resultBitmapPixels;
    AndroidBitmap_lockPixels(env, newBitmap, &resultBitmapPixels);//获取空位图像素

    memcpy((uint32_t*)resultBitmapPixels,newBitmapPixels,sizeof(uint32_t)*newWidth*newHeight);  //把镜像后的像素复制到新的bitmap上

    AndroidBitmap_unlockPixels(env, newBitmap);
    delete [] newBitmapPixels;
    return newBitmap;
}
/**
 * 创建一个空的位图
 * @param env
 * @param width
 * @param height
 * @return
 */
jobject generateBitmap(JNIEnv *env,uint32_t width, uint32_t height){

    jclass bitmapClazz = env->FindClass("android/graphics/Bitmap");
    jmethodID createBitmapFunction = env->GetStaticMethodID(bitmapClazz, "createBitmap", "(IILandroid/graphics/Bitmap$Config;)Landroid/graphics/Bitmap;");

    jstring configName=env->NewStringUTF("ARGB_8888");

    jclass bitmapConfigClass = env->FindClass("android/graphics/Bitmap$Config");//定义一个枚举类
    jmethodID mid = env->GetStaticMethodID(bitmapConfigClass, "valueOf", "(Ljava/lang/String;)Landroid/graphics/Bitmap$Config;");

    jobject bitmapConfig = env->CallStaticObjectMethod(bitmapConfigClass,mid,configName);

    jobject newBitmap=env->CallStaticObjectMethod(bitmapClazz,createBitmapFunction,(jint)width,(jint)height,bitmapConfig);
    //Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888); java 示例

    return newBitmap;
}