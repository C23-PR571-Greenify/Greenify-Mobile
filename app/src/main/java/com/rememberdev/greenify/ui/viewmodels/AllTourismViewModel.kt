package com.rememberdev.greenify.ui.viewmodels

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.rememberdev.greenify.data.remote.ApiConfig
import com.rememberdev.greenify.model.AllTourismResponse
import com.rememberdev.greenify.model.CleanedResultItem
import com.rememberdev.greenify.model.DataItem
import retrofit2.Call
import retrofit2.Response

class AllTourismViewModel(private val application: Application) : ViewModel() {
    companion object {
        private const val TAG = "AllTourismViewModel"
    }

    private val _listAllTourismSuccess = MutableLiveData<ArrayList<DataItem>>()
    val listAllTourismSuccess: LiveData<ArrayList<DataItem>> =
        _listAllTourismSuccess

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    fun getAllTourism(){
        _isLoading.value = true
        val client = ApiConfig.getApiService().getAllTourism()
        client.enqueue(object : retrofit2.Callback<AllTourismResponse>{
            override fun onResponse(
                call: Call<AllTourismResponse>,
                response: Response<AllTourismResponse>
            ) {
                _isLoading.value = false
                if (response.isSuccessful){
                    val data = response.body()
                    _listAllTourismSuccess.value = data!!.data as ArrayList<DataItem>
                }
            }

            override fun onFailure(call: Call<AllTourismResponse>, t: Throwable) {

            }
        })
    }
}