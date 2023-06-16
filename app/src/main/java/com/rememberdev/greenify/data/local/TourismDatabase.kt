//package com.rememberdev.greenify.data.local
//
//import android.content.Context
//import androidx.room.Database
//import androidx.room.Room
//import androidx.room.RoomDatabase
//import com.rememberdev.greenify.model.DataItem
//
//@Database(
//    entities = [DataItem::class],
//    version = 1,
//    exportSchema = false
//)
//
//abstract class TourismDatabase : RoomDatabase() {
//    companion object{
//        @Volatile
//        private var INSTANCE: TourismDatabase? = null
//
//        @JvmStatic
//        fun getDatabase(context: Context): TourismDatabase{
//            return INSTANCE ?: synchronized(this){
//                INSTANCE ?: Room.databaseBuilder(context.applicationContext,
//                TourismDatabase::class.java, "tourism_database")
//                    .fallbackToDestructiveMigration()
//                    .build()
//                    .also { INSTANCE = it }
//            }
//        }
//    }
//}