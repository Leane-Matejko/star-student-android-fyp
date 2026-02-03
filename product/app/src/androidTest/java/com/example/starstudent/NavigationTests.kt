package com.example.starstudent

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import com.example.starstudent.core.domain.navigation.Navigation
import com.example.starstudent.core.domain.navigation.Screens
import com.example.starstudent.ui.theme.StarStudentTheme
import kotlinx.coroutines.delay
import kotlinx.coroutines.test.runTest
import okhttp3.internal.wait
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test

class NavigationTests {

    @get:Rule
    var composeTest = createComposeRule()

    @Test
    fun navigationExists() {
        composeTest.setContent {
            Navigation()
        }
    }

    @Test
    fun signInRegisterScreenRouteExists(){
        composeTest.setContent {
            Screens.SignInRegisterScreen
        }
    }

    @Test
    fun signInRegisterScreenRouteValue(){
        lateinit var route : String
        composeTest.setContent {
            route = Screens.SignInRegisterScreen.route
        }

        assertEquals(route, "sign_in_register")
    }

    @Test
    fun signInScreenRouteExists(){
        composeTest.setContent {
            Screens.SignInScreen
        }
    }

    @Test
    fun signInScreenRouteValue(){
        lateinit var route : String
        composeTest.setContent {
            route = Screens.SignInScreen.route
        }

        assertEquals(route, "sign_in")
    }

    @Test
    fun addEmailScreenRouteExists(){
        composeTest.setContent {
            Screens.AddEmailScreen
        }
    }

    @Test
    fun addEmailScreenRouteValue(){
        lateinit var route : String
        composeTest.setContent {
            route = Screens.AddEmailScreen.route
        }

        assertEquals(route, "add_email")
    }

    @Test
    fun homepageScreenRouteExists(){
        composeTest.setContent {
            Screens.HomePageScreen
        }
    }

    @Test
    fun homepageScreenRouteValue(){
        lateinit var route : String
        composeTest.setContent {
            route = Screens.HomePageScreen.route
        }

        assertEquals(route, "home_page")
    }

    @Test
    fun createPasswordsScreenRouteExists(){
        composeTest.setContent {
            Screens.CreatePasswordsScreen
        }
    }

    @Test
    fun createPasswordsScreenRouteValue(){
        lateinit var route : String
        composeTest.setContent {
            route = Screens.CreatePasswordsScreen.route
        }

        assertEquals(route, "create_passwords")
    }

    @Test
    fun startDestinationCorrect(){
        composeTest.setContent{
            Navigation()
        }

        composeTest
            .onNodeWithTag("SignInRegisterScreen")
            .assertExists()
    }

    @Test
    fun navigateToSignInCorrect(){
        composeTest.setContent{
            Navigation()
        }

        composeTest.onNodeWithTag("button1")
            .performClick()

        composeTest
            .onNodeWithTag("SignInScreen")
            .assertExists()
    }

    @Test
    fun navigateSignInToHomepageCorrect(){
        composeTest.setContent{
            Navigation()
        }

        composeTest.onNodeWithTag("button1")
            .performClick()
        composeTest
            .onNodeWithTag("SignInScreen")
            .assertExists()

        composeTest.onNodeWithTag("inputField1")
            .performTextInput("lm@gmail.com")
        composeTest.onNodeWithTag("inputField2")
            .performTextInput("test")

        composeTest.onNodeWithTag("button1")
            .performClick()


        composeTest
            .onNodeWithTag("HomepageScreen")
    }

    @Test
    fun navigateSignInRegisterToAddEmailCorrect(){
        composeTest.setContent{
            Navigation()
        }

        composeTest.onNodeWithTag("button2")
            .performClick()
        composeTest
            .onNodeWithTag("AddEmailScreen")
            .assertExists()
    }


//Revisit this test -> changes in the coroutine structure

//    @Test
//    fun navigateSignInRegisterToCreatePasswordsCorrect() = runTest{
//        composeTest.setContent{
//            StarStudentTheme() {
//                Navigation()
//            }
//        }
//
//        composeTest
//            .onNodeWithTag("SignInRegisterScreen")
//            .assertExists()
//
//        composeTest.onNodeWithTag("button2").performClick()
//        composeTest
//            .onNodeWithTag("AddEmailScreen")
//            .assertExists()
//
//        composeTest.onNodeWithTag("inputField1")
//            .performTextInput("testing@gmail.com")
//
//        composeTest.waitForIdle()
//
//        composeTest.onNodeWithTag("button1")
//            .performClick()
//
//        composeTest.waitForIdle()
//        composeTest
//            .onNodeWithTag("CreatePasswordsScreen")
//            .assertExists()
//    }

//    @Test
//    fun navigateRegisterToHomepageCorrect(){
//        composeTest.setContent {
//            Navigation()
//        }
//
//        composeTest
//            .onNodeWithTag("SignInRegisterScreen")
//            .assertExists()
//
//        composeTest.onNodeWithTag("button2")
//            .performClick()
//        composeTest.waitForIdle()
//        composeTest
//            .onNodeWithTag("AddEmailScreen")
//            .assertExists()
//
//        composeTest.onNodeWithTag("inputField1")
//            .performTextInput("test@gmail.com")
//        composeTest.onNodeWithTag("button1")
//            .performClick()
//        composeTest.waitForIdle()
//        composeTest
//            .onNodeWithTag("CreatePasswordsScreen")
//            .assertExists()
//
//        composeTest.onNodeWithTag("inputField1")
//            .performTextInput("testing")
//        composeTest.onNodeWithTag("inputField2")
//            .performTextInput("testing")
//
//        composeTest.onNodeWithTag("button1")
//            .performClick()
//        composeTest.waitForIdle()
//
//        composeTest
//            .onNodeWithTag("HomepageScreen")
//            .assertExists()
//
//    }
}