package com.example.starstudent.planner.view.screens

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Card
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerState
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ModifierLocalBeyondBoundsLayout
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.starstudent.core.domain.navigation.NavigationOptions
import com.example.starstudent.core.view.uiComponents.BannerFormat
import com.example.starstudent.core.view.uiComponents.CalendarGrid
import com.example.starstudent.core.view.uiComponents.avatarWindow
import com.example.starstudent.core.view.uiComponents.button
import com.example.starstudent.core.view.uiComponents.largeNavWidget
import com.example.starstudent.ui.theme.LocalExtendedLabelColours
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.Calendar
import java.util.Date

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun PlannerScreen(navController: NavController){
    
    val viewModel = viewModel<PlannerViewModel>()

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
    ) {
            padding ->
        LazyColumn(
            modifier = Modifier
                .padding(padding)
        ) {
            item{
                PlannerContent(viewModel, navController)
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


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun PlannerContent(viewModel : PlannerViewModel, navController : NavController){

    val viewModel = viewModel

    val colors = LocalExtendedLabelColours.current

    Column(
        verticalArrangement = Arrangement.spacedBy(12.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxWidth()
            .padding(30.dp)
    ){
        Text(
            text = "Planner",
            fontSize = 30.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.primary
        )

        Spacer(modifier = Modifier.height(10.dp))

        val dates = remember(viewModel.calendarYear, viewModel.calendarMonth) {
            viewModel.generateMonthDates(viewModel.calendarYear, viewModel.calendarMonth)
        }

        val selectedDates = remember(dates) { mutableStateOf(setOf<Date>()) }

        Column(
            modifier = Modifier
                .height(400.dp)
                .background(
                    color = MaterialTheme.colorScheme.primary,
                    shape = RoundedCornerShape(24.dp)
                )
                .padding(all = 20.dp)
        ) {
            CalendarGrid(
                dates = dates,
                tasks = viewModel.taskList,
                onClick = { clickedDate ->
                    selectedDates.value =
                        if (clickedDate in selectedDates.value) {
                            selectedDates.value - clickedDate
                        } else {
                            selectedDates.value + clickedDate
                        }
                },
                prevButtonClick = {viewModel.getPreviousMonth()},
                nextButtonClick = {viewModel.getNextMonth()},
                monthButtonClick = {viewModel.showSelectMonthDialog()}
            )
        }

        Column(
            modifier = Modifier
                .height(100.dp)
                .background(
                    color = MaterialTheme.colorScheme.secondary,
                    shape = RoundedCornerShape(24.dp)
                )
                .padding(all = 20.dp)
        ) {
            button("All Tasks",
                1) { viewModel.navTaskList(navController) }
        }

        largeNavWidget(
            Icons.Filled.Warning,
            "Overdue",
            "Placeholder",
            Modifier.fillMaxWidth()
        ){ }

        largeNavWidget(
            Icons.Filled.DateRange,
            "Today",
            "Placeholder",
            Modifier.fillMaxWidth()
        ){ }

        largeNavWidget(
            Icons.Filled.DateRange,
            "This Week",
            "Placeholder",
            Modifier.fillMaxWidth()
        ){ }

        largeNavWidget(
            Icons.Filled.DateRange,
            "This Month",
            "Placeholder",
            Modifier.fillMaxWidth()
        ){ }

    }

    LaunchedEffect(Unit) {
        viewModel.getTaskList()
    }

    if(viewModel.showSelectMonthDialog){
        Dialog(
            onDismissRequest = { viewModel.hideSelectMonthDialog() }
        ){
            SelectMonthDialog(viewModel)
        }
    }
}

@Composable
fun SelectMonthDialog(viewModel: PlannerViewModel){
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(440.dp)
            .padding(10.dp)
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp, 20.dp)
        ) {

            Text(
                text = "Select Month/Year",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                color = MaterialTheme.colorScheme.secondary,
                modifier = Modifier
                    .fillMaxWidth()
            )

            Text(
                text = "Select Year",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier
                    .fillMaxWidth()
            )

            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .background(
                        color = MaterialTheme.colorScheme.primary,
                        shape = RoundedCornerShape(16.dp)
                    )
                    .padding(10.dp)
                    .clickable(onClick = {viewModel.toggleYearList()})
            ) {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = viewModel.calendarYear.toString(),
                        textAlign = TextAlign.Center,
                        fontWeight = FontWeight.SemiBold,
                        color = MaterialTheme.colorScheme.background
                    )

                    Icon(
                        modifier = Modifier
                            .testTag("editLabelColourButton"),
                        imageVector = if (viewModel.showYearList) {
                            Icons.Filled.KeyboardArrowUp
                        } else {
                            Icons.Filled.KeyboardArrowDown
                        },
                        contentDescription = "Menu",
                        tint = MaterialTheme.colorScheme.secondary
                    )
                }


                val scrollState = rememberScrollState()

                DropdownMenu(
                    expanded = viewModel.showYearList,
                    onDismissRequest = { viewModel.toggleYearList() },
                    scrollState = scrollState,
                    modifier = Modifier
                        .heightIn(max = 200.dp)
                ) {
                    viewModel.yearList.forEach { year ->
                        DropdownMenuItem(
                            text = { Text(text = year.toString()) },
                            onClick = {
                                viewModel.setYear(year)
                            }
                        )
                    }
                }
            }

            Text(
                text = "Select Month",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier
                    .fillMaxWidth()
            )

            LazyVerticalGrid(
                columns = GridCells.Fixed(3),
                verticalArrangement = Arrangement.spacedBy(8.dp),
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                modifier = Modifier
                    .padding(4.dp)
            ){
            items(viewModel.monthList.size){
                month ->
                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier
                            .background(
                                color = MaterialTheme.colorScheme.primary,
                                shape = RoundedCornerShape(16.dp)
                            )
                            .padding(4.dp)
                            .clickable(onClick = { viewModel.setMonth(month) })
                    ){
                        Text(
                            text = viewModel.monthList[month],
                            textAlign = TextAlign.Center,
                            fontWeight = FontWeight.SemiBold,
                            color = MaterialTheme.colorScheme.background
                        )
                    }
                }
            }
        }
    }
}