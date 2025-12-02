package com.example.starstudent

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import com.example.starstudent.signInRegister.screens.uiComponents.Background
import com.example.starstudent.signInRegister.screens.uiComponents.avatarWindow
import com.example.starstudent.signInRegister.screens.uiComponents.mediumIconWidget
import com.example.starstudent.signInRegister.screens.uiComponents.smallProgressWidget
import org.junit.Rule
import org.junit.Test

//@RunWith(AndroidJUnit4::class)
class UIComponentsTests {

    @get:Rule
    val composeTest = createComposeRule()

    //Testing the render of the background component
    @Test
    fun testBackgroundExists(){
        composeTest.setContent {
            Background()
        }
    }

    @Test
    fun testBackgroundRender(){
        composeTest.setContent {
            Background()
        }

        composeTest
            .onNodeWithTag("background")
            .assertIsDisplayed()
    }

    //Testing the render of the medium icon widget component
    @Test
    fun testMediumIconWidgetExists(){
        composeTest.setContent {
            mediumIconWidget(Icons.Filled.Star,
                Icons.Filled.Star,
                "Test")
        }
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

    //Testing the render of the small progress widget component
    @Test
    fun testSmallProgressWidgetExists(){
        composeTest.setContent {
            smallProgressWidget(6,
                10,
                "Test")
        }
    }

    @Test
    fun testSmallProgressWidgetBackgroundRender(){
        composeTest.setContent {
            smallProgressWidget(6,
                10,
                "Test")
        }

        composeTest
            .onNodeWithTag("smallProgressWidgetBackground")
            .assertIsDisplayed()
    }

    @Test
    fun testSmallProgressWidgetProgressBarRender(){
        composeTest.setContent {
            smallProgressWidget(6,
                10,
                "Test")
        }

        composeTest
            .onNodeWithTag("smallProgressWidgetProgressBar")
            .assertIsDisplayed()
    }

    @Test
    fun testSmallProgressWidgetNameLabelRender(){
        composeTest.setContent {
            smallProgressWidget(6,
                10,
                "Test")
        }

        composeTest
            .onNodeWithTag("smallProgressWidgetNameLabel")
            .assertIsDisplayed()
    }

    @Test
    fun testSmallProgressWidgetNameTaskRender(){
        composeTest.setContent {
            smallProgressWidget(6,
                10,
                "Test")
        }

        composeTest
            .onNodeWithTag("smallProgressWidgetTaskLabel")
            .assertIsDisplayed()
    }

    //Testing the render of the avatar window component
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