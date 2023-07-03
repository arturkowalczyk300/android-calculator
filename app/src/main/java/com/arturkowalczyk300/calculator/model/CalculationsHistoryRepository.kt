package com.arturkowalczyk300.calculator.model

import android.content.Context
import androidx.lifecycle.LiveData
import kotlinx.coroutines.runBlocking

class CalculationsHistoryRepository {
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