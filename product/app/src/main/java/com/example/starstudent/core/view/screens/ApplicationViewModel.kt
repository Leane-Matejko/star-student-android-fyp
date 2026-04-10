package com.example.starstudent.core.view.screens

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.starstudent.core.domain.Themes

//Sets the theme for the app
class ApplicationViewModel : ViewModel() {

    //Default theme is initially system
    var themeMode: Themes by mutableStateOf(
        Themes.SYSTEM
    )
        private set

    //Set the app's theme
    fun setTheme(
        theme : Themes
    ){
        themeMode = theme
    }
}