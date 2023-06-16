package com.rememberdev.greenify.ui.fragments

import android.Manifest
import android.app.Application
import android.content.Intent
import android.content.pm.PackageManager
import android.content.res.Resources
import android.location.Location
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.MapView
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MapStyleOptions
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.rememberdev.greenify.R
import com.rememberdev.greenify.databinding.FragmentExploreBinding
import com.rememberdev.greenify.databinding.ItemRecommendedBinding
import com.rememberdev.greenify.ui.activities.DetailTourismActivity
import com.rememberdev.greenify.ui.viewmodels.DetailTourismViewModel
import com.rememberdev.greenify.ui.viewmodels.RecommendedTourismViewModel
import com.rememberdev.greenify.utils.ViewModelFactory

class ExploreFragment : Fragment(), OnMapReadyCallback {

    companion object{
        private const val TAG = "MapsFragment"
    }

    private lateinit var fusedLocationClient: FusedLocationProviderClient

    private lateinit var mapView: MapView
    private lateinit var googleMap: GoogleMap

    private var _binding: FragmentExploreBinding? = null
    private val binding get() = _binding!!

    private val recommendedTourismViewModel: RecommendedTourismViewModel by viewModels {
        ViewModelFactory.getInstance(Application())
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentExploreBinding.inflate(inflater, container, false)
        mapView = binding.mapView
        mapView.onCreate(savedInstanceState)
        mapView.getMapAsync(this)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mapView.onResume()

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireActivity())

        if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            fusedLocationClient.lastLocation
                .addOnSuccessListener { location: Location? ->
                    location?.let {
                        val latitude = location.latitude
                        val longitude = location.longitude

                        recommendedTourismViewModel.getRecommendedTourism(latitude, longitude)
                    }
                }
        } else {
            ActivityCompat.requestPermissions(requireActivity(), arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), 1)
        }
    }

    override fun onResume() {
        super.onResume()
        mapView.onResume()
    }

    override fun onPause() {
        super.onPause()
        mapView.onPause()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        mapView.onSaveInstanceState(outState)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        mapView.onDestroy()
        _binding = null
    }

    override fun onMapReady(googleMap: GoogleMap) {
        this.googleMap = googleMap

        this.googleMap.uiSettings.isZoomControlsEnabled = true
        this.googleMap.uiSettings.isIndoorLevelPickerEnabled = true
        this.googleMap.uiSettings.isCompassEnabled = true
        this.googleMap.uiSettings.isMapToolbarEnabled = true

        getLocationNow()
        getMapStyle()

        recommendedTourismViewModel.listRecommendedTourismSuccess.observe(this){
            it.forEach {
                val latLng = it.lat?.let { it1 -> it.lng?.let { it2 -> LatLng(it1 as Double,
                    it2 as Double
                ) } }
                latLng?.let { it1 -> MarkerOptions().position(it1).title(it.placeName).snippet(it.id.toString())}
                    ?.let { it2 -> googleMap.addMarker(it2) }
                latLng?.let { it1 -> CameraUpdateFactory.newLatLngZoom(it1, 14f) }
                    ?.let { it2 -> googleMap.moveCamera(it2) }
//                showInfoWindowAdapter(it.placeName, it.rating, it.city, it.price)
                googleMap.setOnInfoWindowClickListener  {
                    val placeName = it.title
                    val city = it.snippet

                    // Membuat intent untuk aktivitas baru
                    val intent = Intent(context, DetailTourismActivity::class.java)
                    intent.putExtra("placeName", placeName)
                    intent.putExtra("EXTRA_ID", city)

                    // Memulai aktivitas baru
                    startActivity(intent)

                    // Mengembalikan nilai true untuk menunjukkan bahwa klik marker telah ditangani
                    false
                }
            }
        }
    }

    private val requestPermissionLauncher =
        registerForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) { isGranted: Boolean ->
            if (isGranted) {
                getLocationNow()
            }
        }

    private fun getMapStyle() {
        try {
            val isSuccess = googleMap.setMapStyle(MapStyleOptions.loadRawResourceStyle(requireContext(), R.raw.map_style))
            if (!isSuccess){
                Log.e(TAG, "parsing Style failed!")
            }
        }catch (ex: Resources.NotFoundException){
            Log.e(TAG, "Cannot find Map Style! Error: ", ex)
        }
    }

    private fun getLocationNow() {
        if (ContextCompat.checkSelfPermission(
                this.requireContext(),
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            googleMap.isMyLocationEnabled = true
        } else {
            requestPermissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
        }
    }

    private fun showInfoWindowAdapter(placeName:String?, rating: Any, city: String?, price: String?) {
        googleMap.setInfoWindowAdapter(object : GoogleMap.InfoWindowAdapter{
            private val binding: ItemRecommendedBinding = ItemRecommendedBinding.inflate(layoutInflater)
            override fun getInfoContents(p0: Marker): View? {
                return null
            }

            override fun getInfoWindow(p0: Marker): View {
                Glide.with(context!!)
                    .load("https://www.salonlfc.com/wp-content/uploads/2018/01/image-not-found-scaled.png")
                    .into(binding.imgTourism)
                binding.tvTourismName.text = placeName
                binding.tvAverageRating.text = rating.toString()
                binding.tvLocation.text = city
                binding.tvPrice.text = "Rp. ${price} /Visit"

//                holder.itemView.setOnClickListener {
//                    val intent = Intent(it.context, DetailTourismActivity::class.java)
//                    intent.putExtra("EXTRA_ID", data.id.toString())
//                    intent.putExtra("EXTRA_NAME", data.placeName)
//                    it.context.startActivity(intent)
//                }
                return  binding.root
            }
        })
    }
}