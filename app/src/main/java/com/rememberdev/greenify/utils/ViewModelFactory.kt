package com.rememberdev.greenify.utils

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.rememberdev.greenify.ui.viewmodels.*

class ViewModelFactory private constructor(private val mApplication: Application) :
    ViewModelProvider.NewInstanceFactory() {
    companion object {
        @Volatile
        private var INSTANCE: ViewModelFactory? = null

        @JvmStatic
        fun getInstance(application: Application): ViewModelFactory {
            if (INSTANCE == null) {
                synchronized(ViewModelFactory::class.java) {
                    INSTANCE = ViewModelFactory(application)
                }
            }
            return INSTANCE as ViewModelFactory
        }
    }

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(RegisterViewModel::class.java)) {
            return RegisterViewModel(mApplication) as T
        } else if (modelClass.isAssignableFrom(LoginViewModel::class.java)) {
            return LoginViewModel(mApplication) as T
        } else if (modelClass.isAssignableFrom(VerifyOTPViewModel::class.java)) {
            return VerifyOTPViewModel(mApplication) as T
        } else if (modelClass.isAssignableFrom(ResendOTPViewModel::class.java)) {
            return ResendOTPViewModel(mApplication) as T
        }else if (modelClass.isAssignableFrom(CategoriesViewModel::class.java)) {
            return CategoriesViewModel(mApplication) as T
        } else if (modelClass.isAssignableFrom(RecommendedTourismViewModel::class.java)) {
            return RecommendedTourismViewModel(mApplication) as T
        } else if (modelClass.isAssignableFrom(UserViewModel::class.java)) {
            return UserViewModel(mApplication) as T
        } else if (modelClass.isAssignableFrom(AllTourismViewModel::class.java)) {
            return AllTourismViewModel(mApplication) as T
        } else if (modelClass.isAssignableFrom(DetailTourismViewModel::class.java)) {
            return DetailTourismViewModel(mApplication) as T
        } else if (modelClass.isAssignableFrom(LocationViewModel::class.java)) {
            return LocationViewModel(mApplication) as T
        } else if (modelClass.isAssignableFrom(AuthorViewModel::class.java)) {
            return AuthorViewModel(mApplication) as T
        } else if (modelClass.isAssignableFrom(GiveRatingViewModel::class.java)) {
            return GiveRatingViewModel(mApplication) as T
        } else if (modelClass.isAssignableFrom(AllTourismByCategoryViewModel::class.java)) {
            return AllTourismByCategoryViewModel(mApplication) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
    }
}
