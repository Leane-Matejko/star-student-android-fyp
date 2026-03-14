package com.example.starstudent.core.view.screens

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
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
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
        viewModel.username,
        date = viewModel.curDate,
        { viewModel.profileNav(navController) }
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .padding(padding)
        ) {
            item{
                HomepageContent(viewModel)
            }
        }

    }

    viewModel.viewModelScope.launch{
        while(true){
            viewModel.updateTime()
            delay(60000)
        }
    }

}

@Composable
fun HomepageContent(viewModel: HomepageViewModel) {

    val viewModel = viewModel

    Spacer(
        modifier = Modifier
            .testTag("HomepageScreen")
    )
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.Companion.CenterHorizontally,
        modifier = Modifier.Companion
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
                "Placeholder Text",
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
                    "Sleep",
                    modifier = Modifier
                        .weight(1f)
                )
                mediumIconWidget(
                    Icons.Filled.Star,
                    Icons.Filled.Star,
                    "Sleep",
                    modifier = Modifier
                        .weight(1f)
                )
            }
        }

        spacer(Modifier.height(20.dp))

        Row(
            Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        )
        {
            smallProgressWidget(9, 10, "Tasks", Modifier.weight(1f))
            smallProgressWidget(8, 10, "Tasks", Modifier.weight(1f))
            smallProgressWidget(7, 10, "Tasks", Modifier.weight(1f))
        }

        spacer(Modifier.height(20.dp))


        largeNavWidget(Icons.Filled.Star, "Study Centre", "Testing", Modifier.fillMaxWidth())
        spacer(Modifier.height(10.dp))
        largeNavWidget(Icons.Filled.Star, "Study Centre", "Testing", Modifier.fillMaxWidth())
        spacer(Modifier.height(10.dp))
        largeNavWidget(Icons.Filled.Star, "Study Centre", "Testing", Modifier.fillMaxWidth())
        spacer(Modifier.height(10.dp))
        largeNavWidget(Icons.Filled.Star, "Study Centre", "Testing", Modifier.fillMaxWidth())
        spacer(Modifier.height(10.dp))
        largeNavWidget(Icons.Filled.Star, "Study Centre", "Testing", Modifier.fillMaxWidth())


    }
}