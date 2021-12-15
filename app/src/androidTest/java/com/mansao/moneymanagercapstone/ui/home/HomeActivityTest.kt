package com.mansao.moneymanagercapstone.ui.home

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.platform.app.InstrumentationRegistry.getInstrumentation
import androidx.test.runner.lifecycle.ActivityLifecycleMonitorRegistry
import androidx.test.runner.lifecycle.Stage
import com.mansao.moneymanagercapstone.R
import com.mansao.moneymanagercapstone.ui.addtask.MoneyAddUpdateActivity
import org.junit.Assert.*
import org.junit.Rule
import org.junit.Test

class HomeActivityTest {
    @get:Rule
    var activityRule = ActivityScenarioRule(HomeActivity::class.java)

    @Test
    fun runTest(){
        onView(withId(R.id.rv_list)).check(matches(isDisplayed()))
        onView(withId(R.id.fab_add)).perform(click())

        getInstrumentation().runOnMainSync {
            run{
                val addUpdateActivity = ActivityLifecycleMonitorRegistry.getInstance()
                    .getActivitiesInStage(Stage.RESUMED).elementAtOrNull(0)
                assertTrue(addUpdateActivity?.javaClass == MoneyAddUpdateActivity::class.java)
            }

        }
    }


}