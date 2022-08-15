package com.example.otusalekseymakarovmovies

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View.GONE
import com.example.otusalekseymakarovmovies.data.dto.MovieDto

class FavoriteMoviesActivity : MainActivity() {
    override val moviesListActivity: MutableList<MovieDto> =
        MoviesList.movies.filter { it.favourite }.toMutableList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        findFavoriteFAB().visibility = GONE
    }

    override fun addToFavorite(selectedItem: Int) {
        val buf = moviesListActivity[selectedItem]
        val indexMovie = MoviesList.movies.withIndex().find { it.value == buf }?.index

        if (indexMovie != null) {
            MoviesList.movies[indexMovie] = MoviesList.movies[indexMovie].run {
                MovieDto(
                    title,
                    description,
                    rateScore,
                    ageRestriction,
                    imageUrl,
                    selected,
                    !favourite
                )
            }
            when (MoviesList.movies[indexMovie].favourite) {
                true -> {
                    Log.e(
                        "FavoriteMoviesActivity",
                        "In this activity movies can only become unfavorite"
                    )

                }
                false -> {
                    moviesListActivity.remove(buf)
                    listView.adapter?.notifyItemRemoved(selectedItem)
                }
            }
        }


    }

    override fun ShowDetails(movieDto: MovieDto, selectedItem: Int) {
        val buf = moviesListActivity[selectedItem]
        val indexMovie = MoviesList.movies.withIndex().find { it.value == buf }?.index
        startActivity(
            Intent(this, MovieDetails::class.java)
                .putExtra("Description", movieDto.description)
                .putExtra("ImageUrl", movieDto.imageUrl)
                .putExtra("Title", movieDto.title)
                .putExtra("AgeRestriction", movieDto.ageRestriction)
                .putExtra("RateScore", movieDto.rateScore)
        )
        indexMovie?.let {
            selectInMainActivity(it)

            MoviesList.selectedItem?.let { MoviesList.previousSelectedItem = MoviesList.selectedItem }
            val previousSelectedItem = MoviesList.previousSelectedItem
            MoviesList.selectedItem = indexMovie

            MoviesList.previousSelectedItem?.let { it1 ->
                val previousSelectedInFavorite = moviesListActivity.withIndex()
                    .find { it2 -> it2.value == MoviesList.movies[it1] }?.index
                if (previousSelectedInFavorite != null && previousSelectedInFavorite != selectedItem) {
                    moviesListActivity[previousSelectedInFavorite] = moviesListActivity[previousSelectedInFavorite].run {
                        MovieDto(
                            title,
                            description,
                            rateScore,
                            ageRestriction,
                            imageUrl,
                            false,
                            favourite
                        )
                    }
                    (listView.adapter as? MoviesListAdapter)?.notifyItemChanged(
                        previousSelectedInFavorite
                    )
                }
            }
            unselectPreviousInMainActivity(it, previousSelectedItem)
        }

        moviesListActivity[selectedItem] = moviesListActivity[selectedItem].run {
            MovieDto(
                title,
                description,
                rateScore,
                ageRestriction,
                imageUrl,
                true,
                favourite
            )
        }
        (listView.adapter as? MoviesListAdapter)?.notifyItemChanged(selectedItem)
    }
}


fun selectInMainActivity(selectedItem: Int) {

    MoviesList.movies[selectedItem] = MoviesList.movies[selectedItem].run {
        MovieDto(
            title,
            description,
            rateScore,
            ageRestriction,
            imageUrl,
            true,
            favourite
        )
    }
}

fun unselectPreviousInMainActivity(selectedItem: Int, previousSelectedItem: Int?) {
    if ((previousSelectedItem != null) && (previousSelectedItem != selectedItem)) {
        MoviesList.movies[previousSelectedItem] = MoviesList.movies[previousSelectedItem].run {
            MovieDto(
                title,
                description,
                rateScore,
                ageRestriction,
                imageUrl,
                false,
                favourite
            )
        }
    }
}