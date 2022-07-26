package com.example.mtstetaandroid.data

import com.example.otusalekseymakarovmovies.data.features.movies.ActorsDataSource

class ActorsModel(
	private val genresDataSource: ActorsDataSource
) {
	
	fun getActors() = genresDataSource.getActors()
}