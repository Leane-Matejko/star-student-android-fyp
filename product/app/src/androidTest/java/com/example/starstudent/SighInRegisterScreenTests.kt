package com.example.starstudent

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.navigation.compose.rememberNavController
import com.example.starstudent.signInRegister.screens.SignInRegisterScreen
import org.junit.Rule
import org.junit.Test

class SighInRegisterScreenTests {

    @get:Rule
    var composeTest = createComposeRule()

    @Test
    fun signInRegisterScreenExists(){
        composeTest.setContent {
            val navController = rememberNavController()
            SignInRegisterScreen(navController)
        }
    }

    @Test
    fun signInRegisterScreenBackgroundExists(){
        composeTest.setContent {
            val navController = rememberNavController()
            SignInRegisterScreen(navController)
        }

        composeTest.waitForIdle()
        composeTest
            .onNodeWithTag("background")
            .assertIsDisplayed()
    }

    @Test
    fun signInRegisterScreenSighInLabelExists(){
        composeTest.setContent {
            val navController = rememberNavController()
            SignInRegisterScreen(navController)
        }

        composeTest.waitForIdle()
        composeTest
            .onNodeWithTag("signInLabel")
            .assertIsDisplayed()
    }

    @Test
    fun signInRegisterScreenButtonOneExists(){
        composeTest.setContent {
            val navController = rememberNavController()
            SignInRegisterScreen(navController)
        }

        composeTest.waitForIdle()
        composeTest
            .onNodeWithTag("button1")
            .assertIsDisplayed()
    }

    @Test
    fun signInRegisterScreenButtonTwoExists(){
        composeTest.setContent {
            val navController = rememberNavController()
            SignInRegisterScreen(navController)
        }

        composeTest.waitForIdle()
        composeTest
            .onNodeWithTag("button2")
            .assertIsDisplayed()
    }

    @Test
    fun completeRenderSignInRegisterScreen(){
        composeTest.setContent {
            val navController = rememberNavController()
            SignInRegisterScreen(navController)
        }

        composeTest.waitForIdle()
        composeTest
            .onNodeWithTag("background")
            .assertIsDisplayed()

        composeTest
            .onNodeWithTag("signInLabel")
            .assertIsDisplayed()

        composeTest
            .onNodeWithTag("button1")
            .assertIsDisplayed()

        composeTest
            .onNodeWithTag("button2")
            .assertIsDisplayed()
    }
}