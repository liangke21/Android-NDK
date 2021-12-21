package com.example.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import com.example.myapplication.databinding.ActivityMainBinding

import com.example.myapplication.jni.MyThread
import kotlin.concurrent.thread

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Example of a call to a native method
        //      binding.sampleText.text = stringFromJNI()
        val myThread = MyThread()

        binding.button5.setOnClickListener {
            val t = Thread.currentThread()
            Log.e("线程", " 打印进程pid: " + android.os.Process.myPid() + " 打印线程id: " + t.id);
            myThread.createNativeThread()
        }

        binding.button6.setOnClickListener {
            val t = Thread.currentThread()
            Log.e("线程", " 打印进程pid: " + android.os.Process.myPid() + " 打印线程id: " + t.id);
            myThread.createNativeThreadWithArgs()
        }


        binding.button7.setOnClickListener {
            val t = Thread.currentThread()
            Log.e("线程", " 打印进程pid: " + android.os.Process.myPid() + " 打印线程id: " + t.id);
            myThread.joinNativeThread()
        }

        binding.button.setOnClickListener {


            thread {

                Thread.sleep(300000)
            }

        }


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