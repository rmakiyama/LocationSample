package com.rmakiyama.locationsample.ui.coarse

import android.Manifest
import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.rmakiyama.locationsample.DIContainer
import com.rmakiyama.locationsample.R
import com.rmakiyama.locationsample.location.LocationClient
import kotlinx.coroutines.launch
import permissions.dispatcher.NeedsPermission
import permissions.dispatcher.RuntimePermissions
import timber.log.Timber

@RuntimePermissions
class CoarseLocationFragment : Fragment() {

    private val fusedLocationClient: LocationClient by lazy {
        DIContainer.resolveLocationClient(requireActivity().application)
    }
    private val viewModel: CoarseLocationViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_coarse_location, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
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
            runCatching { fusedLocationClient.getLastLocation() }
                .onSuccess { location -> Timber.i("location => $location") }
                .onFailure { Timber.e(it) }
        }
    }
}
