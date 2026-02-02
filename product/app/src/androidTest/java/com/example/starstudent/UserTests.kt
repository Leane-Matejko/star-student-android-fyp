package com.example.starstudent

import androidx.compose.ui.test.junit4.createAndroidComposeRule
import com.example.starstudent.core.domain.CurrentApplication
import com.example.starstudent.core.domain.MainActivity
import com.example.starstudent.core.domain.User
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test

class UserTests {

    @get:Rule
    var composeTest = createAndroidComposeRule<MainActivity>()

    @Test
    fun userExists(){
        User()
    }

    @Test
    fun setUserEmailExists(){
        val user = User()
        user.setEmail("testing@gmail.com")
    }

    @Test
    fun setUserEmail(){
        val user = User()
        user.setEmail("testing@gmail.com")
        assertEquals("testing@gmail.com", user.email.getEmail())
    }

    @Test
    fun setUserHashMap(){
        CurrentApplication.instance.setUser("testing@gmail.com")

        val user = User()
        val testHash =
            hashMapOf(
                "username" to "testing@gmail.com",
                "password" to "test")

        val result = user.createUserHashMap("test")

        assertEquals(testHash, result)
    }
}