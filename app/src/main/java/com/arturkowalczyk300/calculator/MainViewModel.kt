package com.arturkowalczyk300.calculator

import androidx.lifecycle.ViewModel
import net.objecthunter.exp4j.Expression
import net.objecthunter.exp4j.ExpressionBuilder
import java.lang.NumberFormatException
import java.lang.StringBuilder

class MainViewModel: ViewModel() {
    var currentExpression: StringBuilder = StringBuilder()

    fun calculateResult(expression: String): Double {
        val exp: Expression =
            ExpressionBuilder(expression)
                .build()

        return exp.evaluate()
    }

    fun isStringOperator(expression: String): Boolean {
        var isOperator = false

        when (expression) {
            "*" -> isOperator = true
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