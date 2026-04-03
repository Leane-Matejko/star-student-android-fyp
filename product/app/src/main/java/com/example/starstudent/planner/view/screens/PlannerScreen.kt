package com.example.starstudent.planner.view.screens

import android.util.Log
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
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.starstudent.core.domain.navigation.NavigationOptions
import com.example.starstudent.core.view.uiComponents.BannerFormat
import com.example.starstudent.core.view.uiComponents.CalendarGrid
import com.example.starstudent.core.view.uiComponents.avatarWindow
import com.example.starstudent.core.view.uiComponents.button
import com.example.starstudent.core.view.uiComponents.largeNavWidget
import com.example.starstudent.ui.theme.LocalExtendedLabelColours
import java.util.Calendar
import java.util.Date

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
}


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

        val month = Calendar.SEPTEMBER

        Log.d("MONTH", month.toString())

        val dates = remember {
            generateMonthDates(2026, month)
        }

        val selectedDates = remember { mutableStateOf(setOf<Date>()) }

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
                dates = dates.map { (date, _) ->
                    date to (date in selectedDates.value)
                },
                onClick = { clickedDate ->
                    selectedDates.value =
                        if (clickedDate in selectedDates.value) {
                            selectedDates.value - clickedDate
                        } else {
                            selectedDates.value + clickedDate
                        }
                }
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
            Icons.Filled.Star,
            "Today",
            "Placeholder",
            Modifier.fillMaxWidth()
        ){ }

        largeNavWidget(
            Icons.Filled.Star,
            "This Week",
            "Placeholder",
            Modifier.fillMaxWidth()
        ){ }

        largeNavWidget(
            Icons.Filled.Star,
            "This Month",
            "Placeholder",
            Modifier.fillMaxWidth()
        ){ }

    }

}

fun generateMonthDates(year: Int, month: Int): List<Pair<Date, Boolean>> {
    val calendar = Calendar.getInstance()
    calendar.set(year, month, 1)

    val daysInMonth = calendar.getActualMaximum(Calendar.DAY_OF_MONTH)

    val dates = mutableListOf<Pair<Date, Boolean>>()

    for (day in 1..daysInMonth) {
        calendar.set(year, month, day)

        dates.add(
            Pair(
                calendar.time,
                true
            )
        )
    }

    return dates
}