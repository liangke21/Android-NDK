package com.muc.myapplication

import com.muc.myapplication.entity.Animal
import kotlin.properties.Delegates

class JNIField {


    /*
       companion object {
            // Used to load the 'myapplication' library on application startup.
            init {
                System.loadLibrary("myapplication")//目前还没有发现  每个文件要不要引用这个c++库
            }
        }
    */
    companion object {
        @JvmStatic
         val num=0;
         @JvmStatic
        external fun staticField()
    }

    external fun setField(animal: Animal)

    external fun getStaticField(animal: Animal)


}