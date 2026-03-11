package com.example.starstudent.core.view.uiComponents

import android.R
import android.widget.Switch
import android.widget.ToggleButton
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SwitchColors
import androidx.compose.material3.SwitchDefaults
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ModifierLocalBeyondBoundsLayout
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.example.starstudent.R as myAppRes

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

@Composable
fun mediumIconWidget(
    quanIcon: ImageVector,
    repIcon: ImageVector,
    label: String,
    modifier: Modifier)
    {
        Column(
            modifier = modifier
                .aspectRatio(1f)
                .background(
                    color = MaterialTheme.colorScheme.primary,
                    shape = RoundedCornerShape(24.dp)
                )
                .padding(horizontal = 20.dp, vertical = 36.dp)
                .testTag("mediumIconWidgetBackground"),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Row(
                horizontalArrangement = Arrangement.Center
            )
            {
                Icon(
                    imageVector = quanIcon,
                    contentDescription = "Quantify Icon",
                    modifier = Modifier
                        .size(36.dp)
                        .testTag("mediumIconWidgetQuanIcon"),
                    tint = MaterialTheme.colorScheme.background

                )

                Spacer(modifier = Modifier.width(5.dp))

                Icon(
                    imageVector = repIcon,
                    contentDescription = "Representation Icon",
                    modifier = Modifier
                        .size(36.dp)
                        .testTag("mediumIconWidgetRepIcon"),
                    tint = MaterialTheme.colorScheme.background

                )
            }

            Spacer(modifier = Modifier.height(5.dp))

            Text(text = label,
                    modifier = Modifier
                        .size(100.dp)
                        .testTag("mediumIconWidgetText"),
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.background
            )
        }
}

