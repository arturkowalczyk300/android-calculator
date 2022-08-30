package com.arturkowalczyk300.calculator.model

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Room

class CalculationsHistoryRepository() {
    private var database: CalculationsHistoryDatabase? = null
    private var dao: CalculationsHistoryDAO? = null

    fun initDatabase(context: Context){
        database = CalculationsHistoryDatabase.getInstance(context)
        dao = database!!.getDAO()
    }

    fun getAllCalculationHistoryEntities(): LiveData<CalculationEntity> {
        return dao!!.getAllCalculationHistoryEntities()
    }

    fun insertCalculationHistoryEntity(entity: CalculationEntity) {
        dao!!.insertCalculationHistoryEntity(entity)
    }

    fun deleteCalculationHistoryEntity(entity: CalculationEntity) {
        dao!!.deleteCalculationHistoryEntity(entity)
    }

    fun deleteAllCalculationHistoryEntities() {
        dao!!.deleteAllCalculationHistoryEntities()
    }
}