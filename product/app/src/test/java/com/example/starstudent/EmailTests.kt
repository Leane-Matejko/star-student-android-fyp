package com.example.starstudent

import com.example.starstudent.signInRegister.domain.Email
import com.example.starstudent.signInRegister.domain.NotRealEmailAddress
import org.junit.Test
import org.junit.Before
import kotlin.test.assertEquals
import kotlin.test.assertFails
import kotlin.test.assertTrue
import kotlin.test.assertFalse

class EmailTests {

    private lateinit var email : Email

    @Before
    fun setup(){
        email = Email("testeremail@gmail.com")
    }

    @Test
    fun testEmailExists(){
        val email = Email("Test")
    }

    @Test
    fun testEmailGetter(){
        assertEquals("testeremail@gmail.com", email.getEmail())
    }

    @Test
    fun testContainAtSymbol(){
        assertTrue(email.containsAtSymbol())
    }

    @Test
    fun testDoesNotContainAtSymbol(){
        assertFalse(Email("fakeEmail.gmail.com").containsAtSymbol())
    }

    @Test
    fun testCheckRealEmailTrue(){
        assertTrue(email.checkRealEmail())
    }

    @Test
    fun testCheckRealEmailFalseThrows(){

        val exception = assertFails {
            throw NotRealEmailAddress()
        }
        assertEquals("This is not a real email address. Please try again.",
            exception.message)
    }



}