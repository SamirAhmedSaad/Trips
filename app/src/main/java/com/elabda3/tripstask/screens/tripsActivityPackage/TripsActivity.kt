package com.elabda3.tripstask.screens.tripsActivityPackage

import android.os.Bundle
import android.text.TextUtils
import androidx.lifecycle.Observer
import com.akexorcist.googledirection.util.DirectionConverter
import com.elabda3.tripstask.R
import com.elabda3.tripstask.base.BaseActivity
import com.elabda3.tripstask.base.getMarkerIcon
import com.elabda3.tripstask.base.loadingDialog
import com.elabda3.tripstask.base.myToast
import com.elabda3.tripstask.databinding.ActivityTripsBinding
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.*
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken


class TripsActivity : BaseActivity<ActivityTripsBinding,TripsViewModelImp>(), OnMapReadyCallback {

    lateinit var googleMap: GoogleMap

    var savedStateRetained = false

    override fun getViewModelClass(): Class<TripsViewModelImp> = TripsViewModelImp::class.java

    override fun resourceId(): Int = R.layout.activity_trips

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        savedStateRetained = savedInstanceState != null

        dataBinding.viewModel = viewModel

        val mapFragment = supportFragmentManager.findFragmentById(R.id.mapFragment) as SupportMapFragment
        mapFragment.getMapAsync(this)

        clicks()

    }


    private fun clicks() {


    }

    private fun observe() {

        viewModel.progressLiveData.observe(this, Observer {
            loadingDialog(it)
        })

        viewModel.alertMessageLiveData.observe(this, Observer {
            it.getContentIfNotHandled()?.let {alert ->
                myToast(alert)
            }
        })

        viewModel.tripLoaded.observe(this, Observer {
            dataBinding.trip = it

            val cameraUpdate = CameraUpdateFactory.newLatLngBounds(adjustLatlngMarkersToShowInScreen(it.pickLat,it.pickLng,it.destinationLat,it.destinationLng),calculatePaddingForMarkers())

            if(::googleMap.isInitialized){
                googleMap.animateCamera(cameraUpdate)
            }

        })

        viewModel.routePoints.observe(this, Observer {

            if(::googleMap.isInitialized) {
                googleMap.clear()
            }

            val polylineOptions = DirectionConverter.createPolyline(
                this,
                it,
                5,
                resources.getColor(R.color.blue)
            )

            if(::googleMap.isInitialized) {
                if(it != null && it.size > 0) {
                    googleMap.addMarker(
                        MarkerOptions().position(it[0])
                            .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED))
                    )
                    googleMap.addMarker(
                        MarkerOptions().position(it[it.size-1])
                            .icon(getMarkerIcon(resources.getColor(R.color.blue)))
                    )
                }
                googleMap.addPolyline(polylineOptions)
            }

        })

    }

    override fun onMapReady(googleMap: GoogleMap?) {

        googleMap ?: return

        this.googleMap = googleMap

        if(!savedStateRetained){
            viewModel.getLatestTrip()
        }

        observe()

    }

    private fun adjustLatlngMarkersToShowInScreen(
        pickLat : Double,pickLng : Double , destinationLat : Double , destinationLng : Double
    ) : LatLngBounds{
        val builder = LatLngBounds.Builder()
        val pickLocation = LatLng(pickLat,pickLng)
        val destintaionLocation = LatLng(destinationLat,destinationLng)
        builder.include(pickLocation)
        builder.include(destintaionLocation)

        return builder.build()
    }

    private fun calculatePaddingForMarkers() : Int{
        val width = resources.displayMetrics.widthPixels;
        val height = resources.displayMetrics.heightPixels;
        val  minMetric = Math.min(width, height);
        return (minMetric * .2).toInt()
    }

}
