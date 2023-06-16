package com.rememberdev.greenify.ui.viewmodels

import android.app.Application
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.rememberdev.greenify.data.remote.ApiConfig
import com.rememberdev.greenify.model.CategoryResponse
import com.rememberdev.greenify.model.DataCategories
import retrofit2.Call
import retrofit2.Response

class CategoriesViewModel (application: Application) : ViewModel(){
    private val _categoryList = MutableLiveData<List<DataCategories>>()
    val categoryList: LiveData<List<DataCategories>> = _categoryList

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    fun getCategoriesList(){
        _isLoading.value = true
        val client = ApiConfig.getApiService().getCategories()
        client.enqueue(object : retrofit2.Callback<CategoryResponse> {
            override fun onResponse(
                call: Call<CategoryResponse>,
                response: Response<CategoryResponse>
            ) {
                _isLoading.value = false
                if (response.isSuccessful){
                    _categoryList.value = response.body()?.data as List<DataCategories>
                    Log.d("CategoryViewModel", _categoryList.toString())
                } else {
                    Log.d("CategoryViewModel","response onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<CategoryResponse>, t: Throwable) {
                _isLoading.value = false
                Log.e("CategoryViewModel", "onFailure: ${t.message.toString()}")
            }
        })
    }
}