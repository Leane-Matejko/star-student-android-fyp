package com.example.starstudent.studySpaces.view.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.starstudent.core.view.uiComponents.BannerFormat
import com.example.starstudent.core.view.uiComponents.button
import com.example.starstudent.core.view.uiComponents.largeNavWidget
import com.example.starstudent.core.view.uiComponents.smallAvatarWindow
import com.example.starstudent.core.view.uiComponents.smallProgressWidget
import com.example.starstudent.core.view.uiComponents.textField
import com.example.starstudent.core.view.uiComponents.varLargeNavWidget
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun StudyCentreScreen(navController: NavController){

    val viewModel = viewModel<StudyCentreViewModel>()

    BannerFormat(
        username = viewModel.username,
        date = viewModel.curDate,
        profileOnClick = {
            viewModel.profileNav(navController)
                         },
        list = viewModel.getNavigationMenu(navController),
        showNav = viewModel.showNavMenu,
        navOnClick = {
            viewModel.showNavMenu()
                     },
        onDismissNav = {
            viewModel.dismissNavMenu()
        }
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .padding(padding)
        ) {
            item{
                StudyCentreContent(viewModel)
            }
        }

    }

    viewModel.viewModelScope.launch{

        viewModel.updateTime()
        delay(60000 - System.currentTimeMillis() % 60000)

        while(true){
            viewModel.updateTime()
            delay(60000)
        }
    }
}

@Composable
fun StudyCentreContent(viewModel: StudyCentreViewModel){
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.Companion.CenterHorizontally,
        modifier = Modifier.Companion
            .fillMaxWidth()
            .padding(30.dp)
    ){

        Text(
            text = "Study Centre",
            fontSize = 30.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.primary
        )

        Spacer(modifier = Modifier.height(10.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp)
                .height(120.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {

            varLargeNavWidget(
                repIcon = Icons.Filled.Star,
                title = "Study Space Detector",
                titleSize = 16,
                description = "Not Detected",
                modifier = Modifier
            ) { }
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(viewModel.studySessionLength.dp),
            horizontalArrangement = Arrangement.Start
        ) {

            Column(
                modifier = Modifier
                    .background(
                        color = MaterialTheme.colorScheme.secondary,
                        shape = RoundedCornerShape(24.dp)
                    )
                    .padding(all = 20.dp)
                ,
                verticalArrangement =  Arrangement.spacedBy(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ){

                Text(
                    text = "Study Session",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Left,
                    color = MaterialTheme.colorScheme.primary
                )

                smallAvatarWindow(
                    "Leane",
                    modifier = Modifier,
                    viewModel.studyingStatus
                )

                largeNavWidget(
                    Icons.Filled.Star,
                    "Session Goals",
                    "Placeholder",
                    Modifier.fillMaxWidth()
                ){ }

                if(viewModel.showPauseButton()){
                    button(
                        viewModel.formatPauseSessionButton(),
                        2
                    ){viewModel.viewModelScope.launch {  viewModel.updatePauseSession()}}
                }

                button(
                    viewModel.formatSessionButton(),
                    1
                ) { viewModel.viewModelScope.launch {viewModel.updateSessionStatus()}}
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        Text(
            text = "Tasks",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Left,
            color = MaterialTheme.colorScheme.primary
        )

        Spacer(modifier = Modifier.height(20.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(100.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {

            smallProgressWidget(
                12,
                16,
                "All Tasks",
                12
            )

            smallProgressWidget(
                12,
                16,
                "This Week",
                10
            )

            smallProgressWidget(
                12,
                16,
                "Overdue",
                12
            )

        }
        
        Spacer(modifier = Modifier.height(20.dp))

        Column (
            modifier = Modifier
                .background(
                    color = MaterialTheme.colorScheme.primary,
                    shape = RoundedCornerShape(24.dp)
                )
                .padding(all = 20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ){

            Text(
                text = "Recent Study History",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Left,
                color = MaterialTheme.colorScheme.onPrimary
            )

            Spacer(modifier = Modifier.height(10.dp))

            viewModel.viewModelScope.launch {  viewModel.getRecentStudySessions()}

            viewModel.recentStudySessions.forEach { option ->

                viewModel.viewModelScope.launch {
                    val pausedSessions = viewModel.getPausedSessions(option.id)
                    viewModel.getTotalPausedTime(pausedSessions)}

                textField(
                    viewModel.getLongToDate(option.startTime),
                    viewModel.calculateTotalStudyTime(
                        sessionId = option.id,
                        startTime = option.startTime,
                        endTime = option.endTime
                    ),
                    50
                )
            }

            viewModel.viewModelScope.launch {
                while(viewModel.sessionStatus && !viewModel.isSessionPause){
                    viewModel.updateTimer()
                    viewModel.updateStudyingStatus()
                    delay(1000)
                }
            }
        }
    }
}