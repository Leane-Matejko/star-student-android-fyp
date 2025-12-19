package com.example.starstudent

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.Modifier
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertTextEquals
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import com.example.starstudent.core.domain.MainActivity
import com.example.starstudent.core.domain.TestActivity
import com.example.starstudent.core.view.uiComponents.Background
import com.example.starstudent.core.view.uiComponents.avatarWindow
import com.example.starstudent.core.view.uiComponents.button
import com.example.starstudent.core.view.uiComponents.inputField
import com.example.starstudent.core.view.uiComponents.largeNavWidget
import com.example.starstudent.core.view.uiComponents.mediumIconWidget
import com.example.starstudent.core.view.uiComponents.smallProgressWidget
import com.example.starstudent.core.view.uiComponents.spacer
import com.example.starstudent.core.view.uiComponents.textField
import org.junit.Rule
import org.junit.Test


class UIComponentsTests {

    @get:Rule
    var composeTest = createComposeRule()

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


        composeTest.waitForIdle()
        composeTest
            .onNodeWithTag("background")
            .assertIsDisplayed()
    }

    //Testing the render of the medium icon widget component
    @Test
    fun testMediumIconWidgetExists(){
        composeTest.setContent {
                mediumIconWidget(
                    Icons.Filled.Star,
                    Icons.Filled.Star,
                    "Test"
                )

        }
    }

    @Test
    fun testMediumIconWidgetBackgroundRender(){
        composeTest.setContent {
                mediumIconWidget(
                    Icons.Filled.Star,
                    Icons.Filled.Star,
                    "Test"
                )

        }

        composeTest.waitForIdle()
        composeTest
            .onNodeWithTag("mediumIconWidgetBackground")
            .assertIsDisplayed()
    }

    @Test
    fun testMediumIconWidgetQuanIconRender(){
        composeTest.setContent {
                mediumIconWidget(
                    Icons.Filled.Star,
                    Icons.Filled.Star,
                    "Test"
                )

        }

        composeTest.waitForIdle()
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

        composeTest.waitForIdle()
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

        composeTest.waitForIdle()
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

        composeTest.waitForIdle()
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

        composeTest.waitForIdle()
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

        composeTest.waitForIdle()
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

        composeTest.waitForIdle()
        composeTest
            .onNodeWithTag("smallProgressWidgetTaskLabel")
            .assertIsDisplayed()
    }

    //Testing the render of the large navigation widget
    @Test
    fun testLargeNavWidgetExists(){
        composeTest.setContent {
            largeNavWidget(Icons.Filled.Star,
                "Test",
                "Test")
        }
    }

    @Test
    fun testLargeNavWidgetRepIconRender(){
        composeTest.setContent {
            largeNavWidget(Icons.Filled.Star,
                "Test",
                "Test")
        }

        composeTest.waitForIdle()
        composeTest
            .onNodeWithTag("largeNavWidgetRepIcon")
            .assertIsDisplayed()
    }

    @Test
    fun testLargeNavWidgetTitleLabelRender(){
        composeTest.setContent {
            largeNavWidget(Icons.Filled.Star,
                "Test",
                "Test")
        }

        composeTest.waitForIdle()
        composeTest
            .onNodeWithTag("largeNavWidgetTitleLabel")
            .assertIsDisplayed()
    }

    @Test
    fun testLargeNavWidgetDescriptionLabelRender(){


        composeTest.setContent {
            largeNavWidget(Icons.Filled.Star,
                "Test",
                "Test")
        }

        composeTest.waitForIdle()
        composeTest
            .onNodeWithTag("largeNavWidgetDescriptionLabel")
            .assertIsDisplayed()
    }

    //Testing the render of the avatar window component
    @Test
    fun testAvatarWindowExists(){
        composeTest.setContent {
            avatarWindow("")
        }
    }

    @Test
    fun testAvatarWindowBackgroundRender(){
        composeTest.setContent {
            avatarWindow("")
        }

        composeTest.waitForIdle()
        composeTest
            .onNodeWithTag("avatarWindowBackground")
            .assertIsDisplayed()
    }

    @Test
    fun testAvatarWindowLabelRender(){
        composeTest.setContent {
            avatarWindow("Test")
        }

        composeTest.waitForIdle()
        composeTest
            .onNodeWithTag("avatarWindowLabel")
            .assertIsDisplayed()
    }

    //Testing the render of the text field component
    @Test
    fun testTextFieldExists(){
        composeTest.setContent {
            textField("","")
        }
    }

    @Test
    fun testTextFieldHeaderLabelRender(){
        composeTest.setContent {
            textField("Test","Test")
        }

        composeTest.waitForIdle()
        composeTest
            .onNodeWithTag("textFieldHeaderLabel")
            .assertIsDisplayed()
    }

    @Test
    fun testTextFieldBackgroundRender(){
        composeTest.setContent {
            textField("","")
        }

        composeTest.waitForIdle()
        composeTest
            .onNodeWithTag("textFieldBackground")
            .assertIsDisplayed()
    }

    @Test
    fun testTextFieldInfoLabelRender(){
        composeTest.setContent {
            textField("Test","Test")
        }

        composeTest.waitForIdle()
        composeTest
            .onNodeWithTag("textFieldInfoLabel")
            .assertIsDisplayed()
    }

    //Testing the render of the text field
    @Test
    fun testSpacerExists(){
        composeTest.setContent {
            spacer(modifier = Modifier)
        }
    }

    //Testing the render of the input field component
    @Test
    fun testInputFieldExists(){
        composeTest.setContent {
            inputField("",
                "",
                {})
        }
    }

    @Test
    fun testInputFieldRender(){
        composeTest.setContent {
            inputField("",
                "",
                {})
        }

        composeTest.waitForIdle()
        composeTest
            .onNodeWithTag("inputField")
            .assertIsDisplayed()
    }

    //Testing the render of the button component
    @Test
    fun testButtonExists(){
        composeTest.setContent {
            button("test") {}
        }
    }

    @Test
    fun testButtonRender(){
        composeTest.setContent {
            button("test") {}
        }

        composeTest.waitForIdle()
        composeTest
            .onNodeWithTag("button")
            .assertIsDisplayed()
    }

    @Test
    fun testButtonLabelRender(){
        composeTest.setContent {
            button("test") {}
        }

        composeTest.waitForIdle()
        composeTest
            .onNodeWithTag("buttonLabel", useUnmergedTree = true)
            .assertTextEquals("test")
    }

}