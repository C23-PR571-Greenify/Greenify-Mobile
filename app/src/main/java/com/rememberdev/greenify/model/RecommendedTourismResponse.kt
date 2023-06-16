package com.rememberdev.greenify.model

import com.google.gson.annotations.SerializedName

data class RecommendedTourismResponse(

	@field:SerializedName("msg")
	val msg: String,

	@field:SerializedName("data")
	val data: DataRecommendedTourism,

	@field:SerializedName("error")
	val error: Boolean
)

data class TourismImagesItem(

	@field:SerializedName("image_url")
	val imageUrl: String
)

data class CleanedResultItem(

	@field:SerializedName("place_name")
	val placeName: String,

	@field:SerializedName("createdAt")
	val createdAt: String,

	@field:SerializedName("tourism_images")
	val tourismImages: List<TourismImagesItem>,

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

data class DataRecommendedTourism(

	@field:SerializedName("pagination")
	val pagination: Pagination,

	@field:SerializedName("cleanedResult")
	val cleanedResult: List<CleanedResultItem>
)

data class Pagination(

	@field:SerializedName("totalItems")
	val totalItems: Int,

	@field:SerializedName("itemsPerPage")
	val itemsPerPage: Int,

	@field:SerializedName("totalPages")
	val totalPages: Int,

	@field:SerializedName("currentPage")
	val currentPage: Int
)
