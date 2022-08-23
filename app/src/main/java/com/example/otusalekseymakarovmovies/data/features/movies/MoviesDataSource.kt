package com.example.otusalekseymakarovmovies.data.features.movies

import com.example.otusalekseymakarovmovies.data.dto.MovieDto

interface MoviesDataSource {
	fun getMovies(): List<MovieDto>
}