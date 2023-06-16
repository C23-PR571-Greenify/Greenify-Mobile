package com.rememberdev.greenify.ui.viewmodels

import android.app.Application
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.rememberdev.greenify.data.preferences.IdUserPreference
import com.rememberdev.greenify.data.remote.ApiConfig
import com.rememberdev.greenify.model.UserModel
import retrofit2.Call
import retrofit2.Response

class UserViewModel(private val application: Application) : ViewModel() {

    private val _dataUser= MutableLiveData<UserModel>()
    val dataUser: LiveData<UserModel> = _dataUser

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    fun getUserById(user_id: Int) {
        _isLoading.value = true
        val client = ApiConfig.getApiService().getUserById(user_id)
        client.enqueue(object : retrofit2.Callback<UserModel> {
            override fun onResponse(call: Call<UserModel>, response: Response<UserModel>) {
                if (response.isSuccessful) {
                    _dataUser.value = response.body()
                }
                _isLoading.value = false
            }

            override fun onFailure(call: Call<UserModel>, t: Throwable) {
                _isLoading.value = false
                Log.e("CategoryViewModel", "onFailure: ${t.message.toString()}")
            }
        })
    }
}