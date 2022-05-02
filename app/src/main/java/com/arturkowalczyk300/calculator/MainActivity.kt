package com.arturkowalczyk300.calculator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button

class MainActivity : AppCompatActivity() {
    //var exampleBtn: Button = findViewById(R.id.btnAC)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //exampleBtn.setOnClickListener(fc())
    }

    fun buttonOnClickListener(view: View){Log.v(
        "myApp",
        "clicked button with tag:${view.getTag()}"
    )}

}