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
        email = Email()
        email.setEmail("testeremail@gmail.com")
    }

    @Test
    fun testEmailExists(){
        val email = Email()
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
        val testEmail = Email()
        testEmail.setEmail("fakeEmail.gmail.com")
        assertFalse(Email().containsAtSymbol())
    }

    @Test
    fun testCheckRealEmailTrue(){
        assertTrue(email.checkRealEmail())
    }

    @Test
    fun testNotRealEmailAddressException(){

        val exception = assertFails {
            throw NotRealEmailAddress()
        }
        assertEquals("This is not a real email address. Please try again.",
            exception.message)
    }


    @Test
    fun testCheckRealEmail(){
        assertEquals(true, email.checkRealEmail())
    }

    @Test
    fun testCheckFakeEmail() {
        val exception = assertFails {
            val testEmail = Email()
            testEmail.setEmail("fakeemail.com")
            testEmail.checkRealEmail()
        }
        assertEquals(
            "This is not a real email address. Please try again.",
            exception.message
        )
    }
}