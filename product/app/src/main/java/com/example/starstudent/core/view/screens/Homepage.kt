package com.example.starstudent.core.view.screens

import android.util.Log
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
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Create
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.starstudent.core.view.uiComponents.Background
import com.example.starstudent.core.view.uiComponents.BannerFormat
import com.example.starstudent.core.view.uiComponents.avatarWindow
import com.example.starstudent.core.view.uiComponents.largeNavWidget
import com.example.starstudent.core.view.uiComponents.mediumIconWidget
import com.example.starstudent.core.view.uiComponents.simpleToggle
import com.example.starstudent.core.view.uiComponents.smallProgressWidget
import com.example.starstudent.core.view.uiComponents.spacer
import com.example.starstudent.core.view.uiComponents.toggle
import com.example.starstudent.ui.theme.LocalExtendedLabelColours
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.text.replaceFirstChar

/* Responsible for populating the homepage screen.
*/
@Composable
fun Homepage(
    navController: NavController,
    applicationViewModel: ApplicationViewModel
){

    val viewModel = viewModel<HomepageViewModel>()

    viewModel.getUser()

    Background()
    BannerFormat(
        username = viewModel.username,
        date = viewModel.curDate,
        profileOnClick = { viewModel.profileNav(navController) },
        list = viewModel.getNavigationMenu(navController),
        showNav = viewModel.showNavMenu,
        navOnClick = {viewModel.showNavMenu()},
        onDismissNav = {viewModel.dismissNavMenu()}
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .padding(padding)
        ) {
            item{
                HomepageContent(
                    viewModel,
                    navController,
                    applicationViewModel)
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
fun HomepageContent(
    viewModel: HomepageViewModel,
    navController : NavController,
    applicationViewModel : ApplicationViewModel) {

    Spacer(
        modifier = Modifier
            .testTag("HomepageScreen")
    )
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxWidth()
            .padding(30.dp)
    ) {
        Text(
            text = "Home",
            fontSize = 30.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.primary
        )

        spacer(modifier = Modifier.width(10.dp))

        if(viewModel.homepageSettings.avatarWindow ||
            viewModel.homepageSettings.studyProgress ||
            viewModel.homepageSettings.sleepProgress
            ) {

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(280.dp),
                horizontalArrangement = Arrangement.SpaceEvenly,

                ) {

                if(viewModel.homepageSettings.avatarWindow) {
                    avatarWindow(
                        viewModel.username,
                        modifier = Modifier
                            .weight(1f)
                    )
                }

                if(viewModel.homepageSettings.studyProgress ||
                    viewModel.homepageSettings.sleepProgress) {

//                    Spacer(modifier = Modifier.width(20.dp))
                    Column(
                        modifier = Modifier.weight(1f),
                        verticalArrangement = Arrangement.spacedBy(5.dp),
                        horizontalAlignment = Alignment.CenterHorizontally

                    ) {
                        if (viewModel.homepageSettings.studyProgress) {
                            mediumIconWidget(
                                Icons.Filled.Star,
                                viewModel.studyComparisonIcon,
                                "Study",
                                modifier = Modifier
                                    .weight(1f)
                            )
                        }

                        if (viewModel.homepageSettings.sleepProgress) {
                            mediumIconWidget(
                                Icons.Filled.Notifications,
                                Icons.Filled.KeyboardArrowUp,
                                "Sleep",
                                modifier = Modifier
                                    .weight(1f)
                            )
                        }
                    }
                }
            }
        }

        if(viewModel.homepageSettings.overdueTasks ||
            viewModel.homepageSettings.weeklyTasks ||
            viewModel.homepageSettings.allTasks) {

            spacer(Modifier.height(20.dp))


            Text(
                text = "Tasks",
                fontSize = 16.sp,
                fontWeight = FontWeight.SemiBold,
                color = MaterialTheme.colorScheme.primary
            )

            Spacer(modifier = Modifier.height(10.dp))

            Row(
                Modifier
                    .fillMaxWidth()
                    .heightIn(max = 100.dp),
                horizontalArrangement = Arrangement.SpaceEvenly
            )
            {
                if(viewModel.homepageSettings.overdueTasks) {
                    smallProgressWidget(
                        viewModel.overdueToDo,
                        viewModel.overdueTaskList.size,
                        "Overdue",
                        12,
                        {
                            viewModel.showTasksDialog("overdue")
                        },
                        Modifier
//                            .weight(1f)
                    )
                }

                if (viewModel.homepageSettings.weeklyTasks) {
                    smallProgressWidget(
                        viewModel.weekToDo,
                        viewModel.weekTaskList.size,
                        "Weekly",
                        12,
                        {
                            viewModel.showTasksDialog("weekly")
                        },
                        Modifier
//                            .weight(1f)
                    )
                }

                if (viewModel.homepageSettings.allTasks) {
                    smallProgressWidget(
                        viewModel.allToDo,
                        viewModel.allTaskList.size,
                        "All",
                        12,
                        {
                            viewModel.showTasksDialog("all")
                        },
                        Modifier
//                            .weight(1f)
                    )
                }
            }
        }

        if(viewModel.homepageSettings.studyCentreNav ||
            viewModel.homepageSettings.plannerNav ||
            viewModel.homepageSettings.taskListNav ||
            viewModel.homepageSettings.historyNav ||
            viewModel.homepageSettings.profileNav) {

            spacer(Modifier.height(20.dp))

            Text(
                text = "Navigation",
                fontSize = 16.sp,
                fontWeight = FontWeight.SemiBold,
                color = MaterialTheme.colorScheme.primary
            )

            Spacer(modifier = Modifier.height(10.dp))

            Column(
                verticalArrangement = Arrangement.spacedBy(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                if(viewModel.homepageSettings.studyCentreNav) {
                    largeNavWidget(
                        Icons.Filled.Star,
                        "Study Centre",
                        "Study Spaces and Sessions",
                        Modifier.fillMaxWidth()
                    ) {
                        viewModel.navStudyCentre(navController)
                    }
                }

                if (viewModel.homepageSettings.plannerNav) {
                    largeNavWidget(
                        Icons.Filled.DateRange,
                        "Planner",
                        "Planner and Task Management",
                        Modifier.fillMaxWidth()
                    ) {
                        viewModel.navPlanner(navController)
                    }
                }

                if (viewModel.homepageSettings.taskListNav) {
                    largeNavWidget(
                        Icons.Filled.Menu,
                        "Task List",
                        "Categorised Tasks",
                        Modifier.fillMaxWidth()
                    ) {
                        viewModel.navTaskList(navController)
                    }
                }

                if (viewModel.homepageSettings.historyNav) {
                    largeNavWidget(
                        Icons.Filled.Search,
                        "History",
                        "Session History Management",
                        Modifier.fillMaxWidth()
                    ) {
                        viewModel.navHistory(navController)
                    }
                }

                if(viewModel.homepageSettings.profileNav) {
                    largeNavWidget(
                        Icons.Filled.Face,
                        "Profile Settings",
                        "Personal app settings",
                        Modifier.fillMaxWidth()
                    ) {
                        viewModel.profileNav(navController)
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Row(
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .clickable(onClick = {
                    viewModel.showHomepageCustomisationDialog()
                })
        ) {
            Icon(
                Icons.Filled.Create,
                contentDescription = "CustomiseHomepage",
                modifier = Modifier,
                tint = MaterialTheme.colorScheme.primary
            )

            Spacer(modifier = Modifier.width(6.dp))

            Text(
                text = "Customise Homepage",
                fontSize = 16.sp,
                fontWeight = FontWeight.SemiBold,
                color = MaterialTheme.colorScheme.primary
            )
        }
    }

    LaunchedEffect(Unit) {
        if(viewModel.checkIsMonday()){
            viewModel.updateTasksList()
        }
        viewModel.getAllCurrentTasks()
        viewModel.getSessionList()
        viewModel.updateTheme(applicationViewModel)
        viewModel.getHomepageSettings()
    }

    if (viewModel.showTasksDialog){
        Dialog(onDismissRequest = {
            viewModel.hideTasksDialog()
        }) {
            TasksDialog(viewModel)
        }
    }

    if (viewModel.showHomepageCustomisationDialog){
        Dialog(onDismissRequest = {
            viewModel.hideHomepageCustomisationDialog()
        }) {
            HomepageCustomisationDialog(viewModel)
        }
    }
}

@Composable
fun TasksDialog(viewModel: HomepageViewModel){
    val colors = LocalExtendedLabelColours.current

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp)
    ) {

        val scrollState = rememberScrollState()

        Column(
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxWidth()
                .verticalScroll(scrollState)
                .padding(20.dp, 20.dp)
        ) {
            Text(
                text = "${viewModel.tasksType.replaceFirstChar { it.uppercase() }} Tasks",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                color = MaterialTheme.colorScheme.secondary,
                modifier = Modifier
                    .fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(10.dp))

            if(viewModel.currentTaskList.isEmpty()){

                Row(
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    Icon(
                        Icons.Filled.Done,
                        contentDescription = "CompleteTasks",
                        modifier = Modifier,
                        tint = MaterialTheme.colorScheme.primary
                    )

                    Text(
                        text = "Done",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = MaterialTheme.colorScheme.primary
                    )
                }
            }else{

                val today = System.currentTimeMillis()

                viewModel.currentTaskList.forEach { task ->

                    val isOverdue = !((task.dueDate <= today) && (!task.isComplete))

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(
                                color = if (isOverdue) {MaterialTheme.colorScheme.primary}
                                else {MaterialTheme.colorScheme.tertiary},
                                shape = RoundedCornerShape(24.dp)
                            )
                            .padding(6.dp)
                        ,
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween

                    ) {
                        simpleToggle(
                            0.6f,
                            task.isComplete,
                        ) {
                            viewModel.viewModelScope.launch {
                                viewModel.updateTaskCompletion(
                                    task.id,
                                    task.isComplete
                                )
                            }
                        }

                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            modifier = Modifier
                                .weight(1f)

                        ) {

                            Box(
                                contentAlignment = Alignment.Center,
                                modifier = Modifier
                                    .background(
                                        color = viewModel.getCategoryColour(
                                            task.taskCategoryLabelColour,
                                            colors
                                        ),
                                        shape = RoundedCornerShape(24.dp),
                                    )
                                    .padding(start = 4.dp, end = 4.dp, top = 2.dp, bottom = 2.dp)
                                    .width(200.dp)
                            ) {
                                Text(
                                    text = task.taskCategoryLabel,
                                    color = MaterialTheme.colorScheme.surface,
                                    fontWeight = FontWeight.SemiBold,
                                    textAlign = TextAlign.Center,
                                    fontSize = 8.sp
                                )

                            }

                            Text(
                                text = task.taskLabel,
                                color = if (isOverdue) {
                                    MaterialTheme.colorScheme.background
                                } else {
                                    MaterialTheme.colorScheme.primary
                                },
                                fontWeight = FontWeight.SemiBold,
                                fontSize = 14.sp,
                                modifier = Modifier
                                    .padding(2.dp)
                            )

                            Text(
                                text = "Due: ${
                                    viewModel.formatDateTime(
                                        task.dueDate,
                                        "EEE d MMM yy"
                                    )
                                }",
                                color = if (isOverdue) {
                                    MaterialTheme.colorScheme.background
                                } else {
                                    MaterialTheme.colorScheme.primary
                                },
                                fontSize = 12.sp,
                                modifier = Modifier
                            )
                        }

                        if (task.isCritical) {
                            Spacer(modifier = Modifier.width(10.dp))

                            Icon(
                                imageVector = Icons.Filled.Star,
                                contentDescription = "Critical Task",
                                tint = if (isOverdue) {
                                    MaterialTheme.colorScheme.background
                                } else {
                                    MaterialTheme.colorScheme.primary
                                },
                                modifier = Modifier
                                    .size(20.dp)
                                    .weight(0.2f)
                            )
                        } else {
                            Icon(
                                imageVector = Icons.Filled.Star,
                                contentDescription = "Critical Task",
                                tint = if (isOverdue) {
                                    MaterialTheme.colorScheme.primary
                                } else {
                                    MaterialTheme.colorScheme.tertiary
                                },
                                modifier = Modifier
                                    .size(20.dp)
                                    .weight(0.2f)
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun HomepageCustomisationDialog(viewModel: HomepageViewModel) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp)
    ) {

        val scrollState = rememberScrollState()

        Column(
            verticalArrangement = Arrangement.spacedBy(10.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxWidth()
                .verticalScroll(scrollState)
                .padding(30.dp, 20.dp)
        ) {

            Text(
                text = "Homepage Customisation",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                color = MaterialTheme.colorScheme.secondary,
                modifier = Modifier
                    .fillMaxWidth()
            )

            Row(
                horizontalArrangement = Arrangement.spacedBy(10.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                simpleToggle(
                    0.7f,
                    viewModel.homepageSettings.avatarWindow
                ) {
                    viewModel.viewModelScope.launch {
                        viewModel.updateAvatarWindow()
                    }
                }

                Text(
                    text = "Avatar Window",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Start,
                    color = MaterialTheme.colorScheme.primary,
                    modifier = Modifier
                        .fillMaxWidth()
                )

            }

            Row(
                horizontalArrangement = Arrangement.spacedBy(10.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                simpleToggle(
                    0.7f,
                    viewModel.homepageSettings.studyProgress
                ) {
                    viewModel.viewModelScope.launch {
                        viewModel.updateStudyProgress()
                    }
                }

                Text(
                    text = "Study Progress Widget",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Start,
                    color = MaterialTheme.colorScheme.primary,
                    modifier = Modifier
                        .fillMaxWidth()
                )

            }

            Row(
                horizontalArrangement = Arrangement.spacedBy(10.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                simpleToggle(
                    0.7f,
                    viewModel.homepageSettings.sleepProgress
                ) {
                    viewModel.viewModelScope.launch {
                        viewModel.updateSleepProgress()
                    }
                }

                Text(
                    text = "Sleep Progress Widget",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Start,
                    color = MaterialTheme.colorScheme.primary,
                    modifier = Modifier
                        .fillMaxWidth()
                )

            }

            Row(
                horizontalArrangement = Arrangement.spacedBy(10.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                simpleToggle(
                    0.7f,
                    viewModel.homepageSettings.overdueTasks
                ) {
                    viewModel.viewModelScope.launch {
                        viewModel.updateOverdueTasks()
                    }
                }

                Text(
                    text = "Overdue Tasks",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Start,
                    color = MaterialTheme.colorScheme.primary,
                    modifier = Modifier
                        .fillMaxWidth()
                )

            }

            Row(
                horizontalArrangement = Arrangement.spacedBy(10.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                simpleToggle(
                    0.7f,
                    viewModel.homepageSettings.weeklyTasks
                ) {
                    viewModel.viewModelScope.launch {
                        viewModel.updateWeeklyTasks()
                    }
                }

                Text(
                    text = "Weekly Tasks",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Start,
                    color = MaterialTheme.colorScheme.primary,
                    modifier = Modifier
                        .fillMaxWidth()
                )

            }

            Row(
                horizontalArrangement = Arrangement.spacedBy(10.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                simpleToggle(
                    0.7f,
                    viewModel.homepageSettings.allTasks
                ) {
                    viewModel.viewModelScope.launch {
                        viewModel.updateAllTasks()
                    }
                }

                Text(
                    text = "All Tasks",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Start,
                    color = MaterialTheme.colorScheme.primary,
                    modifier = Modifier
                        .fillMaxWidth()
                )

            }

            Row(
                horizontalArrangement = Arrangement.spacedBy(10.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                simpleToggle(
                    0.7f,
                    viewModel.homepageSettings.studyCentreNav
                ) {
                    viewModel.viewModelScope.launch {
                        viewModel.updateStudyCentreNav()
                    }
                }

                Text(
                    text = "Study Centre Navigation",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Start,
                    color = MaterialTheme.colorScheme.primary,
                    modifier = Modifier
                        .fillMaxWidth()
                )

            }

            Row(
                horizontalArrangement = Arrangement.spacedBy(10.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                simpleToggle(
                    0.7f,
                    viewModel.homepageSettings.plannerNav
                ) {
                    viewModel.viewModelScope.launch {
                        viewModel.updatePlannerNav()
                    }
                }

                Text(
                    text = "Planner Navigation",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Start,
                    color = MaterialTheme.colorScheme.primary,
                    modifier = Modifier
                        .fillMaxWidth()
                )

            }

            Row(
                horizontalArrangement = Arrangement.spacedBy(10.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                simpleToggle(
                    0.7f,
                    viewModel.homepageSettings.taskListNav
                ) {
                    viewModel.viewModelScope.launch {
                        viewModel.updateTaskListNav()
                    }
                }

                Text(
                    text = "Task List Navigation",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Start,
                    color = MaterialTheme.colorScheme.primary,
                    modifier = Modifier
                        .fillMaxWidth()
                )

            }
            Row(
                horizontalArrangement = Arrangement.spacedBy(10.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                simpleToggle(
                    0.7f,
                    viewModel.homepageSettings.historyNav
                ) {
                    viewModel.viewModelScope.launch {
                        viewModel.updateHistoryNav()
                    }
                }

                Text(
                    text = "History Navigation",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Start,
                    color = MaterialTheme.colorScheme.primary,
                    modifier = Modifier
                        .fillMaxWidth()
                )

            }
            Row(
                horizontalArrangement = Arrangement.spacedBy(10.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                simpleToggle(
                    0.7f,
                    viewModel.homepageSettings.profileNav
                ) {
                    viewModel.viewModelScope.launch {
                        viewModel.updateProfileNav()
                    }
                }

                Text(
                    text = "Profile Navigation",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Start,
                    color = MaterialTheme.colorScheme.primary,
                    modifier = Modifier
                        .fillMaxWidth()
                )
            }
        }
    }
}