package com.example.starstudent

import com.example.starstudent.signInRegister.domain.Email
import com.example.starstudent.signInRegister.domain.SendEmail
import org.junit.Before
import kotlin.test.Test
import kotlin.test.assertEquals

class SendEmailTests {

    private lateinit var sendEmail : SendEmail

    @Before
    fun setup(){
        sendEmail = SendEmail()
        sendEmail.sendAuthEmail("test@gmail.com")
    }

    @Test
    fun sendEmailExists(){
        sendEmail = SendEmail()
    }

    @Test
    fun sendEmailMessageExists(){
        assertEquals("Authentication email feature. This will be added in a future version.",
            sendEmail.sendAuthEmail("test@gmail.com"))
    }
}

