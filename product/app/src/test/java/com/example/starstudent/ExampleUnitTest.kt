package com.example.starstudent

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.util.Log
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import org.junit.Test

import org.junit.Assert.*
import org.mockito.Mockito.mock
import org.mockito.kotlin.whenever

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun addition_isCorrect() {
        assertEquals(4, 2 + 2)
    }

    fun createMockNetworkInstance(networkState: Boolean): Boolean {
        //creating the mock instance
        val mockContext = mock<Context>()
        val mockCM = mock<ConnectivityManager>()
        val mockNetwork = mock<Network>()
        val mockCapabilities = mock<NetworkCapabilities>()

        //Creating a fake process flow
        whenever(mockContext.getSystemService(Context.CONNECTIVITY_SERVICE)).thenReturn(mockCM)
        whenever(mockCM.activeNetwork).thenReturn(mockNetwork)
        whenever(mockCM.getNetworkCapabilities(mockNetwork)).thenReturn(mockCapabilities)

        //Setting the status of the mock network instance
        whenever(mockCapabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET))
            .thenReturn(networkState)

        return mockContext.testingInternetConnection()
    }

    @Test
    fun checkDatabaseConnectionTrueTest() {
        val mockNetworkInstance = createMockNetworkInstance(true)
        assertTrue( mockNetworkInstance)
    }

    @Test
    fun checkDatabaseConnectionFalseTest() {
        val mockNetworkInstance = createMockNetworkInstance(false)
        assertFalse( mockNetworkInstance)
    }
}