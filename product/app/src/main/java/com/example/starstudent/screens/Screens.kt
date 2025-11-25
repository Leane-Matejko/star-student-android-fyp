package com.example.starstudent.screens

sealed class Screens(val route: String) {
    object SignInRegisterScreen: Screens("sign_in_register")
    object SignInScreen: Screens("sign_in")
    object AddEmailScreen: Screens("add_email")
}