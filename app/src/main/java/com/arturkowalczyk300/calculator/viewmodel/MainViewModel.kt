package com.arturkowalczyk300.calculator.viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.arturkowalczyk300.calculator.model.CalculationEntity
import com.arturkowalczyk300.calculator.model.CalculationsHistoryRepository
import net.objecthunter.exp4j.Expression
import net.objecthunter.exp4j.ExpressionBuilder
import java.lang.NumberFormatException
import java.lang.StringBuilder
import java.util.*

class MainViewModel : ViewModel() {
    var currentExpression: StringBuilder = StringBuilder()
    private var repository: CalculationsHistoryRepository

    init {
        repository = CalculationsHistoryRepository()
    }

    fun initDatabase(context: Context) {
        repository.initDatabase(context)
    }

    fun calculateResult(expression: String, currentDate: Date = Date(0)): Double {
        val exp: Expression =
            ExpressionBuilder(expression)
                .build()
        val result = exp.evaluate()

        repository.insertCalculationHistoryEntity(
            CalculationEntity(
                expression,
                result,
                Date(0)
            )
        )

        return result
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

    fun getAllCalculationHistoryEntities(): LiveData<List<CalculationEntity>> {
        return repository.getAllCalculationHistoryEntities()
    }

    fun insertCalculationHistoryEntity(entity: CalculationEntity) {
        repository.insertCalculationHistoryEntity(entity)
    }

    fun deleteCalculationHistoryEntity(entity: CalculationEntity) {
        repository.deleteCalculationHistoryEntity(entity)
    }

    fun deleteAllCalculationHistoryEntities() {
        repository.deleteAllCalculationHistoryEntities()
    }
}