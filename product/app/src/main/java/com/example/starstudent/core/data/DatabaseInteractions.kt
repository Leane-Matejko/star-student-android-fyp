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

                if (result == null) {
                    Log.d(ContentValues.TAG, "Testing file cannot be found.");
                    resultDoc = arrayOf("File is Empty")
                }else {
                    Log.d(ContentValues.TAG, "${result.id} => ${result.data}")
                    resultDoc += arrayOf(result.id.toString(), result.data.toString())
//                    Log.d(ContentValues.TAG, resultDoc.contentToString())
                }
            }
                    Log.d("FirestoreDebug", "Firestore call completed")

                    if (result == null) {
                        Log.d(ContentValues.TAG, "Testing file cannot be found.");
                        resultDoc = arrayOf("File is Empty")
                    }else {
                        Log.d(ContentValues.TAG, "${result.id} => ${result.data}")
                        resultDoc += arrayOf(result.id.toString(), result.data.toString())
                        Log.d(ContentValues.TAG, resultDoc.contentToString())
                    }

//                }.addOnFailureListener { exception ->
//                    Log.w(ContentValues.TAG, "Error getting documents.", exception)
//                    Log.d("FirestoreCheck", "Cannot connect to database")
//                }

        }else{
            throw NoInternetConnection()
        }
    }

    fun searchIfUserExists() : Boolean{
        return false
    }

}

private fun Array<String>.await() {
    Log.d("WAITING...", "Adding info to result")
}
