package com.example.otusalekseymakarovmovies.data.features.movies

import com.example.otusalekseymakarovmovies.R
import com.example.otusalekseymakarovmovies.data.features.movies.ActorsDataSource

class ActorsDataSourceImpl : ActorsDataSource {
	override fun getActors() = listOf(
		Pair("Джейсон Стейтем", R.drawable.image_jason_statham_actor),
		Pair("Холт Маккэланни", R.drawable.image_holt_mccallany_actor),
		Pair("Джош Харнетт", R.drawable.image_josh_hartnett_actor)
	)
}