package com.rememberdev.greenify.data.remote

import com.rememberdev.greenify.model.*
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @FormUrlEncoded
    @POST("users/signup")
    fun userRegister(
        @Field("fullname") fullname: String,
        @Field("username") username: String,
        @Field("email") email: String,
        @Field("password") password: String,
        @Field("phone") phone: String,
        @Field("confirmPassword") confirmPassword: String,
    ): Call<RegisterResponse>

    @GET("users/{user_id}")
    fun getUserById(
        @Path("user_id") user_id: Int
    ): Call<UserModel>

    @FormUrlEncoded
    @POST("auth/login")
    fun loginUser(
        @Field("email") email: String,
        @Field("password") password: String
    ): Call<LoginResponse>

    @FormUrlEncoded
    @POST("auth/verifyotp")
    fun verifyOTP(
        @Field("user_id") user_id: String,
        @Field("otp") otp: String
    ): Call<VerifyOTPResponse>

    @FormUrlEncoded
    @POST("auth/resendotp")
    fun resendOTP(
        @Field("user_id") user_id: String,
        @Field("email") email: String
    ): Call<ResendOTPResponse>

    @GET("categories")
    fun getCategories(): Call<CategoryResponse>

    @FormUrlEncoded
    @POST("tourism/recommendation")
    fun getRecommendedTourism(
        @Field("lat") lat: Any,
        @Field("lng") lng: Any
    ): Call<RecommendedTourismResponse>

    @GET("tourism")
    fun getAllTourism(): Call<AllTourismResponse>

    @GET("tourism/{id}")
    fun getTourismById(
        @Path("id") id: Int
    ): Call<DataItem>

    @GET("tourism/category/{id}")
    fun getTourismByCategoryId(
        @Path("id") id: Int
    ): Call<TourismByCategoryResponse>

    @GET("authors")
    fun getAuthors(): Call<AuthorResponse>

    @FormUrlEncoded
    @POST("tourism/rating")
    fun giveRating(
        @Field("user_id") user_id: String,
        @Field("tourism_id") tourism_id: String,
        @Field("rating") rating: Any
    ): Call<GiveRatingResponse>

}