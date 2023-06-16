package com.rememberdev.greenify.model

import com.google.gson.annotations.SerializedName

data class UserModel(

	@field:SerializedName("user_id")
	val userId: Int,

	@field:SerializedName("phone")
	val phone: String,

	@field:SerializedName("fullname")
	val fullname: String,

	@field:SerializedName("email")
	val email: String,

	@field:SerializedName("username")
	val username: String
)
