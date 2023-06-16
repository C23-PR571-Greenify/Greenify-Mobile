package com.rememberdev.greenify.model

import com.google.gson.annotations.SerializedName

data class GiveRatingResponse(

	@field:SerializedName("msg")
	val msg: String,

	@field:SerializedName("data")
	val data: DataGiveRating,

	@field:SerializedName("error")
	val error: Boolean
)

data class DataGiveRating(

	@field:SerializedName("place_name")
	val placeName: String,

	@field:SerializedName("category_id")
	val categoryId: Int,

	@field:SerializedName("id")
	val id: Int
)
