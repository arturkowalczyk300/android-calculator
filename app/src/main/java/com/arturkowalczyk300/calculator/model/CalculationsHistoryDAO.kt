package com.arturkowalczyk300.calculator.model

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface CalculationsHistoryDAO {
    @Query("SELECT * FROM main_table")
    fun getAllCalculationHistoryEntities(): LiveData<CalculationEntity>

    @Insert
    fun insertCalculationHistoryEntity(entity: CalculationEntity)

    @Delete
    fun deleteCalculationHistoryEntity(entity: CalculationEntity)

    @Query("DELETE FROM main_table")
    fun deleteAllCalculationHistoryEntities()
}