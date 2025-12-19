package com.example.starstudent.signInRegister.domain

import java.lang.Exception

/* Exception throw when the passwords entered do not match*/
class PasswordsDoNotMatch
    : Exception("Passwords do not match. Please try again.")