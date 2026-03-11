package com.example.starstudent.core.view.screens


import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import com.example.starstudent.core.domain.navigation.Screens
import kotlinx.coroutines.launch

/* ViewModel for the homepage.
*/
class HomepageViewModel : ViewModel(){

    fun profileNav(navController: NavController){
        Log.d("TEST", "Navigating to the profile...")
        navController.navigate(Screens.ProfileInformationScreen.route)
    }

}