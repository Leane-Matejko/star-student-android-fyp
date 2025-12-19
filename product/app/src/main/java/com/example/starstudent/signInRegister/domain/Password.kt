package com.example.starstudent.signInRegister.domain

/* Involves functions relating to the user's password and it's basic validation.
*/
class Password {

    private var password = ""

    //Checking if the passwords match.
    fun checkPasswordsMatch(firstPassword: String?, secondPassword:String?): Boolean {
        if (firstPassword.toString() == secondPassword.toString()) {
            return true
        }
        throw PasswordsDoNotMatch()
    }
}