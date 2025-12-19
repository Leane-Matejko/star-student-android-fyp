package com.example.starstudent.core.view.uiComponents

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

/* UI component for backgrounds.
*/
@Composable
fun Background() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Brush
                .verticalGradient(
                    listOf(
                        MaterialTheme.colorScheme.background,
                        MaterialTheme.colorScheme.secondary)))
            .testTag("background")
    )
}


/* UI component for a medium icon widget.
*/
@Composable
fun mediumIconWidget(
    quanIcon: ImageVector,
    repIcon: ImageVector,
    label: String){
    Box(
        modifier = Modifier
            .size(100.dp)
            .background(
                color = MaterialTheme.colorScheme.primary,
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
                    tint = MaterialTheme.colorScheme.background

                )
                Icon(
                    imageVector = repIcon,
                    contentDescription = "Representation Icon",
                    modifier = Modifier
                        .size(40.dp)
                        .testTag("mediumIconWidgetRepIcon"),
                    tint = MaterialTheme.colorScheme.background

                )
            }

            Text(text = label,
                    modifier = Modifier
                        .size(100.dp)
                        .align(Alignment.BottomCenter)
                        .testTag("mediumIconWidgetText"),
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.background
                )

    }
}


/* UI component for a small progress widget.
*/
@Composable
fun smallProgressWidget(numCompleteTasks: Int, numTasks: Int, label: String){
    Box(
        modifier = Modifier
            .size(100.dp)
            .background(
                color = MaterialTheme.colorScheme.primary,
                shape = RoundedCornerShape(24.dp)
            )
            .testTag("smallProgressWidgetBackground"),
    ){
        CircularProgressIndicator(
            progress = numCompleteTasks / numTasks.toFloat(),
            modifier = Modifier.size(80.dp)
                .align(Alignment.Center)
                .testTag("smallProgressWidgetProgressBar"),
            color = MaterialTheme.colorScheme.background,
            trackColor = MaterialTheme.colorScheme.secondary,
            strokeWidth = 10.dp,
        )

        Column(modifier = Modifier.align(Alignment.Center)) {
            Text(
                text = label,
                color = MaterialTheme.colorScheme.background,
                modifier = Modifier
                    .testTag("smallProgressWidgetNameLabel")
            )

            Text(
                text = "$numCompleteTasks/$numTasks",
                color = MaterialTheme.colorScheme.secondary,
                fontSize = 6.sp,
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .testTag("smallProgressWidgetTaskLabel")
            )

        }

    }

}


/* UI component for the large navigation widget.
*/
@Composable
fun largeNavWidget(repIcon: ImageVector, title: String, description: String){
    Box(
        modifier = Modifier
            .size(70.dp, 25.dp)
            .background(
                color = MaterialTheme.colorScheme.primary,
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
            tint = MaterialTheme.colorScheme.background

        )

        Column(Modifier.align(Alignment.CenterEnd)
            .padding(0.dp, 2.dp, 2.dp, 4
                .dp))
        {
            Text(
                title,
                color = MaterialTheme.colorScheme.background,
                fontSize = 12.sp,
                modifier = Modifier
                    .testTag("largeNavWidgetTitleLabel")
            )

            Text(
                text = description,
                color = MaterialTheme.colorScheme.secondary,
                fontSize = 6.sp,
                modifier = Modifier
                    .testTag("largeNavWidgetDescriptionLabel")
            )
        }

    }
}


/* UI component for avatar window.
*/
@Composable
fun avatarWindow(prompt: String){
    Box(
        modifier = Modifier
            .background(Brush.verticalGradient(listOf(MaterialTheme.colorScheme.background,MaterialTheme.colorScheme.secondary)),
                shape = RoundedCornerShape(15f
                ))
            .width(120f.dp)
            .height(120.dp)
            .padding(0.dp, 2.dp)
            .testTag("avatarWindowBackground")

    ) {
        Text(
            text = prompt,
            color = MaterialTheme.colorScheme.primary,
            fontSize = 12.sp,
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .testTag("avatarWindowLabel")
            )
    }
}


/* UI component for text field.
*/
@Composable
fun textField(header: String, info: String){
    Column(){
        Text(
            text = header,
            color = MaterialTheme.colorScheme.background,
            fontSize = 12.sp,
            modifier = Modifier
                .testTag("textFieldHeaderLabel")
        )
        Box(
            modifier = Modifier
                .background(
                    shape = RoundedCornerShape(12f),
                    color = MaterialTheme.colorScheme.tertiary
                )
                .size(45.dp, 8.dp)
                .padding(2.dp)
                .testTag("textFieldBackground")
        ){
            Text(
                text = info,
                fontSize = 3.sp,
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier
                    .testTag("textFieldInfoLabel")
            )
        }
    }
}

/* UI component for spacers.
*/
@Composable
fun spacer(modifier :Modifier){
    Spacer(modifier
        .height(20.dp))
}



/* UI component for input fields.
*/
@Composable
fun inputField(label: String,
               value: String,
               onValueChange: (String) -> Unit){

    var info by remember { mutableStateOf("") }

    TextField(
        value = value,
        onValueChange = onValueChange,
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


/* UI component for buttons.
*/
@Composable
fun button(label : String, onClick: () -> Unit) {
    Button(
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(
            containerColor = MaterialTheme.colorScheme.primary,
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