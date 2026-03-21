package com.example.starstudent.studySpaces.domain

import android.util.Log
import com.example.starstudent.core.domain.CurrentApplication
import com.example.starstudent.studySpaces.data.entities.SavedLocations
import com.google.android.gms.location.LocationServices

class LocationDetector {

    private var longitude = 0.0

    private var latitude = 0.0

    private var withinStudySpace = false

    val locationManager = LocationServices
        .getFusedLocationProviderClient(
            CurrentApplication.instance)

    fun getLocation() {
        locationManager.lastLocation
            .addOnSuccessListener { location ->
                if (location != null) {
                    latitude = location.latitude
                    longitude = location.longitude
                }
            }
            .addOnFailureListener {
                Log.d("GPS LAT", "Failed to get location.")
            }
    }

    suspend fun checkLocation(savedLocations: List<SavedLocations>) {
        var checked = false
        for (location in savedLocations){
            if((longitude <= (location.longitude + 0.0000350)) &&
                (longitude >= (location.longitude - 0.0000350)) &&
                (latitude <= (location.latitude + 0.0000350)) &&
                (latitude >= (location.latitude - 0.0000350))
            ){
                checked = true
                break
            }
        }

        withinStudySpace = checked
    }

    fun studyDetectorFormatted(): String {
        return if(withinStudySpace){
            "Study Space Detected"
        }else{
            "Not Detected"
        }
    }

    fun getWithinStudySpace(): Boolean{
        return withinStudySpace
    }

    fun getLatitude(): Double{
        return latitude
    }

    fun getLongitude(): Double{
        return longitude
    }
}