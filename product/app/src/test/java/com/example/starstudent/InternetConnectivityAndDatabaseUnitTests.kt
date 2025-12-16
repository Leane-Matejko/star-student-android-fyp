package com.example.starstudent

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import com.example.starstudent.core.data.NetworkConnectivity
import com.example.starstudent.core.data.NoInternetConnection
import org.junit.Test

import org.junit.Assert.*
import org.mockito.Mockito.mock
import org.mockito.Mockito.`when`
import org.mockito.kotlin.whenever

import kotlin.test.assertFails

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class InternetConnectivityAndDatabaseUnitTests {
    @Test
    fun addition_isCorrect() {
        assertEquals(4, 2 + 2)
    }

    fun createMockNetworkInstance(networkState: Boolean): Boolean {
        //creating the mock instance
//        val mockContext = mock(Context::class.java)

        val mockConnectivityManager = mock(ConnectivityManager::class.java)
        val mockNetwork = mock(Network::class.java)
        val mockCapabilities = mock(NetworkCapabilities::class.java)

        //Creating a fake process flow
//        `when`(mockContext.getSystemService(Context.CONNECTIVITY_SERVICE))
//            .thenReturn(mockConnectivityManager)
        `when`(mockConnectivityManager.activeNetwork).thenReturn(mockNetwork)
        `when`(mockConnectivityManager.getNetworkCapabilities(mockNetwork)).thenReturn(mockCapabilities)

        //Setting the status of the mock network instance
        `when`(mockCapabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)).thenReturn(networkState)

        return NetworkConnectivity(mockConnectivityManager).testingInternetConnection()
    }

    @Test
    fun checkDatabaseConnectionTrueTest() {
        val mockNetworkInstance = createMockNetworkInstance(true)
        assertTrue(mockNetworkInstance)
    }

    @Test
    fun checkDatabaseConnectionFalseTest() {
        val mockNetworkInstance = createMockNetworkInstance(false)
        assertFalse( mockNetworkInstance)
    }

    @Test
    fun noInternetConnectionExceptionTest(){
        val exception = assertFails {
            throw NoInternetConnection()
        }
        assertEquals("No Internet Connection. Please try again later.", exception.message)
    }


}