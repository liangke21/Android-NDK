package com.muc.myapplication

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import com.muc.myapplication.databinding.ActivityMainBinding
import com.muc.myapplication.load.JNIDyNamIcLoad

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

             val jn = JNIDyNamIcLoad

        // Example of a call to a native method
        binding.sampleText.text = "helloWorld"

        binding.sampleText.setOnClickListener {


        }
    }


}