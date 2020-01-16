package com.rmakiyama.locationsample

import android.app.Application
import android.content.Context
import android.os.Build
import androidx.appcompat.app.AppCompatDelegate.*
import com.rmakiyama.locationsample.location.LocationClient
import com.rmakiyama.locationsample.location.LocationClientImpl
import timber.log.Timber

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        setupTimber()
        setupNightMode()
    }

    private fun setupTimber() {
        if (BuildConfig.DEBUG) Timber.plant(Timber.DebugTree())
    }

    private fun setupNightMode() {
        val nightMode = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            MODE_NIGHT_FOLLOW_SYSTEM
        } else {
            MODE_NIGHT_AUTO_BATTERY
        }
        setDefaultNightMode(nightMode)
    }
}

object DIContainer {

    fun resolveLocationClient(context: Context): LocationClient {
        return LocationClientImpl(context)
    }
}
