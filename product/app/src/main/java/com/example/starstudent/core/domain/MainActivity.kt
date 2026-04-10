package com.example.starstudent.core.domain

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.starstudent.core.domain.location.LocationPermissions
import com.example.starstudent.core.domain.navigation.Navigation
import com.example.starstudent.core.view.screens.ApplicationViewModel
import com.example.starstudent.ui.theme.StarStudentTheme


class MainActivity : ComponentActivity() {

    val locationPermissions = LocationPermissions(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
         setContent{
             val applicationViewModel: ApplicationViewModel = viewModel()

             //Setting the theme and allow for changes later on
             StarStudentTheme(
                theme = applicationViewModel.themeMode
             ) {
                 Navigation(applicationViewModel)
             }
        }
        enableEdgeToEdge()

        //Checks if the user has allowed the system location permission to the app
        if(!locationPermissions.hasLocationPermission()){
            locationPermissions.requestLocationPermission()
        }
    }
}

