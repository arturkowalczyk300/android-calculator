package com.arturkowalczyk300.calculator

import android.widget.Toast
import androidx.lifecycle.ViewModel
import net.objecthunter.exp4j.Expression
import net.objecthunter.exp4j.ExpressionBuilder
import java.lang.Exception
import java.lang.NumberFormatException
import java.lang.StringBuilder

class MainViewModel: ViewModel() {
    var currentExpression: StringBuilder = StringBuilder()

    fun calculateResult(expression: String): Double{
            val exp: Expression =
                ExpressionBuilder(expression.replace("x", "*"))
                    .build()
            val result: Double = exp.evaluate()

            return result
    }

    fun isStringOperator(expression: String): Boolean {
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

    fun isStringNumber(expression: String): Boolean {
        var isNumber = true // init value

        try {
            Integer.parseInt(expression)
        } catch (ex: NumberFormatException) {
            isNumber = false
        }
        return isNumber
    }
}