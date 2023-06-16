package com.rememberdev.greenify.ui.viewmodels

import android.app.Application
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.rememberdev.greenify.data.preferences.IdUserPreference
import com.rememberdev.greenify.data.preferences.TokenPreference
import com.rememberdev.greenify.data.remote.ApiConfig
import com.rememberdev.greenify.model.LoginResponse
import retrofit2.Call
import retrofit2.Response

class LoginViewModel(private val application: Application) : ViewModel() {

    companion object {
        private const val TAG = "LoginViewModel"
    }

    private val _loginSuccess = MutableLiveData<Boolean>()
    val loginSuccess: LiveData<Boolean> = _loginSuccess

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val idUserPreferences = IdUserPreference(application)
    private val tokenPreference = TokenPreference(application)

    fun loginUser(email: String, password: String) {
        _isLoading.value = true
        val client = ApiConfig.getApiService().loginUser(email, password)
        client.enqueue(object : retrofit2.Callback<LoginResponse> {
            override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
                if (response.isSuccessful) {
                    val data = response.body()
                    if (data != null){
                        Toast.makeText(
                            application,
                            "${data.msg} | ${data.msg}",
                            Toast.LENGTH_SHORT
                        ).show()
                        tokenPreference.setToken(data.loginResult.token)
//                        PREFERENCE ID USER -> USER PROFILE
                        idUserPreferences.setUserID(data.loginResult.userId.toString())
                        _isLoading.value = true
                        _loginSuccess.value = true
                    } else{
                        Toast.makeText(
                            application,
                            "${data?.msg} | ${data?.msg}",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
                else if (response.code() == 401){
                    _loginSuccess.value = false
                }
                _isLoading.value = false
            }

            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                Log.e(TAG, "onFailure: ${t.message.toString()}")
                _isLoading.value = false
                _loginSuccess.value = false
            }
        })
    }
}