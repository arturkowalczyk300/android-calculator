package com.arturkowalczyk300.calculator.model

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Room
import kotlinx.coroutines.runBlocking
import java.util.*

class CalculationsHistoryRepository() {
    private var database: CalculationsHistoryDatabase? = null
    private var dao: CalculationsHistoryDAO? = null

    fun initDatabase(context: Context) {
        database = CalculationsHistoryDatabase.getInstance(context)
        dao = database!!.getDAO()
    }

    fun getAllCalculationHistoryEntities(): LiveData<List<CalculationEntity>> {
            return dao!!.getAllCalculationHistoryEntities()
    }

    fun insertCalculationHistoryEntity(entity: CalculationEntity) {
        return runBlocking {
            dao!!.insertCalculationHistoryEntity(entity)
        }
    }

    fun deleteCalculationHistoryEntity(entity: CalculationEntity) {
        return runBlocking {
            dao!!.deleteCalculationHistoryEntity(entity)
        }
    }

    fun deleteCalculationHistoryEntity(entityEquation: String) {
        return runBlocking {
            dao!!.deleteCalculationHistoryEntity(entityEquation)
        }
    }

    fun deleteAllCalculationHistoryEntities() {
        return runBlocking {
            dao!!.deleteAllCalculationHistoryEntities()
        }
    }
}