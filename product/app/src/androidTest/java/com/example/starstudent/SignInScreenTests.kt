package com.example.starstudent

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.navigation.compose.rememberNavController
import com.example.starstudent.signInRegister.screens.SignInScreen
import org.junit.Rule
import org.junit.Test

class SignInScreenTests {
    @get:Rule
    var composeTest = createComposeRule()

    @Test
    fun signInScreenExists(){
        composeTest.setContent {
            val navController = rememberNavController()
            SignInScreen(navController)
        }
    }

    @Test
    fun signInScreenBackgroundExists(){
        composeTest.setContent {
            val navController = rememberNavController()
            SignInScreen(navController)
        }

        composeTest.waitForIdle()
        composeTest
            .onNodeWithTag("background")
            .assertIsDisplayed()
    }

    @Test
    fun signInScreenSignInLabelExists(){
        composeTest.setContent {
            val navController = rememberNavController()
            SignInScreen(navController)
        }

        composeTest.waitForIdle()
        composeTest
            .onNodeWithTag("signInLabel")
            .assertIsDisplayed()
    }

    @Test
    fun signInScreenInputFieldOneExists(){
        composeTest.setContent {
            val navController = rememberNavController()
            SignInScreen(navController)
        }

        composeTest.waitForIdle()
        composeTest
            .onNodeWithTag("inputField1")
            .assertIsDisplayed()
    }

    @Test
    fun signInScreenInputFieldTwoExists(){
        composeTest.setContent {
            val navController = rememberNavController()
            SignInScreen(navController)
        }

        composeTest.waitForIdle()
        composeTest
            .onNodeWithTag("inputField2")
            .assertIsDisplayed()
    }

    @Test
    fun signInScreenButtonOneExists(){
        composeTest.setContent {
            val navController = rememberNavController()
            SignInScreen(navController)
        }

        composeTest.waitForIdle()
        composeTest
            .onNodeWithTag("button1")
            .assertIsDisplayed()
    }

    @Test
    fun completeRenderSignInScreen(){
        composeTest.setContent {
            val navController = rememberNavController()
            SignInScreen(navController)
        }

        composeTest.waitForIdle()
        composeTest
            .onNodeWithTag("background")
            .assertIsDisplayed()
        composeTest
            .onNodeWithTag("signInLabel")
            .assertIsDisplayed()
        composeTest
            .onNodeWithTag("inputField1")
            .assertIsDisplayed()
        composeTest
            .onNodeWithTag("inputField2")
            .assertIsDisplayed()
        composeTest
            .onNodeWithTag("button1")
            .assertIsDisplayed()
    }
}