package com.rememberdev.greenify.ui.viewmodels

import android.app.Application
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.rememberdev.greenify.data.remote.ApiConfig
import com.rememberdev.greenify.model.VerifyOTPResponse
import retrofit2.Call
import retrofit2.Response

class VerifyOTPViewModel(private val application: Application) : ViewModel() {
    companion object {
        private const val TAG = "VerifyOTPViewModel"
    }

    private val _verifySuccess = MutableLiveData<Boolean>()
    val verifySuccess: LiveData<Boolean> = _verifySuccess

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    fun verifyOTP(user_id: String, otp: String) {
        _isLoading.value = true
        val client = ApiConfig.getApiService().verifyOTP(user_id, otp)
        client.enqueue(object : retrofit2.Callback<VerifyOTPResponse> {
            override fun onResponse(
                call: Call<VerifyOTPResponse>,
                response: Response<VerifyOTPResponse>,
            ) {
                if (response.isSuccessful) {
                    val data = response.body()
                    if (data != null) {
                        if (data.status == "VERIFIED") {
                            Toast.makeText(
                                application,
                                "${data.status} | ${data.msg}",
                                Toast.LENGTH_SHORT
                            ).show()
                            _isLoading.value = true
                            _verifySuccess.value = true
                        } else {
                            Toast.makeText(
                                application,
                                "${data.status} | ${data.msg}",
                                Toast.LENGTH_LONG
                            ).show()
                            _isLoading.value = false
                            _verifySuccess.value = false
                        }
                    } else {
                        Toast.makeText(
                            application,
                            "${data?.status} | ${data?.msg}",
                            Toast.LENGTH_SHORT
                        ).show()
                        _verifySuccess.value = false
                        _isLoading.value = false
                    }
                }
            }

            override fun onFailure(call: Call<VerifyOTPResponse>, t: Throwable) {
                Log.e(TAG, "onFailure: ${t.message.toString()}")
                _isLoading.value = false
                _verifySuccess.value = false
            }
        })
    }
}