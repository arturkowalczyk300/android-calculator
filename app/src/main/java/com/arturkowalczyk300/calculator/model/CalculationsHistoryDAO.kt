package com.arturkowalczyk300.calculator.model

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy.IGNORE
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query

@Dao
interface CalculationsHistoryDAO {
    @Query("SELECT * FROM main_table")
    fun getAllCalculationHistoryEntities(): LiveData<List<CalculationEntity>>

    @Insert(onConflict = REPLACE)
    suspend fun insertCalculationHistoryEntity(entity: CalculationEntity)

    @Delete
    suspend fun deleteCalculationHistoryEntity(entity: CalculationEntity)

    @Query("DELETE FROM main_table WHERE equation=:entityEquation")
    suspend fun deleteCalculationHistoryEntity(entityEquation: String)

    @Query("DELETE FROM main_table")
    suspend fun deleteAllCalculationHistoryEntities()
}