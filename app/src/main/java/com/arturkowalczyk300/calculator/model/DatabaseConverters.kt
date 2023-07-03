package com.arturkowalczyk300.calculator.model

import androidx.room.TypeConverter
import java.util.*

class DatabaseConverters {
    @TypeConverter
    fun fromDate(value: Date) = value.time

    @TypeConverter
    fun toDate(value: Long) = Date(value)
}