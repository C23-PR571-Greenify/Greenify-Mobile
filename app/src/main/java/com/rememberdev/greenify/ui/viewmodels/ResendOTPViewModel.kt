package com.rememberdev.greenify.ui.viewmodels

import android.app.Application
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.rememberdev.greenify.data.remote.ApiConfig
import com.rememberdev.greenify.model.ResendOTPResponse
import retrofit2.Call
import retrofit2.Response

class ResendOTPViewModel(private val application: Application) : ViewModel() {
    companion object {
        private const val TAG = "ResendOTPViewModel"
    }

    private val _resendOTPSuccess = MutableLiveData<Boolean>()
    val resendOTPSuccess: LiveData<Boolean> = _resendOTPSuccess

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    fun resendOTP(user_id: String, email: String){
        _isLoading.value = true
        val client = ApiConfig.getApiService().resendOTP(user_id, email)
        client.enqueue(object : retrofit2.Callback<ResendOTPResponse>{
            override fun onResponse(
                call: Call<ResendOTPResponse>,
                response: Response<ResendOTPResponse>
            ) {
                if (response.isSuccessful){
                    val data = response.body()
                    if (data!!.error){
                        Toast.makeText(application, data.msg, Toast.LENGTH_SHORT).show()
                        _isLoading.value = false
                        _resendOTPSuccess.value = false
                    } else {
                        //Berhasil Resend OTP
                        Toast.makeText(application, data.msg, Toast.LENGTH_SHORT).show()
                        _isLoading.value = true
                        _resendOTPSuccess.value = true
                    }
                }
                _isLoading.value = false
            }

            override fun onFailure(call: Call<ResendOTPResponse>, t: Throwable) {
                Log.e(TAG, "onFailure: ${t.message.toString()}")
                _isLoading.value = false
                _resendOTPSuccess.value = false
            }
        })
    }

}