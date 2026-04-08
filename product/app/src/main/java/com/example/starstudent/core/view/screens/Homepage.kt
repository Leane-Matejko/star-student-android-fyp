package com.example.starstudent.core.view.screens

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Create
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ModifierLocalBeyondBoundsLayout
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.starstudent.core.view.uiComponents.Background
import com.example.starstudent.core.view.uiComponents.BannerFormat
import com.example.starstudent.core.view.uiComponents.avatarWindow
import com.example.starstudent.core.view.uiComponents.button
import com.example.starstudent.core.view.uiComponents.largeNavWidget
import com.example.starstudent.core.view.uiComponents.mediumIconWidget
import com.example.starstudent.core.view.uiComponents.smallProgressWidget
import com.example.starstudent.core.view.uiComponents.spacer
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

/* Responsible for populating the homepage screen.
*/
@Composable
fun Homepage(navController: NavController){

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
                HomepageContent(viewModel, navController)
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
fun HomepageContent(viewModel: HomepageViewModel, navController : NavController) {

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
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(280.dp),
            horizontalArrangement = Arrangement.Start,

            ) {
            avatarWindow(
                viewModel.username,
                modifier = Modifier
                    .weight(1f)
            )

            Spacer(modifier = Modifier.width(20.dp))
            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(5.dp)

            ) {

                mediumIconWidget(
                    Icons.Filled.Star,
                    Icons.Filled.Star,
                    "Study",
                    onClick = {

                    },
                    modifier = Modifier
                        .weight(1f)
                )
                mediumIconWidget(
                    Icons.Filled.Notifications,
                    Icons.Filled.Star,
                    "Sleep",
                    onClick = {

                    },
                    modifier = Modifier
                        .weight(1f)
                )
            }
        }

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
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        )
        {
            smallProgressWidget(
                9,
                12,
                "Overdue",
                12,
                Modifier.weight(1f)
            )
            smallProgressWidget(
                8,
                12,
                "Weekly",
                12,
                Modifier.weight(1f)
            )

            smallProgressWidget(
                7,
                12,
                "All",
                12,
                Modifier.weight(1f))
        }

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
            largeNavWidget(
                Icons.Filled.Star,
                "Study Centre",
                "Study Spaces and Sessions",
                Modifier.fillMaxWidth()
            ) {
                viewModel.navStudyCentre(navController)
            }

            largeNavWidget(
                Icons.Filled.DateRange,
                "Planner",
                "Planner and Task Management",
                Modifier.fillMaxWidth()
            ) {
                viewModel.navPlanner(navController)
            }

            largeNavWidget(
                Icons.Filled.Menu,
                "Task List",
                "Categorised Tasks",
                Modifier.fillMaxWidth()
            ) {
                viewModel.navTaskList(navController)
            }

            largeNavWidget(
                Icons.Filled.Search,
                "History",
                "Session History Management",
                Modifier.fillMaxWidth()
            ) {
                viewModel.navHistory(navController)
            }

            largeNavWidget(
                Icons.Filled.Face,
                "Profile Settings",
                "Personal app settings",
                Modifier.fillMaxWidth()
            ) {
                viewModel.profileNav(navController)
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Row(
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .clickable(onClick = {

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
}