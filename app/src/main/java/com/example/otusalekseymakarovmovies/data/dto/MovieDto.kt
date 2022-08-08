package com.example.otusalekseymakarovmovies.data.dto

data class MovieDto(
	val title: String,
	val description: String,
	val rateScore: Int,
	val ageRestriction: Int,
	val imageUrl: String,
	val selected: Boolean = false,
	val favourite: Boolean = false
)
