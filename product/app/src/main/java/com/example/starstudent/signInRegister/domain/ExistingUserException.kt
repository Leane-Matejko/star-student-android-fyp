package com.example.starstudent.signInRegister.domain

/* Exception thrown when the user email already exists within the database.*/
class ExistingUserException
    : Exception("This email has already been used. Please try again.")