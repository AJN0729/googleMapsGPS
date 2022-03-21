package com.example.googlemapsgps

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.example.googlemapsgps.databinding.ActivityMapsBinding
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationServices

class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    private lateinit var binding: ActivityMapsBinding

    lateinit var mFusedLocationClient: FusedLocationProviderClient

    private val SECOND = 1000L

    val locationCallbackRequest = LocationRequest.create().apply {
        interval = 10*SECOND
        fastestInterval = 5*SECOND
        priority = LocationRequest.PRIORITY_HIGH_ACCURACY
    }

    fun startLocationTracking(context: Context) {
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(context)
        mFusedLocationClient.requestLocationUpdates()
    }

    fun stopLocationTracking() {

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMapsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        checkPermissionAndSetView()

    }

    private fun checkPermissionAndSetView() {
     if(PermissionHelper.arePermissionGranted(this)) {
         val mapFragment = supportFragmentManager
             .findFragmentById(R.id.map) as SupportMapFragment
         mapFragment.getMapAsync(this)
     } else {
         PermissionHelper.askForLocationPermission(this)
     }
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        val tulsa = LatLng(36.1540, -95.9928)
        mMap.addMarker(MarkerOptions().position(tulsa).title("Marker in Tulsa"))
        mMap.moveCamera(CameraUpdateFactory.newLatLng(tulsa))
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == PermissionHelper.REQUEST_CODE_FOR_PERMISSION) {
            checkPermissionAndSetView()
        }
    }
}