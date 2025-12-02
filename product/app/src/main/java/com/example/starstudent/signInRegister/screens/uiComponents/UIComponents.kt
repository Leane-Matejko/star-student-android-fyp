package com.example.starstudent.signInRegister.screens.uiComponents

import android.R
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import com.example.starstudent.ui.theme.DarkPink
import com.example.starstudent.ui.theme.White01
import com.example.starstudent.ui.theme.Yellow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ModifierLocalBeyondBoundsLayout
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
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
            .testTag("mediumIconWidgetBackground")
    ){
        Row(modifier = Modifier
            .align(Alignment.TopCenter))
        {
                Icon(
                    imageVector = quanIcon,
                    contentDescription = "Quantify Icon",
                    modifier = Modifier
                        .size(40.dp)
                        .testTag("mediumIconWidgetQuanIcon"),
                    tint = White01

                )
                Icon(
                    imageVector = repIcon,
                    contentDescription = "Representation Icon",
                    modifier = Modifier
                        .size(40.dp)
                        .testTag("mediumIconWidgetRepIcon"),
                    tint = White01

                )
            }

            Text(text = label,
                    modifier = Modifier
                        .size(100.dp)
                        .align(Alignment.BottomCenter)
                        .testTag("mediumIconWidgetText"),
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
            )
            .testTag("smallProgressWidgetBackground"),
    ){
        CircularProgressIndicator(
            progress = numCompleteTasks / numTasks.toFloat(),
            modifier = Modifier.size(80.dp)
                .align(Alignment.Center)
                .testTag("smallProgressWidgetProgressBar"),
            color = Color.White,
            trackColor = LightPink,
            strokeWidth = 10.dp,
        )

        Column(modifier = Modifier.align(Alignment.Center)) {
            Text(
                text = label,
                color = White01,
                modifier = Modifier
                    .testTag("smallProgressWidgetNameLabel")
            )

            Text(
                text = "$numCompleteTasks/$numTasks",
                color = LightPink,
                fontSize = 6.sp,
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .testTag("smallProgressWidgetTaskLabel")
            )

        }

    }

}

@Composable
fun largeNavWidget(repIcon: ImageVector, title: String, description: String){
    Box(
        modifier = Modifier
            .size(70.dp, 25.dp)
            .background(
                color = DarkPink,
                shape = RoundedCornerShape(10.dp)
            )
            .padding(2.dp)
            .testTag("largeNavWidgetBackground")
    ){
        Icon(
            imageVector =repIcon,
            contentDescription = "Representation Icon",
            modifier = Modifier
                .size(20.dp)
                .testTag("largeNavWidgetRepIcon"),
            tint = White01

        )

        Column(Modifier.align(Alignment.CenterEnd)
            .padding(0.dp, 2.dp, 2.dp, 4
                .dp))
        {
            Text(
                title,
                color = White01,
                fontSize = 12.sp,
                modifier = Modifier
                    .testTag("largeNavWidgetTitleLabel")
            )

            Text(
                text = description,
                color = LightPink,
                fontSize = 6.sp,
                modifier = Modifier
                    .testTag("largeNavWidgetDescriptionLabel")
            )
        }

    }
}

@Composable
fun avatarWindow(prompt: String){
    Box(
        modifier = Modifier
            .background(Brush.verticalGradient(listOf(White01,Yellow)),
                shape = RoundedCornerShape(15f
                ))
            .width(120f.dp)
            .height(120.dp)
            .padding(0.dp, 2.dp)
            .testTag("avatarWindowBackground")

    ) {
        Text(
            text = prompt,
            color = DarkPink,
            fontSize = 12.sp,
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .testTag("avatarWindowLabel")
            )
    }
}

@Composable
fun textField(header: String, info: String){
    Column(){
        Text(
            text = header,
            color = DarkPink,
            fontSize = 12.sp,
            modifier = Modifier
                .testTag("textFieldHeaderLabel")
        )
        Box(
            modifier = Modifier
                .background(
                    shape = RoundedCornerShape(12f),
                    color = Yellow
                )
                .size(45.dp, 8.dp)
                .padding(2.dp)
                .testTag("textFieldBackground")
        ){
            Text(
                text = info,
                fontSize = 3.sp,
                color = DarkPink,
                modifier = Modifier
                    .testTag("textFieldInfoLabel")
            )
        }
    }
}

@Composable
fun spacer(modifier :Modifier){
    Spacer(modifier
        .height(20.dp))
}

@Composable
fun inputField(label: String){

    var info by remember { mutableStateOf("") }

    TextField(
        value = info,
        onValueChange = { info = it},
        label = {Text(text = label, fontSize = 12.sp)
                },
        singleLine = true,
        shape = RoundedCornerShape(36f),
        textStyle = LocalTextStyle.current.copy(
            textAlign = TextAlign.Left
        ),
        modifier = Modifier
            .fillMaxWidth()
            .testTag("inputField")
    )
}

@Composable
fun button(label : String, onClick: () -> Unit) {
    Button(
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(
            containerColor = MaterialTheme.colorScheme.secondary,
            contentColor = MaterialTheme.colorScheme.onSecondary
        ),
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp)
            .testTag("button")
    ){

        Text(text = label,
            fontWeight = FontWeight.Bold,
            fontSize = 20.sp,
            modifier = Modifier
                .testTag("buttonLabel")
        )

    }
}