package com.arturkowalczyk300.calculator

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.*
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import com.arturkowalczyk300.calculator.model.CalculationEntity
import com.arturkowalczyk300.calculator.model.CalculationsHistoryDAO
import com.arturkowalczyk300.calculator.model.CalculationsHistoryDatabase
import com.google.common.truth.Truth.assertThat

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.*
import org.junit.runner.RunWith
import java.util.*


@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
@SmallTest
class DatabaseCalculationsHistoryTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var database: CalculationsHistoryDatabase
    private lateinit var dao: CalculationsHistoryDAO

    @Before
    fun setup() {
        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            CalculationsHistoryDatabase::class.java
        ).allowMainThreadQueries().build()

        dao = database.getDAO()
    }

    @After
    fun teardown() {
        database.close()
    }

    @Test
    fun insertCalculationsHistoryEntity() = runBlockingTest {
        val entity = CalculationEntity("2+4*2", 10.0, Date())
        dao.insertCalculationHistoryEntity(entity)

        val allItems = dao.getAllCalculationHistoryEntities().getOrAwaitValue()

        assertThat(allItems).contains(entity)
    }

    @Test
    fun deleteCalculationHistoryEntity() = runBlockingTest {

        val entity = CalculationEntity("2+4*2", 10.0, Date())
        dao.insertCalculationHistoryEntity(entity)
        dao.deleteCalculationHistoryEntity(entity)

        val allItems = dao.getAllCalculationHistoryEntities().getOrAwaitValue()

        assertThat(allItems).doesNotContain(entity)
    }

    @Test
    fun deleteCalculationHistoryEntityWithParameter() = runBlockingTest {
        val entity = CalculationEntity("2+4*2", 10.0, Date())

        dao.insertCalculationHistoryEntity(entity)
        dao.deleteCalculationHistoryEntity(entity.equation)

        val allItems = dao.getAllCalculationHistoryEntities().getOrAwaitValue()

        assertThat(allItems).doesNotContain(entity)
    }

    @Test
    fun deleteAllCalculationHistoryEntities() = runBlockingTest {
        val entity1 = CalculationEntity("2+4*2", 10.0, Date())
        val entity2 = CalculationEntity("4+4*2", 12.0, Date())
        val entity3 = CalculationEntity("5+4*2", 13.0, Date())

        dao.insertCalculationHistoryEntity(entity1)
        dao.insertCalculationHistoryEntity(entity2)
        dao.insertCalculationHistoryEntity(entity3)
        dao.deleteAllCalculationHistoryEntities()

        val allItems = dao.getAllCalculationHistoryEntities().getOrAwaitValue()

        assertThat(allItems).hasSize(0)
    }
}