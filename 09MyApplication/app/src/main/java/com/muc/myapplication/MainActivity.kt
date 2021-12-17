package com.muc.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import com.muc.myapplication.databinding.ActivityMainBinding
import com.muc.myapplication.interfaceCall.NativeInterfaceCallback
import com.muc.myapplication.interfaceCall.NativeThreadInterfaceCallback
import com.muc.myapplication.jni.EventInterfaceCallback

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Example of a call to a native method
        binding.sampleText.text = stringFromJNI()


        val event= EventInterfaceCallback()


           event.eventInterfaceCallback(object:NativeInterfaceCallback{

               override fun interfaceCallback() {
                  val  t=Thread.currentThread()
                Log.d("实现c++回调  当前线程是 : ",t.name)
               }
           })

           event.eventThreadInterfaceCallback(object:NativeThreadInterfaceCallback{

               override fun threadInterfaceCallback() {
                   val  t=Thread.currentThread()
                   Log.d("实现c++回调  当前线程是 : ",t.name)
               }
           })

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
}