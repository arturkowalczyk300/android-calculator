package com.arturkowalczyk300.calculator

import android.view.View
import android.widget.AdapterView
import android.widget.ListView
import androidx.test.espresso.Espresso.onData
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import com.arturkowalczyk300.calculator.view.MainActivity
import org.hamcrest.CoreMatchers.*
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.TypeSafeMatcher
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
@LargeTest
class DialogCalculationsHistoryTest {
    @get:Rule
    val activityRule = ActivityScenarioRule(MainActivity::class.java)

    @Test
    fun test_openList() {
        onView(withId(R.id.main_options_menu_history))
            .perform(click())

        onView(withTagValue(`is`(MainActivity.DIALOG_CALCULATIONS_HISTORY_TAG)))
            .check(matches(isDisplayed()))

    }

    @Test
    fun test_add_calculation_to_history_and_restore_later() {
        onView(withTagValue(`is`("3")))
            .perform(click())
        onView(withTagValue(`is`("+")))
            .perform(click())
        onView(withTagValue(`is`("3")))
            .perform(click())
        onView(withTagValue(`is`("=")))
            .perform(click())

        onView(withTagValue(`is`("AC")))
            .perform(click())

        onView(withId(R.id.main_options_menu_history))
            .perform(click())

        onData(
            allOf(
                `is`(instanceOf(String::class.java)),
                `is`("3+3")
            )
        ).onChildView(withId(R.id.item_text)).perform(click())

        onView(withId(R.id.editTextExpression)).check(matches(withText("3+3")))
    }

    @Test
    fun test_add_calculation_to_history_and_delete_it() {
        onView(withTagValue(`is`("3")))
            .perform(click())
        onView(withTagValue(`is`("+")))
            .perform(click())
        onView(withTagValue(`is`("3")))
            .perform(click())
        onView(withTagValue(`is`("=")))
            .perform(click())

        onView(withId(R.id.main_options_menu_history))
            .perform(click())

        onData(
            allOf(
                `is`(instanceOf(String::class.java)),
                `is`("3+3")
            )
        ).check(matches(isDisplayed()))

        onData(
            allOf(
                `is`(instanceOf(String::class.java)),
                `is`("3+3")
            )
        ).onChildView(withId(R.id.item_button)).perform(click())


        onView(withId(R.id.dialog_lvCalculationsHistory)).check(
            matches(
                not(
                    matcherListViewWithStringDataContains(`is`("3+3"))
                )
            )
        )
    }

    @Test
    fun test_add_calculations_to_history_and_delete_all() {
        onView(withTagValue(`is`("3")))
            .perform(click())
        onView(withTagValue(`is`("+")))
            .perform(click())
        onView(withTagValue(`is`("3")))
            .perform(click())
        onView(withTagValue(`is`("=")))
            .perform(click())
        onView(withTagValue(`is`("AC")))
            .perform(click())

        onView(withTagValue(`is`("6")))
            .perform(click())
        onView(withTagValue(`is`("*")))
            .perform(click())
        onView(withTagValue(`is`("2")))
            .perform(click())
        onView(withTagValue(`is`("=")))
            .perform(click())

        onView(withId(R.id.main_options_menu_history)).perform(click())
        onView(withId(R.id.dialog_btnDeleteAll)).perform(click())

        onView(withId(R.id.dialog_lvCalculationsHistory)).check(matches(matcherListSizeEqual(0)))
    }

    private fun matcherListViewWithStringDataContains(dataMatcher: Matcher<Any?>): Matcher<View> {
        return object : TypeSafeMatcher<View>() {
            override fun describeTo(description: Description) {
                description.appendText("with class name: ")
                dataMatcher.describeTo(description)
            }

            override fun matchesSafely(view: View): Boolean {
                if (view !is AdapterView<*>)
                    return false

                val adapter = view.adapter
                for (i in 0 until adapter.count) {
                    val item = adapter.getItem(i)
                    if (dataMatcher.matches(item))
                        return true
                }

                return false
            }
        }
    }

    @Suppress("SameParameterValue")
    private fun matcherListSizeEqual(size: Int): Matcher<View>{
        return object: TypeSafeMatcher<View>(){
            override fun matchesSafely(item: View?): Boolean {
                return (item as ListView).count == size
            }

            override fun describeTo(description: Description?) {
                description?.appendText("ListView must have $size items")
            }
        }
    }
}