package com.rmakiyama.locationsample.ui.coarse

import android.Manifest
import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.rmakiyama.locationsample.R
import com.rmakiyama.locationsample.model.Location
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import permissions.dispatcher.NeedsPermission
import permissions.dispatcher.RuntimePermissions
import timber.log.Timber

@RuntimePermissions
class CoarseLocationFragment : Fragment() {

    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var viewModel: CoarseLocationViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_coarse_location, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewModel = ViewModelProvider.AndroidViewModelFactory
            .getInstance(requireActivity().application)
            .create(CoarseLocationViewModel::class.java)
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireContext())
        getLocationWithPermissionCheck()
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        onRequestPermissionsResult(requestCode, grantResults)
    }

    @SuppressLint("MissingPermission")
    @NeedsPermission(Manifest.permission.ACCESS_COARSE_LOCATION)
    fun getLocation() {
        lifecycleScope.launch {
            runCatching { fusedLocationClient.lastLocation.await() }
                .onSuccess { location ->
                    Timber.i(
                        "location => ${Location(location.latitude, location.longitude)}"
                    )
                }
                .onFailure { Timber.e(it) }
        }
    }
}
