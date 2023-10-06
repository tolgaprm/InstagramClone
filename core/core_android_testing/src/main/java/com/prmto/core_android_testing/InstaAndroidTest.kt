package com.prmto.core_android_testing

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import dagger.hilt.android.testing.HiltAndroidRule
import org.junit.Before
import org.junit.Rule

abstract class InstaAndroidTest {

    @get:Rule(order = 0)
    val hiltAndroidRule = HiltAndroidRule(this)

    protected lateinit var context: Context

    @Before
    fun setUp() {
        context = ApplicationProvider.getApplicationContext()
        hiltAndroidRule.inject()
    }
}