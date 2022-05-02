package com.arturkowalczyk300.calculator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import java.lang.Integer.parseInt
import java.lang.NumberFormatException
import java.lang.StringBuilder


class MainActivity : AppCompatActivity() {
    lateinit var textViewExpression: TextView
    var currentExpression: StringBuilder = StringBuilder()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        textViewExpression = findViewById(R.id.tvExpression)
    }

    fun buttonOnClickListener(view: View) {
        val tag: String = view.getTag().toString()

        if (isStringNumber(tag)) {
            currentExpression.append(tag)
        } else if (isStringOperator(tag)) {
            if (!isStringOperator(  //ensure if last character is not operator
                    Character.toString(
                        currentExpression.get(currentExpression.length - 1)
                    )
                )
            ) {
                currentExpression.append(tag)
            }
        } else if (tag == "DEL") {
            if (currentExpression.length > 0)
                currentExpression.setLength((currentExpression.length - 1)) //delete last char
        } else if (tag == "AC") {
            currentExpression.clear()
        } else if (tag == "=")
            calculateResult()

        Log.v("myApp", currentExpression.toString())
        textViewExpression.setText(currentExpression)
    }

    private fun calculateResult() {
        Log.v("myApp", "calculate result")
    }

    fun isStringOperator(expression: String): Boolean {
        var isOperator = false

        when (expression) {
            "x" -> isOperator = true
            "+" -> isOperator = true
            "/" -> isOperator = true
            "-" -> isOperator = true
            "%" -> isOperator = true
        }
        return isOperator
    }

    fun isStringNumber(expression: String): Boolean {
        var isNumber = true // init value

        try {
            parseInt(expression)
        } catch (ex: NumberFormatException) {
            isNumber = false
        }
        return isNumber
    }

}