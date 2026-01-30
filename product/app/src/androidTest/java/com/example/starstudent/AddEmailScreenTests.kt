package com.example.starstudent

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.navigation.compose.rememberNavController
import com.example.starstudent.core.view.uiComponents.Background
import com.example.starstudent.signInRegister.screens.AddEmailScreen
import org.junit.Rule
import org.junit.Test

class AddEmailScreenTests {
    @get:Rule
    var composeTest = createComposeRule()

    @Test
    fun addEmailScreenExists(){
        composeTest.setContent {
            val navController = rememberNavController()
            AddEmailScreen(navController)
        }
    }

    @Test
    fun addEmailScreenBackgroundExists(){
        composeTest.setContent {
            val navController = rememberNavController()
            AddEmailScreen(navController)
        }

        composeTest.waitForIdle()
        composeTest
            .onNodeWithTag("background")
            .assertIsDisplayed()
    }

    @Test
    fun addEmailScreenEmailLabelExists(){
        composeTest.setContent {
            val navController = rememberNavController()
            AddEmailScreen(navController)
        }

        composeTest.waitForIdle()
        composeTest
            .onNodeWithTag("emailLabel")
            .assertIsDisplayed()
    }

    @Test
    fun addEmailScreenInputFieldExists(){
        composeTest.setContent {
            val navController = rememberNavController()
            AddEmailScreen(navController)
        }

        composeTest.waitForIdle()
        composeTest
            .onNodeWithTag("inputField")
            .assertIsDisplayed()
    }

    @Test
    fun addEmailScreenButtonExists(){
        composeTest.setContent {
            val navController = rememberNavController()
            AddEmailScreen(navController)
        }

        composeTest.waitForIdle()
        composeTest
            .onNodeWithTag("button")
            .assertIsDisplayed()
    }
}