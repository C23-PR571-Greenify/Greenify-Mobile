package com.rememberdev.greenify.model

import com.google.gson.annotations.SerializedName

data class ResendOTPResponse(

	@field:SerializedName("msg")
	val msg: String,

	@field:SerializedName("data")
	val data: DataResend,

	@field:SerializedName("error")
	val error: Boolean
)

data class DataResend(

	@field:SerializedName("userId")
	val userId: String,

	@field:SerializedName("email")
	val email: String
)
