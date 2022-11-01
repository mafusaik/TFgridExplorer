package by.homework.hlazarseni.tfgridexplorer.presentation.ui.map

import android.annotation.SuppressLint
import android.content.Context
import android.location.Location
import android.os.Looper
import androidx.lifecycle.ViewModel
import by.homework.hlazarseni.tfgridexplorer.presentation.ui.constants.ValueConstants
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.*


@SuppressLint("MissingPermission")
class MapViewModel(context: Context): ViewModel() {

    private val locationClient = LocationServices.getFusedLocationProviderClient(context)

    val locationFlow: Flow<Location> = callbackFlow {
        val callback = object : LocationCallback() {
            override fun onLocationResult(locationResult: LocationResult) {
                trySend(locationResult.lastLocation)
            }
        }

        val request = LocationRequest.create().apply {
            interval = ValueConstants.LOCATION_UPDATE_INTERVAL
            priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        }

        locationClient.requestLocationUpdates(request, callback, Looper.getMainLooper())

        awaitClose {
            locationClient.removeLocationUpdates(callback)
        }
    }
}