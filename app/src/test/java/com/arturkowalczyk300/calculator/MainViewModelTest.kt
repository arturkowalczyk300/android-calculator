package com.arturkowalczyk300.calculator

import com.arturkowalczyk300.calculator.viewmodel.MainViewModel
import org.junit.Assert.*
import org.junit.Before

import org.junit.Test

class MainViewModelTest {
    private lateinit var myViewModel: MainViewModel

    @Before
    fun setUp() {
        myViewModel = MainViewModel()
    }

    @Test
    fun calculateResultAdd() {
        val result: Double = myViewModel.calculateResult("-2.1+4.3")
        assertEquals(2.2, result, 0.0001)
    }

    @Test
    fun calculateResultSubtract() {
        val result: Double = myViewModel.calculateResult("4-5.5")
        assertEquals(-1.5, result, 0.0001)
    }

    @Test
    fun calculateResultMultiply() {
        val result: Double = myViewModel.calculateResult("4*2.1")
        assertEquals(8.4, result, 0.0001)
    }

    @Test
    fun calculateResultDivide() {
        val result: Double = myViewModel.calculateResult("3.2/2")
        assertEquals(1.6, result, 0.0001)
    }

    @Test
    fun calculateResultDivideByZero() {
        assertThrows("Division by zero!", java.lang.ArithmeticException::class.java) {
            myViewModel.calculateResult("5.0/0")
        }
    }

    @Test
    fun calculateResultModulo() {
        val result: Double = myViewModel.calculateResult("5%2")
        assertEquals(1.0, result, 0.0001)
    }


    @Test
    fun calculateResultOrderOfMathematicalOperations() {
        val result: Double = myViewModel.calculateResult("-2*3+5^2")
        assertEquals(19.0, result, 0.0001)
    }

    @Test
    fun testIsStringOperator() {
        assertTrue(myViewModel.isStringOperator("*"))
        assertTrue(myViewModel.isStringOperator("+"))
        assertTrue(myViewModel.isStringOperator("/"))
        assertTrue(myViewModel.isStringOperator("-"))
        assertTrue(myViewModel.isStringOperator("%"))
        assertTrue(myViewModel.isStringOperator("."))
        assertFalse(myViewModel.isStringOperator("3")) //anything else
        assertFalse(myViewModel.isStringOperator("a")) //anything else
    }

    @Test
    fun isStringNumber() {
        assertTrue(myViewModel.isStringNumber("432"))
        assertTrue(myViewModel.isStringNumber("1"))
        assertTrue(myViewModel.isStringNumber("-2"))
        assertFalse(myViewModel.isStringNumber("*"))
        assertFalse(myViewModel.isStringNumber("DEL"))
    }
}