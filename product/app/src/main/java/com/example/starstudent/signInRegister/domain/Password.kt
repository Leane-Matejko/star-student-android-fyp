package com.example.starstudent.signInRegister.domain

class Password {

    private var password = ""

    fun checkPasswordsMatch(firstPassword: String?, secondPassword:String?): Boolean {
        if (firstPassword.toString() == secondPassword.toString()) {
            return true
        }
        throw PasswordsDoNotMatch()
    }
}