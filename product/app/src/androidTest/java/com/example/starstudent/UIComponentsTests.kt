package com.example.starstudent

import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.Modifier
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertTextEquals
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.unit.dp
import com.example.starstudent.core.domain.MainActivity
import com.example.starstudent.core.domain.TestActivity
import com.example.starstudent.core.domain.navigation.NavigationOptions
import com.example.starstudent.core.view.uiComponents.Background
import com.example.starstudent.core.view.uiComponents.TopBanner
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
                    "Test",
                    modifier = Modifier
                )

        }
    }

    @Test
    fun testMediumIconWidgetBackgroundRender(){
        composeTest.setContent {
                mediumIconWidget(
                    Icons.Filled.Star,
                    Icons.Filled.Star,
                    "Test",
                    modifier = Modifier
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
                    "Test",
                    modifier = Modifier
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
                "Test",
                modifier = Modifier)
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
                "Test",
                modifier = Modifier)
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
                "Test",
                12)
        }
    }

    @Test
    fun testSmallProgressWidgetBackgroundRender(){
        composeTest.setContent {
            smallProgressWidget(6,
                10,
                "Test",
                12)
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
                "Test",
                12)
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
                "Test",
                12)
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
                "Test",
                12)
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
            textField("","", 40)
        }
    }

    @Test
    fun testTextFieldHeaderLabelRender(){
        composeTest.setContent {
            textField("Test","Test",40)
        }

        composeTest.waitForIdle()
        composeTest
            .onNodeWithTag("textFieldHeaderLabel")
            .assertIsDisplayed()
    }

    @Test
    fun testTextFieldBackgroundRender(){
        composeTest.setContent {
            textField("","",40)
        }

        composeTest.waitForIdle()
        composeTest
            .onNodeWithTag("textFieldBackground")
            .assertIsDisplayed()
    }

    @Test
    fun testTextFieldInfoLabelRender(){
        composeTest.setContent {
            textField("Test","Test",40)
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
                1,
                {})
        }
    }

    @Test
    fun testInputFieldRender(){
        composeTest.setContent {
            inputField("",
                "",
                1,
                {})
        }

        composeTest.waitForIdle()
        composeTest
            .onNodeWithTag("inputField1")
            .assertIsDisplayed()
    }

    //Testing the render of the button component
    @Test
    fun testButtonExists(){
        composeTest.setContent {
            button("test",1) {}
        }
    }

    @Test
    fun testButtonRender(){
        composeTest.setContent {
            button("test",1) {}
        }

        composeTest.waitForIdle()
        composeTest
            .onNodeWithTag("button1")
            .assertIsDisplayed()
    }

    @Test
    fun testButtonLabelRender(){
        composeTest.setContent {
            button("test",1) {}
        }

        composeTest.waitForIdle()
        composeTest
            .onNodeWithTag("buttonLabel1", useUnmergedTree = true)
            .assertTextEquals("test")
    }


    @Test
    fun testTopBannerExists(){
        composeTest.setContent {
            TopBanner(
                "testing",
                "Wed 11 Mar",
                listOf(NavigationOptions(""){}),
                showNav = false,
                onDismissNav = {},
                navOnClick = {},
                profileOnClick = {}
            )
        }

        composeTest.waitForIdle()
    }

    @Test
    fun testTopBannerRender(){
        composeTest.setContent {
            TopBanner(
                "testing",
                "Wed 11 Mar",
                listOf(NavigationOptions(""){}),
                showNav = false,
                onDismissNav = {},
                navOnClick = {},
                profileOnClick = {}
            )
        }

        composeTest.waitForIdle()
        composeTest
            .onNodeWithTag("topBanner")
            .assertIsDisplayed()
    }

    @Test
    fun testTopBannerMenuIconRender(){
        composeTest.setContent {
            TopBanner(
                "testing",
                "Wed 11 Mar",
                listOf(NavigationOptions(""){}),
                showNav = false,
                onDismissNav = {},
                navOnClick = {},
                profileOnClick = {}
            )
        }

        composeTest.waitForIdle()
        composeTest
            .onNodeWithTag("topBannerMenuIcon", useUnmergedTree = true)
            .assertIsDisplayed()
    }

    @Test
    fun testTopBannerTextUsernameRender(){
        composeTest.setContent {
            TopBanner(
                "testing",
                "Wed 11 Mar",
                listOf(NavigationOptions(""){}),
                showNav = false,
                onDismissNav = {},
                navOnClick = {},
                profileOnClick = {}
            )
        }

        composeTest.waitForIdle()
        composeTest
            .onNodeWithTag("topBannerTextUsername")
            .assertIsDisplayed()
    }

    @Test
    fun testTopBannerTextDateRender(){
        composeTest.setContent {
            TopBanner(
                "testing",
                "Wed 11 Mar",
                listOf(NavigationOptions(""){}),
                showNav = false,
                onDismissNav = {},
                navOnClick = {},
                profileOnClick = {}
            )
        }

        composeTest.waitForIdle()
        composeTest
            .onNodeWithTag("topBannerTextDate")
            .assertIsDisplayed()
    }

    @Test
    fun testTopBannerProfilePictureRender(){
        composeTest.setContent {
            TopBanner(
                "testing",
                "Wed 11 Mar",
                listOf(NavigationOptions(""){}),
                showNav = false,
                onDismissNav = {},
                navOnClick = {},
                profileOnClick = {}
            )
        }

        composeTest.waitForIdle()
        composeTest
            .onNodeWithTag("topBannerProfilePicture", useUnmergedTree = true)
            .assertIsDisplayed()
    }


}
