package com.example.starstudent

import androidx.compose.ui.test.junit4.createAndroidComposeRule
import kotlin.test.assertFailsWith
import com.example.starstudent.core.domain.CurrentApplication
import com.example.starstudent.core.domain.MainActivity
import com.example.starstudent.core.domain.User
import com.example.starstudent.signInRegister.domain.Email
import com.example.starstudent.signInRegister.domain.ExistingUserException
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test
import kotlin.test.assertFails
import kotlin.test.assertFalse

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

    @Test
    fun testExistingUserException(){
        val exception = assertFails {
            throw ExistingUserException()
        }
        kotlin.test.assertEquals(
            "This email has already been used. Please try again.",
            exception.message
        )
    }

    @Test
    fun preexistingUserTrue() = runTest{
        assertFailsWith<ExistingUserException>{

            val testEmail = Email()
            testEmail.setEmail("lm@gmail.com")

            testEmail.preexistingUser()
        }
    }

    @Test
    fun preexistingUserFalse() = runTest{
        val testEmail = Email()
        testEmail.setEmail("thisisauniqueemail09281309218390@gmail.com")

        assertFalse(testEmail.preexistingUser())
    }

}