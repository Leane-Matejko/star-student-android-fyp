package com.example.starstudent.core.view.screens

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.starstudent.core.domain.Themes

class ApplicationViewModel : ViewModel() {

    var themeMode: Themes by mutableStateOf(
        Themes.SYSTEM
    )
        private set

    fun getTheme() : Themes{
        return themeMode
    }

    fun setTheme(
        theme : Themes
    ){
        themeMode = theme
    }
}