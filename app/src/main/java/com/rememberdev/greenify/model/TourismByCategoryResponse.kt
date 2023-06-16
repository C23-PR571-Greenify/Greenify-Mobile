package com.rememberdev.greenify.model

import com.google.gson.annotations.SerializedName

data class TourismByCategoryResponse(

	@field:SerializedName("data")
	val data: List<DataTourismByCategory>
)

data class DataTourismByCategory(

	@field:SerializedName("place_name")
	val placeName: String,

	@field:SerializedName("createdAt")
	val createdAt: String,

	@field:SerializedName("tourism_images")
	val tourismImages: List<TourismByCategoryImagesItem>,

	@field:SerializedName("category_id")
	val categoryId: Int,

	@field:SerializedName("lng")
	val lng: Any,

	@field:SerializedName("city")
	val city: String,

	@field:SerializedName("price")
	val price: String,

	@field:SerializedName("rating")
	val rating: Any,

	@field:SerializedName("description")
	val description: String,

	@field:SerializedName("id")
	val id: Int,

	@field:SerializedName("lat")
	val lat: Any,

	@field:SerializedName("updatedAt")
	val updatedAt: String
)

data class TourismByCategoryImagesItem(

	@field:SerializedName("image_url")
	val imageUrl: String,

	@field:SerializedName("tourism_id")
	val tourismId: Int,

	@field:SerializedName("id")
	val id: Int
)
