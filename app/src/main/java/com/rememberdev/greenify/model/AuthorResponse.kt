package com.rememberdev.greenify.model

import com.google.gson.annotations.SerializedName

data class AuthorResponse(

	@field:SerializedName("msg")
	val msg: String,

	@field:SerializedName("data")
	val data: List<DataAuthor>,

	@field:SerializedName("error")
	val error: Boolean
)

data class DataAuthor(

	@field:SerializedName("path")
	val path: String,

	@field:SerializedName("github")
	val github: String,

	@field:SerializedName("profile_url")
	val profileUrl: String,

	@field:SerializedName("id")
	val id: Int,

	@field:SerializedName("fullname")
	val fullname: String,

	@field:SerializedName("linkedIn")
	val linkedIn: String,

	@field:SerializedName("bangkit_id")
	val bangkitId: String
)
