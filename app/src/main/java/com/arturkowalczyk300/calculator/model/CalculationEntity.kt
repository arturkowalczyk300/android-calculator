package com.arturkowalczyk300.calculator.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "main_table")
data class CalculationEntity(
    @PrimaryKey
    @ColumnInfo(name = "equation")
    val equation: String,

    @ColumnInfo(name = "result")
    val result: Double,

    @ColumnInfo(name = "timestamp")
    val timestamp: Date
)
