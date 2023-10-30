package com.example.app.ui.detail

import androidx.core.os.bundleOf
import androidx.navigation.NavHostController
import androidx.test.core.app.ApplicationProvider.getApplicationContext
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import com.example.app.R
import com.example.app.data.ItemRepository
import com.example.app.data.model.Item
import com.example.app.launchFragmentInHiltContainer
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import javax.inject.Inject

// Test a fragment in isolation

@HiltAndroidTest
class DetailFragmentTest {
    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @Inject
    lateinit var repository: ItemRepository

    @Before
    fun init() {
        hiltRule.inject()
    }

    @Test
    fun showItemCorrectly() {
        runBlocking {
            repository.insert(Item(1, "title 1", "description 1"))

            launchFragmentInHiltContainer<DetailFragment>(
                fragmentArgs = bundleOf("id" to 1),
                navHostController = NavHostController(getApplicationContext())
            )

            onView(withId(R.id.tvDescription)).check(matches(withText("description 1")))
        }
    }
}
