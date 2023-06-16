package com.rememberdev.greenify.model

import com.google.gson.annotations.SerializedName

data class CategoryResponse(

	@field:SerializedName("msg")
	val msg: String,

	@field:SerializedName("data")
	val data: List<DataCategories>,

	@field:SerializedName("error")
	val error: Boolean
)

data class DataCategories(

	@field:SerializedName("createdAt")
	val createdAt: String,

	@field:SerializedName("name")
	val name: String,

	@field:SerializedName("description")
	val description: String,

	@field:SerializedName("average_rating")
	val averageRating: Any,

	@field:SerializedName("id")
	val id: Int,

	@field:SerializedName("updatedAt")
	val updatedAt: String
)
