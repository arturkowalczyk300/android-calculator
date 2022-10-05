package com.arturkowalczyk300.calculator.model

import android.content.Context
import androidx.room.*

@Database(entities = [CalculationEntity::class], version = 3)
@TypeConverters(DatabaseConverters::class)
abstract class CalculationsHistoryDatabase : RoomDatabase() {
    abstract fun getDAO(): CalculationsHistoryDAO

    companion object {
        @Volatile private var dbInstance: CalculationsHistoryDatabase? = null

        fun getInstance(context: Context): CalculationsHistoryDatabase {
            if (dbInstance == null)
                dbInstance =
                    Room.databaseBuilder(
                        context,
                        CalculationsHistoryDatabase::class.java,
                        "CalculationsHistoryDatabase"
                    )
                        .fallbackToDestructiveMigration()
                        .build()
            return dbInstance!!
        }
    }
}