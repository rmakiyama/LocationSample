package com.rmakiyama.locationsample.location

import android.annotation.SuppressLint
import android.content.Context
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.rmakiyama.locationsample.model.Location
import kotlinx.coroutines.tasks.await

interface LocationClient {

    suspend fun getLastLocation(): Location
}

@SuppressLint("MissingPermission")
internal class LocationClientImpl(
    context: Context
) : LocationClient {

    private val fusedLocationClient: FusedLocationProviderClient by lazy {
        LocationServices.getFusedLocationProviderClient(context)
    }

    override suspend fun getLastLocation(): Location {
        return fusedLocationClient.lastLocation.await().let { location ->
            Location(location.latitude, location.longitude)
        }
    }
}
