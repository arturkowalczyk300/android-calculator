package com.arturkowalczyk300.calculator

import android.opengl.Visibility
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import android.widget.Toast
import net.objecthunter.exp4j.Expression
import net.objecthunter.exp4j.ExpressionBuilder
import java.lang.Exception
import java.lang.Integer.parseInt
import java.lang.NumberFormatException
import java.lang.StringBuilder
import kotlin.math.exp


class MainActivity : AppCompatActivity() {
    private lateinit var textViewExpression: TextView
    private lateinit var textViewResult: TextView
    private lateinit var textViewLabelEqual: TextView
    private var currentExpression: StringBuilder = StringBuilder()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        textViewExpression = findViewById(R.id.tvExpression)
        textViewResult = findViewById(R.id.tvResult)
        textViewLabelEqual = findViewById(R.id.tvLabelEqual)
    }

    fun buttonOnClickListener(view: View) {
        switchResultVisibility(false) //expression changed

        val tag: String = view.tag.toString()

        if (isStringNumber(tag)) {
            currentExpression.append(tag)
        } else if (isStringOperator(tag)) {
            val isPreviousCharacterOperator =
                if (currentExpression.isNotEmpty())
                    isStringOperator(currentExpression[currentExpression.length - 1].toString())
                else
                    false
            if (tag == "-" //exception condition to make typing negative numbers possible
                || !isPreviousCharacterOperator
            ) {
                currentExpression.append(tag)
            }
        } else if (tag == "DEL") {
            if (currentExpression.isNotEmpty())
                currentExpression.setLength((currentExpression.length - 1)) //delete last character
        } else if (tag == "AC") {
            currentExpression.clear()
        } else if (tag == "=") {
            calculateResult(currentExpression.toString())
            switchResultVisibility(true) //result ready
        }


        textViewExpression.text = currentExpression
    }

    private fun calculateResult(expression: String) {
        try {
            val exp: Expression =
                ExpressionBuilder(expression.replace("x", "*"))
                    .build()
            val result: Double = exp.evaluate()

            textViewResult.text = result.toString()
        } catch (exc: Exception) {
            Toast.makeText(applicationContext, exc.toString(), Toast.LENGTH_LONG).show()
        }
    }

    private fun isStringOperator(expression: String): Boolean {
        var isOperator = false

        when (expression) {
            "x" -> isOperator = true
            "+" -> isOperator = true
            "/" -> isOperator = true
            "-" -> isOperator = true
            "%" -> isOperator = true
            "." -> isOperator = true
        }
        return isOperator
    }

    private fun isStringNumber(expression: String): Boolean {
        var isNumber = true // init value

        try {
            parseInt(expression)
        } catch (ex: NumberFormatException) {
            isNumber = false
        }
        return isNumber
    }

    private fun switchResultVisibility(visible: Boolean) {
        val visibility = if (visible) View.VISIBLE else View.GONE
        textViewResult.visibility = visibility
        textViewLabelEqual.visibility = visibility
    }
}