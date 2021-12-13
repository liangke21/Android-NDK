package com.muc.myapplication

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import com.muc.myapplication.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    @SuppressLint("LongLogTag")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Example of a call to a native method
        binding.sampleText.text = callNativeInt(2).toString()
        Log.d("\tJAVA int value is android    \t","\t${callNativeInt(2).toString()}"+"    \t实例的字节数\t : ${Int.SIZE_BYTES} \t实例的位数: ${Int.SIZE_BITS} ")

        Log.d("\tJAVA byte value is android   \t", "\t${callNativeByte(2).toString()}"+"     \t实例的字节数\t : ${Byte.SIZE_BYTES} \t实例的位数: ${Byte.SIZE_BITS} ")

        Log.d("\tJAVA short value is android  \t", "\t${callNativeShort(2).toString()}"+"    \t实例的字节数\t : ${Short.SIZE_BYTES} \t实例的位数: ${Short.SIZE_BITS} ")

        Log.d("\tJAVA float value is android  \t","\t${callNativeFloat(2.0f).toString()}"+"   \t实例的字节数\t : ${Float.SIZE_BYTES} \t实例的位数: ${Float.SIZE_BITS} ")

        Log.d("\tJAVA char value is android   \t","\t${callNativeChar('A').toString()}"+"    \t实例的字节数\t : ${Char.SIZE_BYTES} \t实例的位数: ${Char.SIZE_BITS} ")

        Log.d("\tJAVA double value is android \t","\t${callNativeDouble(2.0).toString()}"+"   \t实例的字节数\t : ${Double.SIZE_BYTES} \t实例的位数: ${Double.SIZE_BITS} ")

        Log.d("\tJAVA boolean value is android\t","\t${callNativeBoolean(true).toString()}"+" \t实例的字节数\t : 1 \t实例的位数: 8 ")
    }

    /**
     * A native method that is implemented by the 'myapplication' native library,
     * which is packaged with this application.
     */
    external fun stringFromJNI(): String

    companion object {
        // Used to load the 'myapplication' library on application startup.
        init {
            System.loadLibrary("myapplication")
        }
    }


    external fun callNativeInt(sum:Int):Int

    external fun callNativeByte(b:Byte):Byte

    external fun callNativeChar(ch:Char):Char

    external fun callNativeShort(sh:Short):Short

    external fun callNativeFloat(f:Float):Float

    external fun callNativeLong(l:Long):Long

    external fun callNativeDouble(d:Double):Double

    external fun callNativeBoolean(b:Boolean):Boolean
}