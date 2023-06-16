package com.rememberdev.greenify.ui.viewmodels

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import androidx.core.app.ActivityCompat
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class LocationViewModel(private val context: Context) : ViewModel() {

    private val _lat = MutableLiveData<Double>()
    val lat: LiveData<Double> = _lat

    private val _long = MutableLiveData<Double>()
    val long: LiveData<Double> = _long

    private lateinit var locationManager: LocationManager
    private var locationListener: LocationListener? = null

    fun startLocationUpdates() {
        locationManager = context.getSystemService(Context.LOCATION_SERVICE) as LocationManager

        locationListener = object : LocationListener {
            override fun onLocationChanged(location: Location) {
                val latitude = location.latitude
                val longitude = location.longitude

                _lat.value = latitude
                _long.value = longitude

                // Do something with the latitude and longitude values
                // For example, update LiveData properties or trigger a callback
            }

            override fun onStatusChanged(provider: String, status: Int, extras: Bundle) {}

            override fun onProviderEnabled(provider: String) {}

            override fun onProviderDisabled(provider: String) {}
        }

        // Check and request location permissions if not granted
        if (ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            // Handle the case when permissions are not granted
            return
        }

        // Request location updates
        locationManager.requestLocationUpdates(
            LocationManager.GPS_PROVIDER,
            0,
            0f,
            locationListener as LocationListener
        )
    }

    override fun onCleared() {
        super.onCleared()
        // Clean up resources when the ViewModel is no longer used
        locationManager.removeUpdates(locationListener!!)
    }
}