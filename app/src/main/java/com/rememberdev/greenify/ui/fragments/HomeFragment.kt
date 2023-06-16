@file:Suppress("UNREACHABLE_CODE")

package com.rememberdev.greenify.ui.fragments

import android.Manifest
import android.annotation.SuppressLint
import android.app.AlertDialog
import android.app.Application
import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.rememberdev.greenify.R
import com.rememberdev.greenify.databinding.FragmentHomeBinding
import com.rememberdev.greenify.model.CleanedResultItem
import com.rememberdev.greenify.model.DataCategories
import com.rememberdev.greenify.model.DataItem
import com.rememberdev.greenify.ui.adapters.CategoriesAdapter
import com.rememberdev.greenify.ui.adapters.RecommendedAdapter
import com.rememberdev.greenify.ui.adapters.TourismAdapter
import com.rememberdev.greenify.ui.viewmodels.AllTourismViewModel
import com.rememberdev.greenify.ui.viewmodels.CategoriesViewModel
import com.rememberdev.greenify.ui.viewmodels.RecommendedTourismViewModel
import com.rememberdev.greenify.utils.ViewModelFactory
import kotlin.math.absoluteValue
import kotlin.properties.Delegates

class HomeFragment : Fragment() {

    private lateinit var fusedLocationClient: FusedLocationProviderClient

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private val categoriesViewModel: CategoriesViewModel by viewModels {
        ViewModelFactory.getInstance(Application())
    }

    private val recommendedTourismViewModel: RecommendedTourismViewModel by viewModels {
        ViewModelFactory.getInstance(Application())
    }

    private val allTourismViewModel: AllTourismViewModel by viewModels {
        ViewModelFactory.getInstance(Application())
    }

    private lateinit var rvCategory: RecyclerView
    private val listCategory = ArrayList<DataCategories>()

    private lateinit var rvRecommended: RecyclerView
    private val listRecommended = ArrayList<CleanedResultItem>()

    private lateinit var rvAlltourism: RecyclerView
    private val listAllTourism = ArrayList<DataItem>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentHomeBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireActivity())

        // Menginisialisasi TextView
        val locationTextView: TextView = view.findViewById(R.id.tv_home_current_location)

        if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            fusedLocationClient.lastLocation
                .addOnSuccessListener { location: Location? ->
                    location?.let {
                        val latitude = location.latitude
                        val longitude = location.longitude

                        // Menampilkan latitude dan longitude ke dalam TextView
                        locationTextView.text = "Latitude: $latitude\nLongitude: $longitude"

                        recommendedTourismViewModel.getRecommendedTourism(latitude, longitude)
                    }
                }
        } else {
            ActivityCompat.requestPermissions(requireActivity(), arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), 1)
        }

        categoriesViewModel.getCategoriesList()
//        recommendedTourismViewModel.getRecommendedTourism(-7.929798, 113.80669)
//        recommendedTourismViewModel.getRecommendedTourism(lat, long)
        allTourismViewModel.getAllTourism()

        rvCategory = binding.rvHomeCategory
        rvCategory.setHasFixedSize(true)
        showListCategories(listCategory)

        rvRecommended = binding.rvHomeRecommended
        rvRecommended.setHasFixedSize(true)
        showListRecommended(listRecommended)

        rvAlltourism = binding.rvHomeAllTourism
        rvAlltourism.setHasFixedSize(true)
        showListAllTourism(listAllTourism)

        categoriesViewModel.categoryList.observe(viewLifecycleOwner) {
            showListCategories(it)
        }

        recommendedTourismViewModel.listRecommendedTourismSuccess.observe(viewLifecycleOwner) {
            showListRecommended(it)
        }

        allTourismViewModel.listAllTourismSuccess.observe(viewLifecycleOwner) {
            showListAllTourism(it)
        }

    }

    private fun showListAllTourism(listAllTourism: ArrayList<DataItem>) {
        rvAlltourism.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        val adapter = TourismAdapter(listAllTourism)
        Log.d("rvRecommended", adapter.itemCount.toString())
        rvAlltourism.adapter = adapter
    }

    private fun showListRecommended(listRecommended: ArrayList<CleanedResultItem>) {
        rvRecommended.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        val adapter = RecommendedAdapter(listRecommended)
        Log.d("rvRecommended", adapter.itemCount.toString())
        rvRecommended.adapter = adapter
    }

    private fun showListCategories(listCategory: List<DataCategories>) {
        rvCategory.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        val adapter = CategoriesAdapter(listCategory)
        Log.d("rvCategories", adapter.itemCount.toString())
        rvCategory.adapter = adapter
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

//    override fun onLocationChanged(location: Location) {
//        val latitude = location.latitude
//        val longitude = location.longitude
//
//        // Gunakan latitude dan longitude sesuai kebutuhan Anda
//        // ...
//        lat = latitude
//        long = longitude
//    }

//    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
//        if (requestCode == 1) {
//            // Memeriksa apakah izin diberikan atau tidak
//            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//                if (ActivityCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
//                    // Mengaktifkan pembaruan lokasi
//                    locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0f, this)
//                }
//            } else {
//                // Izin ditolak, tindakan yang sesuai dapat diambil di sini
//                // ...
//            }
//        }
//    }
}