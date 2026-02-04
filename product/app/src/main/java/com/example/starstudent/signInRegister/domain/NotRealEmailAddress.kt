package com.example.starstudent.signInRegister.domain

/* Exception throw when a false or fake email address has been entered*/
class NotRealEmailAddress()
    : Exception("This is not a real email address. Please try again.")