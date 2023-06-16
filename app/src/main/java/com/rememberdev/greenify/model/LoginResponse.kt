package com.rememberdev.greenify.model

import com.google.gson.annotations.SerializedName

data class LoginResponse(

	@field:SerializedName("msg")
	val msg: String,

	@field:SerializedName("loginResult")
	val loginResult: LoginResult,

	@field:SerializedName("error")
	val error: Boolean
)

data class LoginResult(

	@field:SerializedName("userId")
	val userId: Int,

	@field:SerializedName("name")
	val name: String,

	@field:SerializedName("token")
	val token: String
)
