package com.learnproject.aoh1996.avitotraineeapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.LatLngBounds
import com.google.android.gms.maps.model.MarkerOptions
import com.learnproject.aoh1996.avitotraineeapplication.data.DataHolder
import com.learnproject.aoh1996.avitotraineeapplication.databinding.ActivityMapsBinding
import java.lang.IllegalStateException

private const val TAG = "MapsActivity"

class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var map: GoogleMap
    private lateinit var binding: ActivityMapsBinding
    private lateinit var boundsBuilder: LatLngBounds.Builder
    private lateinit var dataHolder: DataHolder

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMapsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        dataHolder = DataHolder.getInstance()

        boundsBuilder = LatLngBounds.builder()

        Log.d(TAG, "onCreate: ")

    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    override fun onMapReady(googleMap: GoogleMap) {
        Log.d(TAG, "onMapReady: ")
        map = googleMap

        updateMap()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.maps_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        return when(item.itemId) {
            R.id.mnuFilter -> {
                startActivity(Intent(this, FilterActivity::class.java))
                true
            }
            R.id.mnuNormal -> {
                map.mapType = GoogleMap.MAP_TYPE_NORMAL
                true
            }
            R.id.mnuSatellite -> {
                map.mapType = GoogleMap.MAP_TYPE_SATELLITE
                true
            }
            R.id.mnuTerrain -> {
                map.mapType = GoogleMap.MAP_TYPE_TERRAIN
                true
            }
            R.id.mnuHybrid -> {
                map.mapType = GoogleMap.MAP_TYPE_HYBRID
                true
            }

            else -> return super.onOptionsItemSelected(item)
        }
    }


    override fun onResume() {
        Log.d(TAG, "onResume: ")
        if (this::map.isInitialized) {
            updateMap()
        }
        super.onResume()
    }

    private fun updateMap() {

        Log.d(TAG, "updateMap: ")

        val set = dataHolder.servicesWithPins.value
        if (set != null) {
            if (set.isNotEmpty()) {
                map.clear()
                var counter = 0

                set.forEach {
                    if (it.value.isActive) {
                        Log.d(TAG, "service ${it.value.name} is active!")
                        it.value.servicePins.forEach {pin ->
                            //Log.d(TAG, "\n${pin.id}")
                            val pos = LatLng(pin.lat, pin.lng)
                            boundsBuilder.include(pos)
                            counter++
                            map.addMarker(MarkerOptions().position(pos).title(pin.id.toString()))
                        }
                    }
                }
                Log.d(TAG, "total pins: $counter")

                map.setOnMapLoadedCallback {
                    try {
                        map.animateCamera(CameraUpdateFactory.newLatLngBounds(boundsBuilder.build(), 130))
                    } catch (e: IllegalStateException) {
                        Log.d(TAG, "updateMap: no markers")
                    }
                }
            }
        }
    }
}