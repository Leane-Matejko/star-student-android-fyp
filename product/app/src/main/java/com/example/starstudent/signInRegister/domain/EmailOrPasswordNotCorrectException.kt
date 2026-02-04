package com.example.starstudent.signInRegister.domain

/* Exception throw when the user's password or username is not correct or mismatched.*/
class EmailOrPasswordNotCorrectException()
    : Exception("Your email or password is not correct. Please try again.")