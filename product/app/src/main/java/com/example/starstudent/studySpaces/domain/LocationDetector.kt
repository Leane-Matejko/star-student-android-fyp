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

    //Get the user's most recent location via GPS
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

    //Check if the user is within any of the saved study spaces
    fun checkLocation(savedLocations: List<SavedLocations>) {
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