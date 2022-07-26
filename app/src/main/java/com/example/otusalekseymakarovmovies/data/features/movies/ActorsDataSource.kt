package com.example.otusalekseymakarovmovies.data.features.movies

interface ActorsDataSource {
    fun getActors(): List<Pair<String, Int>>
}