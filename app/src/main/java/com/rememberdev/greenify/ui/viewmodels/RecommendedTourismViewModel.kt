package com.rememberdev.greenify.ui.viewmodels

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.rememberdev.greenify.data.remote.ApiConfig
import com.rememberdev.greenify.model.CleanedResultItem
import com.rememberdev.greenify.model.RecommendedTourismResponse
import retrofit2.Call
import retrofit2.Response
import kotlin.math.ln

class RecommendedTourismViewModel(private val application: Application) : ViewModel() {

    companion object {
        private const val TAG = "RecommendedTourismViewModel"
    }

    private val _listRecommendedTourismSuccess = MutableLiveData<ArrayList<CleanedResultItem>>()
    val listRecommendedTourismSuccess: LiveData<ArrayList<CleanedResultItem>> =
        _listRecommendedTourismSuccess

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    fun getRecommendedTourism(lat: Any, lng: Any) {
        _isLoading.value = true
        val client = ApiConfig.getApiService().getRecommendedTourism(lat, lng)
        client.enqueue(object : retrofit2.Callback<RecommendedTourismResponse> {
            override fun onResponse(
                call: Call<RecommendedTourismResponse>,
                response: Response<RecommendedTourismResponse>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    val data = response.body()
                    if (data!!.error) {
                        Toast.makeText(application, data.msg, Toast.LENGTH_SHORT).show()
                        _isLoading.value = false
                    } else {
                        _listRecommendedTourismSuccess.value = data.data.cleanedResult as ArrayList<CleanedResultItem>
                        _isLoading.value = true
                    }
                }
                _isLoading.value = false
            }

            override fun onFailure(call: Call<RecommendedTourismResponse>, t: Throwable) {

            }
        })
    }
}