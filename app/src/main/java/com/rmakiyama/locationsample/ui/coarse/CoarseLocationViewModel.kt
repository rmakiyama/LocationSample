package com.rmakiyama.locationsample.ui.coarse

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.rmakiyama.locationsample.DIContainer

class CoarseLocationViewModel(app: Application) : AndroidViewModel(app) {

    val locationClient = DIContainer.resolveLocationClient(getApplication())
    

}
