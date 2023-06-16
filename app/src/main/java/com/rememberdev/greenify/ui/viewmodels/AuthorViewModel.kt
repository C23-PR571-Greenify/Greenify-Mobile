package com.rememberdev.greenify.ui.viewmodels

import android.app.Application
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.rememberdev.greenify.data.remote.ApiConfig
import com.rememberdev.greenify.model.AuthorResponse
import com.rememberdev.greenify.model.DataAuthor
import retrofit2.Call
import retrofit2.Response

class AuthorViewModel(application: Application) : ViewModel() {

    private val _authorList = MutableLiveData<List<DataAuthor>>()
    val authorList: LiveData<List<DataAuthor>> = _authorList

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    fun getAuthors() {
        _isLoading.value = true
        val client = ApiConfig.getApiService().getAuthors()
        client.enqueue(object : retrofit2.Callback<AuthorResponse> {
            override fun onResponse(
                call: Call<AuthorResponse>,
                response: Response<AuthorResponse>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    _authorList.value = response.body()?.data as List<DataAuthor>
                }
            }

            override fun onFailure(call: Call<AuthorResponse>, t: Throwable) {
                _isLoading.value = false
                Log.e("AuthorViewModel", "onFailure: ${t.message.toString()}")
            }
        })
    }
}