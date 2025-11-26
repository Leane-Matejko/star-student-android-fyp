package com.example.starstudent.signInRegister.domain

class Email {

    private var email: String

    constructor(providedEmail: String){ email = providedEmail }

    //Further conditions for real emails can be implemented
    fun checkRealEmail() : Boolean{
        if (containsAtSymbol()){
            return true
        }
        throw NotRealEmailAddress()
    }

    fun containsAtSymbol() : Boolean{
        return email.contains('@')
    }

    fun getEmail() : String{
        return email
    }
}