package com.example.starstudent.core.domain

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities

fun Context.testingInternetConnection(): Boolean{

    val connectivityManager = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

    //Return null if there are no active network
    val network = connectivityManager.activeNetwork ?: return false

    //Return null if there is no Wi-Fi, Internet, Mobile Data available or high enough bandwidth
    val capabilities = connectivityManager.getNetworkCapabilities(network) ?: return false

    //Checking if the network connection is stable
    return capabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)

}