package com.example.starstudent

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.navigation.compose.rememberNavController
import com.example.starstudent.signInRegister.screens.VerifyEmailScreen
import org.junit.Rule
import org.junit.Test

class VerifyEmailScreenTests {

    @get:Rule
    var composeTest = createComposeRule()

    @Test
    fun verifyEmailScreenExists(){
        composeTest.setContent {
            val navController = rememberNavController()
            VerifyEmailScreen(navController)
        }
    }

    @Test
    fun verifyEmailScreenBackgroundExists(){
        composeTest.setContent {
            val navController = rememberNavController()
            VerifyEmailScreen(navController)
        }

        composeTest.waitForIdle()
        composeTest
            .onNodeWithTag("background")
            .assertIsDisplayed()
    }

    @Test
    fun verifyEmailScreenVerifyExists(){
        composeTest.setContent {
            val navController = rememberNavController()
            VerifyEmailScreen(navController)
        }

        composeTest.waitForIdle()
        composeTest
            .onNodeWithTag("verifyLabel")
            .assertIsDisplayed()
    }

    @Test
    fun verifyEmailScreenInputFieldOneExists(){
        composeTest.setContent {
            val navController = rememberNavController()
            VerifyEmailScreen(navController)
        }

        composeTest.waitForIdle()
        composeTest
            .onNodeWithTag("inputField1")
            .assertIsDisplayed()
    }

    @Test
    fun verifyEmailScreenButtonOneExists(){
        composeTest.setContent {
            val navController = rememberNavController()
            VerifyEmailScreen(navController)
        }

        composeTest.waitForIdle()
        composeTest
            .onNodeWithTag("button1")
            .assertIsDisplayed()
    }

    @Test
    fun completeRenderVerifyEmailScreen(){
        composeTest.setContent {
            val navController = rememberNavController()
            VerifyEmailScreen(navController)
        }

        composeTest.waitForIdle()
        composeTest
            .onNodeWithTag("background")
            .assertIsDisplayed()
        composeTest
            .onNodeWithTag("verifyLabel")
            .assertIsDisplayed()
        composeTest
            .onNodeWithTag("inputField1")
            .assertIsDisplayed()
        composeTest
            .onNodeWithTag("button1")
            .assertIsDisplayed()
    }
}