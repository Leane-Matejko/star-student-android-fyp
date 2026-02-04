package com.example.starstudent.signInRegister.screens

import androidx.compose.foundation.gestures.snapping.SnapPosition
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.starstudent.core.view.uiComponents.Background
import com.example.starstudent.core.view.uiComponents.avatarWindow
import com.example.starstudent.core.view.uiComponents.largeNavWidget
import com.example.starstudent.core.view.uiComponents.mediumIconWidget
import com.example.starstudent.core.view.uiComponents.smallProgressWidget
import com.example.starstudent.core.view.uiComponents.spacer


/* Responsible for populating the homepage screen.
*/
@Preview
@Composable
fun Homepage(){
    Background()
    Spacer(modifier = Modifier
        .testTag("HomepageScreen"))
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.Companion.CenterHorizontally,
        modifier = Modifier.Companion
            .fillMaxWidth()
            .fillMaxHeight()
            .padding(30.dp)
    ) {
        Text(
            text = "Home",
            fontSize = 30.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.primary
        )

        spacer(modifier = Modifier.width(10.dp))
        Row (
            modifier = Modifier
                .fillMaxWidth()
                .height(280.dp),
            horizontalArrangement =  Arrangement.Start,

        ){
            avatarWindow("Placeholder Text",
                modifier = Modifier
                    .weight(1f))
            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(5.dp)

            ) {

                mediumIconWidget(
                    Icons.Filled.Star,
                    Icons.Filled.Star,
                    "Sleep",
                    modifier = Modifier
                        .weight(1f))
                mediumIconWidget(
                    Icons.Filled.Star,
                    Icons.Filled.Star,
                    "Sleep",
                    modifier = Modifier
                        .weight(1f))
            }
        }

        spacer(Modifier.height(20.dp))

        Row(Modifier
            .fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(10.dp))
        {
            smallProgressWidget(9, 10, "Tasks", Modifier.weight(1f))
            smallProgressWidget(8, 10, "Tasks", Modifier.weight(1f))
            smallProgressWidget(7, 10, "Tasks", Modifier.weight(1f))
        }

        spacer(Modifier.height(20.dp))

        Column (Modifier
            .fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(10.dp))
        {
            largeNavWidget(Icons.Filled.Star, "Study Centre", "Testing", Modifier.weight(1f))
            largeNavWidget(Icons.Filled.Star, "Study Centre", "Testing" , Modifier.weight(1f))
            largeNavWidget(Icons.Filled.Star, "Study Centre", "Testing" , Modifier.weight(1f))
            largeNavWidget(Icons.Filled.Star, "Study Centre", "Testing" , Modifier.weight(1f))
            largeNavWidget(Icons.Filled.Star, "Study Centre", "Testing" , Modifier.weight(1f))
        }

    }
}