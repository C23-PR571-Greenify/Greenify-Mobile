package com.rememberdev.greenify.ui.viewmodels

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.rememberdev.greenify.data.remote.ApiConfig
import com.rememberdev.greenify.model.DataTourismByCategory
import com.rememberdev.greenify.model.TourismByCategoryResponse
import retrofit2.Call
import retrofit2.Response

class AllTourismByCategoryViewModel(private val application: Application) : ViewModel() {
    companion object {
        private const val TAG = "AllTourismByCategoryViewModel"
    }

    private val _listTourismByCategory = MutableLiveData<ArrayList<DataTourismByCategory>>()
    val listTourismByCategory: LiveData<ArrayList<DataTourismByCategory>> =
        _listTourismByCategory

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    fun getAllTourismByCategory(category_id: Int) {
        _isLoading.value = true
        val client = ApiConfig.getApiService().getTourismByCategoryId(category_id)
        client.enqueue(object : retrofit2.Callback<TourismByCategoryResponse> {
            override fun onResponse(call: Call<TourismByCategoryResponse>, response: Response<TourismByCategoryResponse>) {
                _isLoading.value = false
                if (response.isSuccessful){
                    val data = response.body()
                    _listTourismByCategory.value = data!!.data as ArrayList<DataTourismByCategory>
                    _isLoading.value = false
                }
            }

            override fun onFailure(call: Call<TourismByCategoryResponse>, t: Throwable) {

            }
        })
    }
}