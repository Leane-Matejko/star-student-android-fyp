package com.example.starstudent

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.navigation.compose.rememberNavController
import com.example.starstudent.signInRegister.screens.AddEmailScreen
import com.example.starstudent.signInRegister.screens.CreatePasswordScreen
import org.junit.Rule
import org.junit.Test

class CreatePasswordTests {


    @get:Rule
    var composeTest = createComposeRule()

    @Test
    fun createPasswordScreenExists(){
        composeTest.setContent {
            val navController = rememberNavController()
            CreatePasswordScreen(navController)
        }
    }

    @Test
    fun createPasswordScreenBackgroundExists(){
        composeTest.setContent {
            val navController = rememberNavController()
            CreatePasswordScreen(navController)
        }

        composeTest.waitForIdle()
        composeTest
            .onNodeWithTag("background")
            .assertIsDisplayed()
    }

    @Test
    fun createPasswordScreenSetPasswordLabelExists(){
        composeTest.setContent {
            val navController = rememberNavController()
            CreatePasswordScreen(navController)
        }

        composeTest.waitForIdle()
        composeTest
            .onNodeWithTag("setPasswordLabel")
            .assertIsDisplayed()
    }

    @Test
    fun createPasswordScreenWelcomeLabelExists(){
        composeTest.setContent {
            val navController = rememberNavController()
            CreatePasswordScreen(navController)
        }

        composeTest.waitForIdle()
        composeTest
            .onNodeWithTag("welcomeLabel")
            .assertIsDisplayed()
    }

    @Test
    fun createPasswordScreenInputFieldOneExists(){
        composeTest.setContent {
            val navController = rememberNavController()
            CreatePasswordScreen(navController)
        }

        composeTest.waitForIdle()
        composeTest
            .onNodeWithTag("inputField1")
            .assertIsDisplayed()
    }

    @Test
    fun createPasswordScreenInputFieldTwoExists(){
        composeTest.setContent {
            val navController = rememberNavController()
            CreatePasswordScreen(navController)
        }

        composeTest.waitForIdle()
        composeTest
            .onNodeWithTag("inputField2")
            .assertIsDisplayed()
    }

    @Test
    fun completeRenderCreatePasswordScreen(){
        composeTest.setContent {
            val navController = rememberNavController()
            CreatePasswordScreen(navController)
        }

        composeTest.waitForIdle()
        composeTest
            .onNodeWithTag("background")
            .assertIsDisplayed()
        composeTest
            .onNodeWithTag("setPasswordLabel")
            .assertIsDisplayed()
        composeTest
            .onNodeWithTag("welcomeLabel")
            .assertIsDisplayed()
        composeTest
            .onNodeWithTag("inputField2")
            .assertIsDisplayed()
        composeTest
            .onNodeWithTag("inputField2")
            .assertIsDisplayed()
    }


}