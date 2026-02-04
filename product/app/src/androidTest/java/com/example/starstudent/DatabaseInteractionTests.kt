package com.example.starstudent

import android.content.Context
import android.net.ConnectivityManager
import android.util.Log
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.example.starstudent.core.data.DatabaseInteractions
import com.example.starstudent.core.data.NetworkConnectivity
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.runBlocking
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class DatabaseInteractionTests {

    @Test
    fun testDatabaseConnection() {
        val context = InstrumentationRegistry.getInstrumentation().targetContext
        val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    }
}

private fun Unit.await() {
    Log.d("AWAIT", "Retrieving from database")
}
