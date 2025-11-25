package com.example.starstudent

//import android.content.ContentValues.TAG
//import android.content.Context
//import android.net.ConnectivityManager
//import android.net.Network
//import android.net.NetworkCapabilities
//import android.net.NetworkRequest
import android.os.Bundle
//import android.util.Log
//import android.view.View
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.starstudent.navigation.Navigation
//import androidx.core.content.ContextCompat.getSystemService
//import androidx.lifecycle.lifecycleScope
//import com.example.starstudent.screens.SignInRegisterScreenView
import com.example.starstudent.ui.theme.StarStudentTheme
//import com.google.firebase.Firebase
//import com.google.firebase.firestore.firestore


class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent{
            Navigation()
        }
        enableEdgeToEdge()

//        lifecycleScope.launchWhenCreated {
//
//        }
    }

//    fun buttonClick(view: View?) {
//        println("This is a test")
//    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    StarStudentTheme {
        Greeting("Android")
    }
}

//fun checkDatabaseConnection(view: View) {
//    Log.d("FirestoreCheck", "Starting to connect to database")
//    val db = Firebase.firestore
//    db.collection("test").document("testingFile")
//        .get()
//        .addOnSuccessListener { result ->
//            Log.d("FirestoreDebug", "Firestore call succeeded")
//
//            if (result == null) {
//                Log.d(TAG, "Testing file cannot be found.");
//            }else {
//                Log.d(TAG, "${result.id} => ${result.data}")
//            }
//
//        }.addOnFailureListener { exception ->
//            Log.w(TAG, "Error getting documents.", exception)
//            Log.d("FirestoreCheck", "Starting to connect to database")
//        }
//    println(db);
//}

