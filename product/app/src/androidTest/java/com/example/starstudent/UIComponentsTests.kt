package com.example.starstudent

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.starstudent.signInRegister.screens.uiComponents.avatarWindow
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

//@RunWith(AndroidJUnit4::class)
class UIComponentsTests {

    @get:Rule
    val composeTest = createComposeRule()

    @Test
    fun testAvatarWindowRender(){
        composeTest.setContent {
            avatarWindow("")
        }

        composeTest
            .onNodeWithTag("avatarWindow")
            .assertIsDisplayed()


    }
}