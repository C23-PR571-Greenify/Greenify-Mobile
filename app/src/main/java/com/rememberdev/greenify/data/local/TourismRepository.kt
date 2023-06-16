//package com.rememberdev.greenify.data.local
//
//import androidx.lifecycle.LiveData
//import androidx.paging.Pager
//import androidx.paging.PagingConfig
//import androidx.paging.PagingData
//import androidx.paging.liveData
//import com.rememberdev.greenify.data.remote.ApiService
//import com.rememberdev.greenify.model.DataItem
//
//class TourismRepository(
//    private val tourismDatabase: TourismDatabase,
//    private val apiService: ApiService,
//) {
//    fun getTourism(): LiveData<PagingData<DataItem>> {
//        return Pager(
//            config = PagingConfig(
//                pageSize = 5
//            ),
//            pagingSourceFactory = {
//                TourismPagingSource(apiService)
//            }
//        ).liveData
//    }
//}