package com.rememberdev.greenify.ui.viewmodels

import android.app.Application
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.rememberdev.greenify.data.remote.ApiConfig
import com.rememberdev.greenify.model.GiveRatingResponse
import retrofit2.Call
import retrofit2.Response

class GiveRatingViewModel(private val application: Application) : ViewModel() {
    companion object {
        private const val TAG = "GiveRatingViewModel"
    }

    private val _giveRatingSuccess = MutableLiveData<Boolean>()
    val giveRatingSuccess: LiveData<Boolean> = _giveRatingSuccess

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    fun giveRating(user_id: String, tourism_id: String, rating: Any){
        _isLoading.value = true
        val client = ApiConfig.getApiService().giveRating(user_id, tourism_id, rating)
        client.enqueue(object : retrofit2.Callback<GiveRatingResponse>{
            override fun onResponse(
                call: Call<GiveRatingResponse>,
                response: Response<GiveRatingResponse>
            ) {
                if (response.isSuccessful){
                    val data = response.body()
                    if (data!!.error){
//                        Toast.makeText(application, data.msg, Toast.LENGTH_SHORT).show()
                        _isLoading.value = false
                        _giveRatingSuccess.value = false
                    } else {
                        //Berhasil Give Rating
//                        Toast.makeText(application, data.msg, Toast.LENGTH_SHORT).show()
                        _isLoading.value = true
                        _giveRatingSuccess.value = true
                    }
                }
                _isLoading.value = false
            }

            override fun onFailure(call: Call<GiveRatingResponse>, t: Throwable) {
                Log.e(TAG, "onFailure: ${t.message.toString()}")
                _isLoading.value = false
                _giveRatingSuccess.value = false
            }
        })
    }
}
