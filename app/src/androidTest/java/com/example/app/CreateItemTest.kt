package com.example.app

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.rules.ActivityScenarioRule
import com.example.app.ui.MainActivity
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

// End-to-end test

@HiltAndroidTest
class CreateItemTest {

    @get:Rule(order = 0)
    var hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    var activityRule = ActivityScenarioRule(MainActivity::class.java)

    @Before
    fun init() {
        hiltRule.inject()
    }

    @Test
    fun createItemSuccess() {
        onView(withText("Home")).check(matches(isDisplayed()))

        onView(withId(R.id.fabAdd)).perform(click())

        onView(withText("Edit")).check(matches(isDisplayed()))

        onView(withId(R.id.etTitle)).perform(typeText("Title 123"))
        onView(withId(R.id.etDescription)).perform(typeText("Description abc"))

        onView(withText("SAVE")).perform(click())

        onView(withText("Home")).check(matches(isDisplayed()))
        onView(withText("Title 123")).check(matches(isDisplayed()))
        onView(withText("Description abc")).check(matches(isDisplayed()))
    }
}
