package com.example.starstudent.signInRegister.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.starstudent.core.view.uiComponents.Background
import com.example.starstudent.core.view.uiComponents.avatarWindow
import com.example.starstudent.core.view.uiComponents.mediumIconWidget
import com.example.starstudent.core.view.uiComponents.smallProgressWidget
import com.example.starstudent.core.view.uiComponents.spacer

@Preview
@Composable
fun Homepage(){
//fun Homepage(navController: NavController) {
    Background()
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.Companion.CenterHorizontally,
        modifier = Modifier.Companion
            .fillMaxWidth()
            .fillMaxHeight()
            .padding(40.dp)
    ) {
        Text(
            text = "Home",
            fontSize = 30.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.primary
        )
        Row (
            horizontalArrangement =  Arrangement.Start,

        ){
            avatarWindow("Placeholder Text")
            spacer(Modifier.width(5.dp))
            Column() {
                mediumIconWidget(Icons.Filled.Star, Icons.Filled.Star, "Sleep")
                spacer(Modifier.height(20.dp))
                mediumIconWidget(Icons.Filled.Star, Icons.Filled.Star, "Sleep")
            }
        }

        spacer(Modifier.height(20.dp))

        Row(){
            smallProgressWidget(9, 10, "Tasks")
            spacer(Modifier.width(10.dp))
            smallProgressWidget(8, 10, "Tasks")
            spacer(Modifier.width(10.dp))
            smallProgressWidget(7, 10, "Tasks")
        }

    }
}