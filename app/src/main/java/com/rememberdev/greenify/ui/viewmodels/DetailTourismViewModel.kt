package com.rememberdev.greenify.ui.viewmodels

import android.app.Application
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.rememberdev.greenify.data.remote.ApiConfig
import com.rememberdev.greenify.model.DataItem
import retrofit2.Call
import retrofit2.Response

class DetailTourismViewModel(private val application: Application) : ViewModel() {
    private val _dataDetailTourism = MutableLiveData<DataItem>()
    val dataDetailtourism: LiveData<DataItem> = _dataDetailTourism

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    fun getTourismById(id: Int) {
        _isLoading.value = true
        val client = ApiConfig.getApiService().getTourismById(id)
        client.enqueue(object : retrofit2.Callback<DataItem> {
            override fun onResponse(call: Call<DataItem>, response: Response<DataItem>) {
//                val data = response.body()
                if (response.isSuccessful) {
                    _dataDetailTourism.value = response.body()
                }
                _isLoading.value = false
            }

            override fun onFailure(call: Call<DataItem>, t: Throwable) {
                _isLoading.value = false
                Log.d(
                    "GetTourismById",
                    "onFailure: ${t.message.toString()}"
                )
            }
        })
    }
}