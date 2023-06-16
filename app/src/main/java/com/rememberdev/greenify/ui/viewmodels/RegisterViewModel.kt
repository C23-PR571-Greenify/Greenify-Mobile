package com.rememberdev.greenify.ui.viewmodels

import android.app.Application
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.rememberdev.greenify.data.preferences.IdUserPreference
import com.rememberdev.greenify.data.remote.ApiConfig
import com.rememberdev.greenify.model.RegisterResponse
import retrofit2.Call
import retrofit2.Response

class RegisterViewModel(application: Application) : ViewModel() {
    companion object {
        private const val TAG = "RegisterViewModel"
    }

    private val _registerSuccess = MutableLiveData<Boolean>()
    val registerSuccess: LiveData<Boolean> = _registerSuccess

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val idUserPreferences = IdUserPreference(application)

    fun userRegister(
        fullname: String,
        username: String,
        email: String,
        password: String,
        phone: String,
        confirmPassword: String,
    ) {
        _isLoading.value = true
        val client = ApiConfig.getApiService()
            .userRegister(fullname, username, email, password, phone, confirmPassword)
        client.enqueue(object : retrofit2.Callback<RegisterResponse> {
            override fun onResponse(
                call: Call<RegisterResponse>,
                response: Response<RegisterResponse>,
            ) {
                if (response.isSuccessful) {
                    val dataUser = response.body()
                    if (dataUser != null) {
                        Log.d(TAG, "on Response Success: ${dataUser.msg}")
                        _registerSuccess.value = true
                        val userId = dataUser.data.userId.toString()
                        idUserPreferences.setUserID(userId)
                    } else {
                        Log.d(TAG, "Cannot create user: ${dataUser?.msg}")
                        _registerSuccess.value = false
                    }
                }
                _isLoading.value = false
            }

            override fun onFailure(call: Call<RegisterResponse>, t: Throwable) {
                Log.e(TAG, "onFailure: ${t.message.toString()}")
                _isLoading.value = false
            }
        })
    }
}

