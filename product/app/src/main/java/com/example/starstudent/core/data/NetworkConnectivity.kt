package com.example.starstudent.core.data

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities

class NetworkConnectivity(private val connectivityManager : ConnectivityManager) {

    fun testingInternetConnection(): Boolean {

//        val connectivityManager =
//            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        //Return null if there are no active network
        val network = connectivityManager.activeNetwork ?: return false

        //Return null if there is no Wi-Fi, Internet, Mobile Data available or high enough bandwidth
        val capabilities = connectivityManager.getNetworkCapabilities(network) ?: return false

        //Checking if the network connection is stable
        return capabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)

    }
}