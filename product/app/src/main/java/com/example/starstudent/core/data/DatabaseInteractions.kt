package com.example.starstudent.core.data

import android.content.ContentValues
import android.content.Context
import android.util.Log
import android.view.View
import com.example.starstudent.core.data.NoInternetConnection
import com.example.starstudent.core.domain.testingInternetConnection
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore

class DatabaseInteractions{

    var resultDoc = arrayOf<String>()

    fun checkDatabaseConnection(context: Context, view: View) {

        if(context.testingInternetConnection()) {
            Log.d("FirestoreCheck", "Starting to connect to database")
            val db = Firebase.firestore
            db.collection("test").document("testingFile")
                .get()
                .addOnSuccessListener { result ->
                    Log.d("FirestoreDebug", "Firestore call succeeded")

                    if (result == null) {
                        Log.d(ContentValues.TAG, "Testing file cannot be found.");
                        resultDoc = arrayOf("File is Empty")
                    }else {
                        Log.d(ContentValues.TAG, "${result.id} => ${result.data}")
                        resultDoc = arrayOf(result.id.toString(), result.data.toString())
                    }

                }.addOnFailureListener { exception ->
                    Log.w(ContentValues.TAG, "Error getting documents.", exception)
                    Log.d("FirestoreCheck", "Cannot connect to database")
                }

        }else{
            throw NoInternetConnection()
        }
    }

}