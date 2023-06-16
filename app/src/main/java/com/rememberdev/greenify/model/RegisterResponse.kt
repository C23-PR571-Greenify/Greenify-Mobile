package com.rememberdev.greenify.model

import kotlinx.parcelize.Parcelize
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

@Parcelize
data class RegisterResponse(

	@field:SerializedName("msg")
	val msg: String,

	@field:SerializedName("data")
	val data: Data,

	@field:SerializedName("error")
	val error: Boolean
) : Parcelable

@Parcelize
data class Data(

	@field:SerializedName("userId")
	val userId: Int,

	@field:SerializedName("email")
	val email: String
) : Parcelable
