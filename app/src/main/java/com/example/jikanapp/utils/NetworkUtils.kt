package com.example.jikanapp.utils

import android.annotation.SuppressLint
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import javax.inject.Inject

class NetworkUtil @Inject constructor(private val context: Context){
     fun isNetworkAvailable(): Boolean {
         val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

         val network = connectivityManager.activeNetwork ?: return false
         val capabilities = connectivityManager.getNetworkCapabilities(network) ?: return false

         return capabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET) &&
                 capabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_VALIDATED)
    }
}