/* UI component for a small progress widget.
*/
@Composable
fun smallProgressWidget(numCompleteTasks: Int, numTasks: Int, label: String, modifier: Modifier = Modifier){
    Box(
        modifier = modifier
            .aspectRatio(1f)
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
            trackColor = MaterialTheme.colorScheme.tertiary,
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
                color = MaterialTheme.colorScheme.onTertiary,
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
fun largeNavWidget(repIcon: ImageVector, title: String, description: String, modifier: Modifier = Modifier){
    Row(
        modifier = modifier
            .fillMaxWidth()
            .background(
                color = MaterialTheme.colorScheme.primary,
                shape = RoundedCornerShape(24.dp)
            )
            .height(100.dp)
            .padding(20.dp)
            .testTag("largeNavWidgetBackground"),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Icon(
            imageVector =repIcon,
            contentDescription = "Representation Icon",
            modifier = Modifier
                .size(44.dp)
                .testTag("largeNavWidgetRepIcon"),
            tint = MaterialTheme.colorScheme.background

        )

        Spacer(modifier = Modifier.width(20.dp))

        Column(
            modifier = Modifier.weight(1f)
        ) {
            Text(
                text = title,
                color = MaterialTheme.colorScheme.background,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold ,
                modifier = Modifier
                    .testTag("largeNavWidgetTitleLabel")
            )

            Text(
                text = description,
                color = MaterialTheme.colorScheme.secondary,
                fontSize = 14.sp,
                modifier = Modifier
                    .testTag("largeNavWidgetDescriptionLabel")
            )
        }
    }
}


/* UI component for avatar window.
*/
@Composable
fun avatarWindow(
    prompt: String,
    modifier: Modifier = Modifier){
    Box(
        modifier = modifier
            .background(Brush.verticalGradient(listOf(MaterialTheme.colorScheme.background,MaterialTheme.colorScheme.secondary)),
                shape = RoundedCornerShape(15f
                ))
//            .fillMaxWidth(0.70f)
//            .fillMaxHeight(1f)
            .height(300.dp)
            .padding(5.dp, 2.dp)
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
fun textField(header: String, info: String, height: Int){
    Column(){
        Text(
            text = header,
            color = MaterialTheme.colorScheme.primary,
            fontSize = 20.sp,
            fontWeight = FontWeight.SemiBold,
            modifier = Modifier
                .testTag("textFieldHeaderLabel")
        )

        Spacer(modifier = Modifier.height(6.dp))

        Box(
            modifier = Modifier
                .background(
                    shape = RoundedCornerShape(36f),
                    color = MaterialTheme.colorScheme.background
                )
                .fillMaxWidth()
                .height(height.dp)
//                .size(45.dp, 8.dp)
                .padding(4.dp)
                .testTag("textFieldBackground")
        ){

//            Spacer(modifier = Modifier.width(10.dp))
            Text(
                text = info,
                fontSize = 16.sp,
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier
                    .testTag("textFieldInfoLabel")
                    .padding(10.dp)
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
               seqNumber: Int?,
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
            .testTag("inputField$seqNumber")
    )
}


/* UI component for buttons.
*/
@Composable
fun button(label : String, seqNumber: Int?, onClick: () -> Unit) {
    Button(
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(
            containerColor = MaterialTheme.colorScheme.primary,
            contentColor = MaterialTheme.colorScheme.onSecondary
        ),
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp)
            .testTag("button$seqNumber")
    ){

        Text(text = label,
            fontWeight = FontWeight.Bold,
            fontSize = 20.sp,
            modifier = Modifier
                .testTag("buttonLabel$seqNumber")
        )

    }
}

@Composable
fun TopBanner(
        username : String,
        date: String,
        profileOnClick: () -> Unit
    ){
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.primary)
            .padding(top = 40.dp, start = 20.dp, end = 20.dp, bottom = 16.dp)
            .testTag("topBanner"),

        verticalAlignment = Alignment.Bottom,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            IconButton(onClick = {}) {
                Icon(
                    modifier = Modifier
                        .testTag("topBannerMenuIcon"),
                    imageVector = Icons.Default.Menu,
                    contentDescription = "Menu",
                    tint = MaterialTheme.colorScheme.onPrimary)
            }

            Spacer(modifier = Modifier.width(10.dp))

            Column() {
                Text(
                    modifier = Modifier
                        .testTag("topBannerTextUsername"),
                    text = ("Hello, $username"),
                    color = MaterialTheme.colorScheme.onPrimary,
                    fontSize = 16.sp
                )
                Text(
                    modifier = Modifier
                        .testTag("topBannerTextDate"),
                    text = date,
                    color = MaterialTheme.colorScheme.onSecondary,
                    fontSize = 12.sp
                )
            }
        }

        IconButton(onClick = {profileOnClick()})
        {
            Image(
                painter = painterResource(myAppRes.drawable.profile_picture),
                contentDescription = "Profile Picture",
                modifier = Modifier
                    .size(40.dp)
                    .clip(CircleShape)
                    .testTag("topBannerProfilePicture")
            )
        }

    }
}

@Composable
fun BannerFormat(
    username : String,
    date: String,
    profileOnClick : () -> Unit,
    content: @Composable (PaddingValues) -> Unit
){
    Scaffold(
        topBar = {
            TopBanner(
                username,
                date,
                profileOnClick
            )
        }
    ) {
       padding -> content(padding)
    }
}

@Composable
fun toggle(
    header : String,
    isChecked : Boolean
){
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .testTag("toggle")
    ) {

        Text(
            text = header,
            color = MaterialTheme.colorScheme.primary,
            fontSize = 20.sp,
            fontWeight = FontWeight.SemiBold,
            modifier = Modifier
                .testTag("textFieldHeaderLabel")
        )

        Spacer(modifier = Modifier.height(6.dp))

        androidx.compose.material3.Switch(
            checked = isChecked,
            onCheckedChange = {},
            colors = SwitchDefaults.colors(
                checkedThumbColor = MaterialTheme.colorScheme.background,
                checkedTrackColor = MaterialTheme.colorScheme.primary,
                uncheckedThumbColor = MaterialTheme.colorScheme.background,
                uncheckedTrackColor = MaterialTheme.colorScheme.secondary

            )
        )
    }
}