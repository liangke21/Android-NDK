package com.muc.myapplication

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import com.muc.myapplication.databinding.ActivityMainBinding
import com.muc.myapplication.entity.Animal

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    @SuppressLint("LongLogTag")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Example of a call to a native method
        binding.sampleText.text = stringFromJNI()

        val jf = JNIField()
        val an = Animal("蛮吉")

        jf.setField(an)
        jf.getStaticField(an)
        JNIField.staticField()

        Log.d("Animal of name Field is :",an.name)

        Log.d("Animal of static num Field is :", Animal.num.toString())

        Log.d("Animal JNIField static num is :", JNIField.num.toString())

    }

    /**
     * A native method that is implemented by the 'myapplication' native library,
     * which is packaged with this application.
     */
    external fun stringFromJNI(): String

    companion object {
        // Used to load the 'myapplication' library on application startup.
        init {
            System.loadLibrary("myapplication")  //可以每个kotlin 类里面引用C++库
        }
    }
}