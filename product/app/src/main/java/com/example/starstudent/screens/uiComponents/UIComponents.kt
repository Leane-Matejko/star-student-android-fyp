package com.example.starstudent.screens.uiComponents

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import com.example.starstudent.ui.theme.DarkPink
import com.example.starstudent.ui.theme.White01
import com.example.starstudent.ui.theme.Yellow
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.starstudent.ui.theme.LightPink

@Composable
fun Background() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Brush.verticalGradient(listOf(White01, Yellow)))
            .testTag("background")
    )
}

@Composable
fun mediumIconWidget(quanIcon: ImageVector, repIcon: ImageVector, label: String){
    Box(
        modifier = Modifier
            .size(100.dp)
            .background(
                color = DarkPink,
                shape = RoundedCornerShape(24.dp)
            )
            .padding(horizontal = 10.dp, vertical = 8.dp)
    ){
        Row(modifier = Modifier
            .align(Alignment.TopCenter) )
        {
                Icon(
                    imageVector = quanIcon,
                    contentDescription = "Quantify Icon",
                    modifier = Modifier.size(40.dp),
                    tint = White01

                )
                Icon(
                    imageVector = repIcon,
                    contentDescription = "Representation Icon",
                    modifier = Modifier.size(40.dp),
                    tint = White01

                )
            }

            Text(text = label,
                    modifier = Modifier
                        .size(100.dp)
                        .align(Alignment.BottomCenter),
                    fontWeight = FontWeight.Bold,
                    color = White01
                )

    }
}

@Composable
fun smallProgressWidget(numCompleteTasks: Int, numTasks: Int, label: String){
    Box(
        modifier = Modifier
            .size(100.dp)
            .background(
                color = DarkPink,
                shape = RoundedCornerShape(24.dp)
            ),
    ){
        CircularProgressIndicator(
            progress = Math.floorDiv(numCompleteTasks, numTasks).toFloat(),
            modifier = Modifier.size(80.dp)
                .align(Alignment.Center),
            color = Color.White,
            trackColor = Color.LightGray,
            strokeWidth = 10.dp,
        )

        Column(modifier = Modifier.align(Alignment.Center)) {
            Text(
                text = label,
                color = White01
            )

            Text(
                text = "$numCompleteTasks/$numTasks",
                color = LightPink,
                fontSize = 6.sp,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )

        }

    }

}