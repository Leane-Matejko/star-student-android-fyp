package com.example.starstudent.core.data

import android.content.ContentValues
import android.content.Context
import android.util.Log
import android.view.View
import com.example.starstudent.core.domain.CurrentApplication
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import kotlinx.coroutines.tasks.await

class DatabaseInteractions{

    var resultDoc = arrayOf<String>()
    val applicationContext = CurrentApplication

    /* Checks the firebase database connection by checking if there is access to the test file.
    * */
    suspend fun checkDatabaseConnection(context: Context, collectionPath : String, documentPath: String) {

        if(NetworkConnectivity(applicationContext.instance.connectivityManager).testingInternetConnection()) {
            Log.d("FirestoreCheck", "Starting to connect to database")
            val db = Firebase.firestore
            val result =
                db.collection(collectionPath)
                    .document(documentPath)
                    .get()
                    .await()
            if (result.exists()){
                Log.d("FirestoreDebug", "Firestore call succeeded")
                //Connection to the database has been made
                if (result == null) {
                    //testing file cannot be found
                    Log.d(ContentValues.TAG, "Testing file cannot be found.");
                    resultDoc = arrayOf("File is Empty")
                }else {
                    //testing file has been found, saved values to a variable
                    Log.d(ContentValues.TAG, "${result.id} => ${result.data}")
                    resultDoc += arrayOf(result.id.toString(), result.data.toString())
                }
            }
                    Log.d("FirestoreDebug", "Firestore call completed")
                    //Ensuring a result can be found
                    if (result == null) {
                        Log.d(ContentValues.TAG, "Testing file cannot be found.");
                        resultDoc = arrayOf("File is Empty")
                    }else {
                        //Present the values from the testing file that can be compared
                        Log.d(ContentValues.TAG, "${result.id} => ${result.data}")
                        resultDoc += arrayOf(result.id.toString(), result.data.toString())
                        Log.d(ContentValues.TAG, resultDoc.contentToString())
                    }

        }else{
            throw NoInternetConnection()
        }
    }

}