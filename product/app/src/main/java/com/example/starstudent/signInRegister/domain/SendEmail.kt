package com.example.starstudent.signInRegister.domain

//Firebase JSON has been set up, just kotlin set up remaining

import android.content.Context
import com.example.starstudent.core.data.NetworkConnectivity
import com.example.starstudent.core.domain.CurrentApplication
import com.google.firebase.Firebase
import com.google.firebase.functions.FirebaseFunctions

class SendEmail {
    private var authCode : String
    private var context : Context
    private val senderEmail = "starstudent.contact@gmail.com"
    private var nc : NetworkConnectivity
    private var fbfunc  = FirebaseFunctions.getInstance()

    constructor(receivedAuthCode: String, context: Context){
        authCode = receivedAuthCode
        this.context = context
        nc = NetworkConnectivity(CurrentApplication.instance.connectivityManager)
    }

    fun sendAuthEmail(recipientEmail: String){
        fbfunc
            .getHttpsCallable("sendEmail")
            .call(mapOf("email" to recipientEmail))
            .addOnSuccessListener {
                println("Email sent!")
            }
            .addOnFailureListener {
                println("Error: ${it.message}")
            }
    }
}