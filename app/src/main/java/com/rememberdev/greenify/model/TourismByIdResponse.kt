package com.rememberdev.greenify.model

import com.google.gson.annotations.SerializedName

data class TourismByIdResponse(

	@field:SerializedName("place_name")
	val placeName: String,

	@field:SerializedName("CategoryById")
	val category: CategoryById,

	@field:SerializedName("lng")
	val lng: Any,

	@field:SerializedName("city")
	val city: String,

	@field:SerializedName("rating")
	val rating: Any,

	@field:SerializedName("description")
	val description: String,

	@field:SerializedName("createdAt")
	val createdAt: String,

	@field:SerializedName("tourism_images")
	val tourismImages: List<TourismByIdImagesItem>,

	@field:SerializedName("category_id")
	val categoryId: Int,

	@field:SerializedName("price")
	val price: String,

	@field:SerializedName("id")
	val id: Int,

	@field:SerializedName("lat")
	val lat: Any,

	@field:SerializedName("updatedAt")
	val updatedAt: String
)

data class CategoryById(

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

data class TourismByIdImagesItem(

	@field:SerializedName("createdAt")
	val createdAt: String,

	@field:SerializedName("image_url")
	val imageUrl: String,

	@field:SerializedName("tourism_id")
	val tourismId: Int,

	@field:SerializedName("id")
	val id: Int,

	@field:SerializedName("updatedAt")
	val updatedAt: String
)
