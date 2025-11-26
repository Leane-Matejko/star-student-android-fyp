package com.example.starstudent.navigation

sealed class Screens(val route: String) {
    object SignInRegisterScreen: Screens("sign_in_register")
    object SignInScreen: Screens("sign_in")
    object AddEmailScreen: Screens("add_email")
    object VerifyEmailScreen: Screens("verify_email")
    object HomeScreen: Screens("home_page")
    object CreatePasswordsScreen: Screens("create_password")

    fun withArgs(vararg args: String) : String {
        return buildString {
            append(route)
            args.forEach { arg ->
                append("/$arg")
            }
        }
    }
}