package com.muc.myapplication.entity

import android.annotation.SuppressLint
import android.util.Log

class Entity {


    /**
     * C++ 调用java的实体类函数
     */
    @SuppressLint("LongLogTag")
    fun functionCallback(num: Int) {
        Log.d("functionCallback of parameter is", num.toString())
    }


    companion object {
        @SuppressLint("LongLogTag")
        @JvmStatic
        fun staticFunctionCallback(string: String?): String {

            if (string == null) {
                Log.d("staticFunctionCallback of parameter is ", "null")
            } else {
                Log.d("staticFunctionCallback of parameter is ", string)
            }
            return ""
        }

        @SuppressLint("LongLogTag")
        @JvmStatic
        fun staticFunctionCallback(stringArray: Array<String>?, num: Int): String {
            var a = 0
            stringArray?.let {
                it.forEach { i ->
                    Log.d("staticFunctionCallback Array is : ", i)

                }
            }



            Log.d("staticFunctionCallback num is", num.toString())
            return ""
        }
    }
}