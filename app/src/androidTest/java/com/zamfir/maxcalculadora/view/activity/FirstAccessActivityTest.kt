package com.zamfir.maxcalculadora.view.activity

import androidx.test.espresso.Espresso
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.zamfir.maxcalculadora.R
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class FirstAccessActivityTest {


    @Rule
    val activityScenarioRule = ActivityScenarioRule(FirstAccessActivity::class.java)

    @Test
    fun realizaPrimeiroAcesso(){
        onView(withId(R.id.salario)).perform(typeText("3000"))
        onView(withId(R.id.nome)).perform(typeText("Joaquim"))

        Espresso.closeSoftKeyboard()

        onView(withId(R.id.fab_next)).perform(click())

        onView(withId(R.id.drawer_layout)).check(matches(isDisplayed()))
    }

}