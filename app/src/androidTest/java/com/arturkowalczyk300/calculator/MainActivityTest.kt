package com.arturkowalczyk300.calculator

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.*
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import org.hamcrest.CoreMatchers.not
import org.hamcrest.*
import org.hamcrest.CoreMatchers.`is`
import org.junit.Assert.*
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@LargeTest
class MainActivityTest {
    @get:Rule
    val activityRule = ActivityScenarioRule(MainActivity::class.java)

    // ************************
    // * digit buttons tests
    // ************************
    @Test
    fun test_isButtonDigit1ClickCausingAppendingValidCharacterToExpression() {
        onView(ViewMatchers.withTagValue(`is`("1")))
            .perform(click())

        onView(ViewMatchers.withId(R.id.editTextExpression))
            .check(matches(withText("1")))
    }

    @Test
    fun test_isButtonDigit2ClickCausingAppendingValidCharacterToExpression() {
        onView(ViewMatchers.withTagValue(`is`("2")))
            .perform(click())

        onView(ViewMatchers.withId(R.id.editTextExpression))
            .check(matches(withText("2")))
    }

    @Test
    fun test_isButtonDigit3ClickCausingAppendingValidCharacterToExpression() {
        onView(ViewMatchers.withTagValue(`is`("3")))
            .perform(click())

        onView(ViewMatchers.withId(R.id.editTextExpression))
            .check(matches(withText("3")))
    }

    @Test
    fun test_isButtonDigit4ClickCausingAppendingValidCharacterToExpression() {
        onView(ViewMatchers.withTagValue(`is`("4")))
            .perform(click())

        onView(ViewMatchers.withId(R.id.editTextExpression))
            .check(matches(withText("4")))
    }

    @Test
    fun test_isButtonDigit5ClickCausingAppendingValidCharacterToExpression() {
        onView(ViewMatchers.withTagValue(`is`("5")))
            .perform(click())

        onView(ViewMatchers.withId(R.id.editTextExpression))
            .check(matches(withText("5")))
    }

    @Test
    fun test_isButtonDigit6ClickCausingAppendingValidCharacterToExpression() {
        onView(ViewMatchers.withTagValue(`is`("6")))
            .perform(click())

        onView(ViewMatchers.withId(R.id.editTextExpression))
            .check(matches(withText("6")))
    }

    @Test
    fun test_isButtonDigit7ClickCausingAppendingValidCharacterToExpression() {
        onView(ViewMatchers.withTagValue(`is`("7")))
            .perform(click())

        onView(ViewMatchers.withId(R.id.editTextExpression))
            .check(matches(withText("7")))
    }

    @Test
    fun test_isButtonDigit8ClickCausingAppendingValidCharacterToExpression() {
        onView(ViewMatchers.withTagValue(`is`("8")))
            .perform(click())

        onView(ViewMatchers.withId(R.id.editTextExpression))
            .check(matches(withText("8")))
    }

    @Test
    fun test_isButtonDigit9ClickCausingAppendingValidCharacterToExpression() {
        onView(ViewMatchers.withTagValue(`is`("9")))
            .perform(click())

        onView(ViewMatchers.withId(R.id.editTextExpression))
            .check(matches(withText("9")))
    }

    // ************************
    // * operators buttons tests
    // ************************
    @Test
    fun test_operationAddition() {
        onView(ViewMatchers.withTagValue(`is`("3")))
            .perform(click())

        onView(ViewMatchers.withTagValue(`is`("+")))
            .perform(click())

        onView(ViewMatchers.withTagValue(`is`("4")))
            .perform(click())

        onView(ViewMatchers.withTagValue(`is`("=")))
            .perform(click())

        onView(ViewMatchers.withId(R.id.tvResult))
            .check(matches(withText("7.0")))
    }

    @Test
    fun test_operationSubtraction() {
        onView(ViewMatchers.withTagValue(`is`("9")))
            .perform(click())

        onView(ViewMatchers.withTagValue(`is`("-")))
            .perform(click())

        onView(ViewMatchers.withTagValue(`is`("4")))
            .perform(click())

        onView(ViewMatchers.withTagValue(`is`("=")))
            .perform(click())

        onView(ViewMatchers.withId(R.id.tvResult))
            .check(matches(withText("5.0")))
    }

    @Test
    fun test_operationMultiplication() {
        onView(ViewMatchers.withTagValue(`is`("2")))
            .perform(click())

        onView(ViewMatchers.withTagValue(`is`("*")))
            .perform(click())

        onView(ViewMatchers.withTagValue(`is`("3")))
            .perform(click())

        onView(ViewMatchers.withTagValue(`is`("=")))
            .perform(click())

        onView(ViewMatchers.withId(R.id.tvResult))
            .check(matches(withText("6.0")))
    }

    @Test
    fun test_operationDivision() {
        onView(ViewMatchers.withTagValue(`is`("8")))
            .perform(click())

        onView(ViewMatchers.withTagValue(`is`("/")))
            .perform(click())

        onView(ViewMatchers.withTagValue(`is`("4")))
            .perform(click())

        onView(ViewMatchers.withTagValue(`is`("=")))
            .perform(click())

        onView(ViewMatchers.withId(R.id.tvResult))
            .check(matches(withText("2.0")))
    }

    @Test
    fun test_operationModulo() {
        onView(ViewMatchers.withTagValue(`is`("5")))
            .perform(click())

        onView(ViewMatchers.withTagValue(`is`("%")))
            .perform(click())

        onView(ViewMatchers.withTagValue(`is`("2")))
            .perform(click())

        onView(ViewMatchers.withTagValue(`is`("=")))
            .perform(click())

        onView(ViewMatchers.withId(R.id.tvResult))
            .check(matches(withText("1.0")))
    }

    // ************************
    // * expression delete and one character remove tests
    // ************************
    @Test
    fun test_deleteOneDigit() {
        onView(ViewMatchers.withTagValue(`is`("5")))
            .perform(click())
        onView(ViewMatchers.withTagValue(`is`("2")))
            .perform(click())
        onView(ViewMatchers.withTagValue(`is`("1")))
            .perform(click())

        onView(ViewMatchers.withTagValue(`is`("DEL")))
            .perform(click())

        onView(ViewMatchers.withId(R.id.editTextExpression))
            .check(matches(withText("52")))
    }

    @Test
    fun test_deleteWholeExpression() {
        onView(ViewMatchers.withTagValue(`is`("5")))
            .perform(click())
        onView(ViewMatchers.withTagValue(`is`("2")))
            .perform(click())
        onView(ViewMatchers.withTagValue(`is`("1")))
            .perform(click())

        onView(ViewMatchers.withTagValue(`is`("AC")))
            .perform(click())

        onView(ViewMatchers.withId(R.id.editTextExpression))
            .check(matches(withText("")))
    }

    @Test
    fun test_orderOfOperations() {
        onView(ViewMatchers.withTagValue(`is`("5")))
            .perform(click())
        onView(ViewMatchers.withTagValue(`is`("+")))
            .perform(click())
        onView(ViewMatchers.withTagValue(`is`("2")))
            .perform(click())
        onView(ViewMatchers.withTagValue(`is`("*")))
            .perform(click())
        onView(ViewMatchers.withTagValue(`is`("6")))
            .perform(click())
        onView(ViewMatchers.withTagValue(`is`("/")))
            .perform(click())
        onView(ViewMatchers.withTagValue(`is`("3")))
            .perform(click())

        onView(ViewMatchers.withTagValue(`is`("=")))
            .perform(click())

        onView(ViewMatchers.withId(R.id.tvResult))
            .check(matches(withText("9.0")))

    }

}