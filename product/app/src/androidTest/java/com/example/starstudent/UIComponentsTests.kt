package com.example.starstudent

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import com.example.starstudent.signInRegister.screens.uiComponents.Background
import com.example.starstudent.signInRegister.screens.uiComponents.avatarWindow
import com.example.starstudent.signInRegister.screens.uiComponents.mediumIconWidget
import org.junit.Rule
import org.junit.Test

//@RunWith(AndroidJUnit4::class)
class UIComponentsTests {

    @get:Rule
    val composeTest = createComposeRule()

    @Test
    fun testBackgroundRender(){
        composeTest.setContent {
            Background()
        }

        composeTest
            .onNodeWithTag("background")
            .assertIsDisplayed()
    }

    @Test
    fun testMediumIconWidgetBackgroundRender(){
        composeTest.setContent {
            mediumIconWidget(Icons.Filled.Star,
                Icons.Filled.Star,
                "Test")
        }

        composeTest
            .onNodeWithTag("mediumIconWidgetBackground")
            .assertIsDisplayed()
    }

    @Test
    fun testMediumIconWidgetQuanIconRender(){
        composeTest.setContent {
            mediumIconWidget(Icons.Filled.Star,
                Icons.Filled.Star,
                "Test")
        }

        composeTest
            .onNodeWithTag("mediumIconWidgetQuanIcon")
            .assertIsDisplayed()
    }

    @Test
    fun testMediumIconWidgetRepIconRender(){
        composeTest.setContent {
            mediumIconWidget(Icons.Filled.Star,
                Icons.Filled.Star,
                "Test")
        }

        composeTest
            .onNodeWithTag("mediumIconWidgetRepIcon")
            .assertIsDisplayed()
    }

    @Test
    fun testMediumIconWidgetTextRender(){
        composeTest.setContent {
            mediumIconWidget(Icons.Filled.Star,
                Icons.Filled.Star,
                "Test")
        }

        composeTest
            .onNodeWithTag("mediumIconWidgetText")
            .assertIsDisplayed()
    }

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