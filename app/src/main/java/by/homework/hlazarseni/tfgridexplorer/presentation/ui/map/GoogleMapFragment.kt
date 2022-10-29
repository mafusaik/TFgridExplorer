package by.homework.hlazarseni.tfgridexplorer.presentation.ui.map

import android.Manifest
import android.location.Location
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.navigation.ui.setupWithNavController
import by.homework.hlazarseni.tfgridexplorer.R
import by.homework.hlazarseni.tfgridexplorer.databinding.MapFragmentBinding
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.LocationSource
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import org.koin.android.ext.android.inject


class GoogleMapFragment : Fragment() {

    private lateinit var mMap: GoogleMap


    private val args by navArgs<GoogleMapFragmentArgs>()

    private var _binding: MapFragmentBinding? = null
    private val binding get() = requireNotNull(_binding)

    private var googleMap: GoogleMap? = null
    private var locationListener: LocationSource.OnLocationChangedListener? = null

    private val mapViewModel by inject<MapViewModel>()

    private val permissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isPermissionGranted ->
        if (isPermissionGranted) {

            mapViewModel
                .locationFlow
                .onEach { it.set(setCurrentLocation(it)) }
                .onEach { it.let(::moveCameraToLocation) }
                .onEach { it.apply { addMarkerToLocation(it) } }
                .launchIn(viewLifecycleOwner.lifecycleScope)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return MapFragmentBinding.inflate(inflater, container, false)
            .also { _binding = it }
            .root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        permissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)

        with(binding) {

            toolbarMap.setupWithNavController(findNavController())

            mapView.getMapAsync { map ->
                googleMap = map.apply {

                    uiSettings.isCompassEnabled = true
                    uiSettings.isZoomControlsEnabled = true
                    uiSettings.isMyLocationButtonEnabled = true

                    setLocationSource(object : LocationSource {
                        override fun activate(listener: LocationSource.OnLocationChangedListener) {
                            locationListener = listener
                        }

                        override fun deactivate() {
                            locationListener = null
                        }
                    })
                }
            }
            mapView.onCreate(savedInstanceState)
        }
    }

    private fun setCurrentLocation(location: Location): Location {
        location.longitude = args.longitude.toDouble()
        location.latitude = args.latitude.toDouble()
        return location
    }

    private fun moveCameraToLocation(location: Location) {
        val currentLocation = LatLng(location.latitude, location.longitude)
        googleMap?.animateCamera(
            CameraUpdateFactory.newLatLngZoom(currentLocation, 14f)
        )
    }

    private fun addMarkerToLocation(location: Location) {
        val currentLocation = LatLng(location.latitude, location.longitude)
        googleMap?.addMarker(
            MarkerOptions()
                .position(currentLocation)
                .title(getString(R.string.marker, args.node.nodeId))
        )
    }

    override fun onResume() {
        super.onResume()
        binding.mapView.onResume()
    }

    override fun onPause() {
        super.onPause()
        binding.mapView.onPause()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        binding.mapView.onSaveInstanceState(outState)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding.mapView.onDestroy()
        googleMap = null
        _binding = null
    }
}