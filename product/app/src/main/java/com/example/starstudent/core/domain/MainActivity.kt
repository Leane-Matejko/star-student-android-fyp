package com.example.starstudent.core.domain

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.constraintlayout.motion.widget.Debug.getLocation
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.starstudent.core.domain.location.LocationPermissions
import com.example.starstudent.core.domain.navigation.Navigation
import com.example.starstudent.ui.theme.StarStudentTheme


class MainActivity : ComponentActivity() {

    val locationPermissions = LocationPermissions(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
         setContent{
             StarStudentTheme() {
                 Navigation()
             }
        }
        enableEdgeToEdge()

        //Checks if the user has allowed the system location permission to the app
        if(!locationPermissions.hasLocationPermission()){
            locationPermissions.requestLocationPermission()
        }
    }
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

