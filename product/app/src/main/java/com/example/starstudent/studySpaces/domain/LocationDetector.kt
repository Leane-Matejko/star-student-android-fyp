package com.example.starstudent.studySpaces.domain

import android.os.Looper
import com.example.starstudent.core.domain.CurrentApplication
import com.example.starstudent.studySpaces.data.entities.SavedLocations
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import com.google.android.gms.location.LocationRequest

class LocationDetector {

    private var longitude = 0.0

    private var latitude = 0.0

    private var withinStudySpace = false

    private val fusedLocationClient = LocationServices
        .getFusedLocationProviderClient(CurrentApplication.instance)

    private var locationCallback: LocationCallback? = null

    //Start tracking the user's location every five seconds (Resetting the longitude and latitude when successfull)
    fun startLocationUpdates(onLocation: (Double, Double) -> Unit) {
        val locationRequest = LocationRequest.Builder(
            Priority.PRIORITY_HIGH_ACCURACY,
            5000
        ).apply {
            setMinUpdateIntervalMillis(2000)
        }.build()

        locationCallback = object : LocationCallback() {
            override fun onLocationResult(result: LocationResult) {
                val location = result.lastLocation ?: return

                latitude = location.latitude
                longitude = location.longitude

                onLocation(latitude, longitude)
            }
        }

        fusedLocationClient.requestLocationUpdates(
            locationRequest,
            locationCallback!!,
            Looper.getMainLooper()
        )
    }

    fun stopLocationUpdates() {
        locationCallback?.let {
            fusedLocationClient.removeLocationUpdates(it)
        }
    }

    //Check if the user is within any of the saved study spaces
    fun checkLocation(savedLocations: List<SavedLocations>) {
        var checked = false
        if (longitude == 0.0 && latitude == 0.0){
            withinStudySpace = false
            return
        }
        for (location in savedLocations){
            if((longitude <= (location.longitude + 0.0000500)) &&
                (longitude >= (location.longitude - 0.0000500)) &&
                (latitude <= (location.latitude + 0.0000500)) &&
                (latitude >= (location.latitude - 0.0000500))
            ){
                checked = true
                break
            }
        }
        withinStudySpace = checked
    }

    //Return the string for the study space detector
    fun studyDetectorFormatted(): String {
        return if(withinStudySpace){
            "Study Space Detected"
        }else{
            "Not Detected"
        }
    }

    //Return if the user is within a study space
    fun getWithinStudySpace(): Boolean{
        return withinStudySpace
    }

    //Return the latitude of the user
    fun getLatitude(): Double{
        return latitude
    }

    //Return the longitude of the user
    fun getLongitude(): Double{
        return longitude
    }
}