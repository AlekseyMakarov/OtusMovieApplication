package com.example.mtstetaandroid.data

import com.example.otusalekseymakarovmovies.data.features.movies.GenresDataSource

class GenresModel(
	private val genresDataSource: GenresDataSource
) {
	
	fun getGenres() = genresDataSource.getGenres()
}