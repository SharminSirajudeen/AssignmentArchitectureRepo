package com.example.assignmentarchitecture.ui.activities.mainactivity

import android.arch.lifecycle.Observer
import android.content.Context
import android.graphics.Color
import android.location.Location
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import com.example.assignmentarchitecture.R
import com.example.assignmentarchitecture.databinding.ActivityMainBinding
import com.example.assignmentarchitecture.domain.VehicleModel
import com.example.assignmentarchitecture.ui.base.BaseActivity
import com.example.assignmentarchitecture.ui.maphelper.VehicleMarkerRender
import com.example.assignmentarchitecture.utils.AppUtils
import com.example.assignmentarchitecture.utils.StringUtils
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.CircleOptions
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.LatLngBounds
import com.google.android.gms.maps.model.Marker
import com.google.maps.android.clustering.Cluster
import com.google.maps.android.clustering.ClusterManager
import javax.inject.Inject


class MainActivity : BaseActivity<ActivityMainBinding, MainActivityViewModel>(), MainActivityNavigator,
    OnMapReadyCallback, ClusterManager.OnClusterClickListener<VehicleModel>,
    ClusterManager.OnClusterItemClickListener<VehicleModel> {
    override var viewModel: MainActivityViewModel? = null
        @Inject set
    private var mClusterManager: ClusterManager<VehicleModel>? = null
    private lateinit var mMap: GoogleMap
    private var mapLoaded: Boolean = false
    private var vehiclesLoaded: Boolean = false
    private lateinit var vehiclesList: List<VehicleModel>
    var clickedVehicle: VehicleModel? = null

    var activityMainBinding: ActivityMainBinding? = null

    override val bindingVariable: Int
        get() = 0

    override val layoutId: Int
        get() = R.layout.activity_main

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initViews()
        val mapFragment = supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
        subscribeToVehiclesApi()
        subscribeToVehicleDetailsApi()
        viewModel!!.callVehiclesApi()
    }

    private fun initViews() {
        activityMainBinding = viewDataBinding
        if (activityMainBinding != null) {
            activityMainBinding!!.lifecycleOwner = this
        }
        viewModel!!.navigator = this
        activityMainBinding!!.viewModel = viewModel
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        mMap.setMaxZoomPreference(25.0f)
        mMap.setOnMapLoadedCallback(object : GoogleMap.OnMapLoadedCallback {
            override fun onMapLoaded() {
                mapLoaded = true
                updateMarker()
            }
        })

        mClusterManager = ClusterManager<VehicleModel>(this, mMap)
        mClusterManager!!.setOnClusterClickListener(this)
        mClusterManager!!.setOnClusterItemClickListener(this)
        mClusterManager!!.markerCollection.setOnInfoWindowAdapter(object : GoogleMap.InfoWindowAdapter {
            override fun getInfoWindow(marker: Marker): View {
                var inflater = getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
                var view = inflater.inflate(R.layout.marker_info_window, null)
                val description = view.findViewById<TextView>(com.example.assignmentarchitecture.R.id.description)
                val batteryLevel = view.findViewById<TextView>(com.example.assignmentarchitecture.R.id.battery_level)
                val timeStamp = view.findViewById<TextView>(com.example.assignmentarchitecture.R.id.time_stamp)
                val price = view.findViewById<TextView>(com.example.assignmentarchitecture.R.id.price)

                description.text = clickedVehicle?.description
                batteryLevel.text = clickedVehicle?.batteryLevel!!.toString()
                timeStamp.text = StringUtils.getDisplayDateFromTimeStamp(clickedVehicle?.timestamp)
                val priceWithCurrency = clickedVehicle?.currency + " " + clickedVehicle?.price
                price.text = priceWithCurrency
                return view
            }

            override fun getInfoContents(marker: Marker): View? {
                val inflater = getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
                val view = inflater.inflate(R.layout.marker_info_window, null)
                val description = view.findViewById<TextView>(R.id.description)
                val batteryLevel = view.findViewById<TextView>(R.id.battery_level)
                val timeStamp = view.findViewById<TextView>(R.id.time_stamp)
                val price = view.findViewById<TextView>(R.id.price)

                description.text = clickedVehicle?.description
                batteryLevel.text = clickedVehicle?.batteryLevel!!.toString()
                timeStamp.text = StringUtils.getDisplayDateFromTimeStamp(clickedVehicle?.timestamp)
                val priceWithCurrency = clickedVehicle?.currency + " " + clickedVehicle?.price
                price.text = priceWithCurrency

                return view
            }
        })
        mMap.setInfoWindowAdapter(mClusterManager!!.markerManager)
        val renderer = VehicleMarkerRender(this, mMap, mClusterManager)
        mClusterManager!!.renderer = renderer
    }

    private fun subscribeToVehiclesApi() {
        viewModel!!.vehiclesApiHandler?.isSuccessful?.observe(this, Observer {
            viewModel!!.setIsLoading(false)
            if (it != null) {
                if (it.isNotEmpty()) {
                    vehiclesList = it
                    vehiclesLoaded = true
                    updateMarker()
                    AppUtils.getInstance().showSnackBar(activityMainBinding!!.root, "Successfully loaded")
                }
            }
        })


        viewModel!!.vehiclesApiHandler?.isFailed?.observe(this, Observer {
            viewModel!!.setIsLoading(false)
            AppUtils.getInstance().showSnackBar(activityMainBinding!!.root, "Something went wrong")
        })
    }

    private fun subscribeToVehicleDetailsApi() {
        viewModel?.vehicleDetailsHandler?.isSuccessful?.observe(this, Observer {
            viewModel?.setIsLoading(false)
            if (it != null) {
                clickedVehicle = it

                for (marker in mClusterManager!!.markerCollection.markers) {
                    mClusterManager!!.markerManager.getInfoContents(marker)
                    mClusterManager!!.markerManager.getInfoWindow(marker)
                }
            }
        })

        viewModel?.vehicleDetailsHandler?.isFailed?.observe(this, Observer {
            viewModel?.setIsLoading(false)
            if (it != null) {
                viewModel!!.setIsLoading(false)
                AppUtils.getInstance().showSnackBar(activityMainBinding!!.root, "Something went wrong")
            }
        })
    }

    private fun updateMarker() {
        if (mapLoaded && vehiclesLoaded) {
            vehiclesLoaded = false
            mClusterManager?.addItems(vehiclesList)
            mClusterManager?.cluster()
            var latlongs = ArrayList<LatLng>()
            for (vehicleModel in vehiclesList) {
                var location = LatLng(vehicleModel.latitude, vehicleModel.longitude)
                latlongs.add(location)
            }
            var pair = getCenterWithZoomLevel(latlongs)
            if (pair != null) {
                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(pair.first, pair.second))
            }
        }
    }

    fun getCenterWithZoomLevel(latLongList: ArrayList<LatLng>): Pair<LatLng, Float>? {
        var max: Float = 0f
        if (latLongList.size == 0) {
            return null
        }
        val builder = LatLngBounds.Builder()
        latLongList.forEach {
            builder.include(it)
        }

        var center = builder.build().center
        var distance = 0f
        //get longest distance from center
        latLongList.forEach {
            distance = distance(center, it)

            if (distance > max) {
                max = distance
            }
        }
        val zoom = getZoomLevel(center, max.toDouble())
        return Pair(center, zoom)
    }

    private fun getZoomLevel(center: LatLng, radius: Double): Float {
        val circle = mMap.addCircle(CircleOptions().center(center).radius(radius).strokeColor(Color.TRANSPARENT))
        var zoomLevel = 0.0f

        if (circle != null) {
            circle.isVisible = false
            val radius = circle.radius
            val scale = radius / 500
            zoomLevel = ((16 - Math.log(scale) / Math.log(2.0)).toFloat() - 1f)
        }
        return zoomLevel
    }

    private fun distance(center: LatLng, latLong: LatLng): Float {
        val loc1 = Location("")
        loc1.latitude = center.latitude
        loc1.longitude = center.longitude

        val loc2 = Location("")
        loc2.latitude = latLong.latitude
        loc2.longitude = latLong.longitude
        return loc1.distanceTo(loc2)
    }

    override fun onClusterClick(cluster: Cluster<VehicleModel>?): Boolean {
        val builder = LatLngBounds.builder()
        val vehicleMarkers = cluster?.items
        if (vehicleMarkers != null) for (vehicleModel in vehicleMarkers) {
            val vehiclePosition = vehicleModel.position
            builder.include(vehiclePosition)
        }
        val bounds = builder.build()

        try {
            mMap.animateCamera(CameraUpdateFactory.newLatLngBounds(bounds, 100))
        } catch (error: Exception) {
            Log.e("error", error.message)
        }

        return false
    }


    override fun onClusterItemClick(marker: VehicleModel?): Boolean {
        for (marker1 in mClusterManager?.markerCollection!!.markers) {
            if (marker1.isInfoWindowShown) {
                Log.d("ddddddd", "true")
            }
        }
        if (clickedVehicle != null && (clickedVehicle?.id == marker?.id)) {
            clickedVehicle = null
            return true
        } else {
            viewModel?.getVehicleDetails(marker?.id)
            clickedVehicle = marker
            var latlongs = ArrayList<LatLng>()
            var location = LatLng(marker!!.latitude, marker!!.longitude)
            latlongs.add(location)

            var pair = getCenterWithZoomLevel(latlongs)
            if (pair != null) {
                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(pair.first, pair.second))
            }

            return false
        }
    }

    override fun showSnackBar(stringResId: Int) {
        AppUtils.getInstance().showSnackBar(activityMainBinding!!.root, getString(stringResId))
    }
}
