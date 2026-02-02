package com.example.starstudent

import com.example.starstudent.signInRegister.domain.Email
import com.example.starstudent.signInRegister.domain.Password
import org.junit.Test
import kotlin.test.BeforeTest
import kotlin.test.assertEquals
import kotlin.test.assertFails
import kotlin.test.assertTrue

class PasswordsTests {

    private lateinit var password : Password

    @BeforeTest
    fun setup(){
        password = Password()
    }

    @Test
    fun doesPasswordExist(){
        password = Password()
    }

    @Test
    fun comparingPasswordsTrue(){
        assertTrue( password.checkPasswordsMatch("testing","testing"))
    }

    @Test
    fun testEmailOrPasswordNotCorrectPracticalException(){
        val exception = assertFails {
            password.checkPasswordsMatch("test","testing")
        }
        assertEquals(
            "Passwords do not match. Please try again.",
            exception.message
        )
    }

}