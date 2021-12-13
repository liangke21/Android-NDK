package com.muc.myapplication

class ConversionString {

    companion object{
        init {
            System.loadLibrary("ConversionString")
        }
    }

    external fun callNativeIntString(str:String):String

   // external fun StringModule(jStr:String):Void   //不能 这样写会报错的


    external fun StringModule(jStr:String)
}