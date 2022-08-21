package com.example.otusalekseymakarovmovies

import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.util.Log
import android.view.View.GONE
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.otusalekseymakarovmovies.data.dto.MovieDto
import com.google.android.material.floatingactionbutton.FloatingActionButton

class FavoriteMoviesActivity : AppCompatActivity() {
    lateinit var listView: RecyclerView
    private val moviesListActivity = MoviesList.movies.filter { it.favourite }.toMutableList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        listView = findViewById(R.id.ListViewMovies)
        val moviesListAdapter =
            MoviesListAdapter(::showDetails, moviesListActivity, ::addToFavorite)
        listView.adapter = moviesListAdapter
        listView.layoutManager = when (this.resources.configuration.orientation) {
            Configuration.ORIENTATION_PORTRAIT -> GridLayoutManager(
                this,
                2,
                RecyclerView.VERTICAL,
                false
            )
            Configuration.ORIENTATION_LANDSCAPE -> GridLayoutManager(
                this,
                3,
                RecyclerView.VERTICAL,
                false
            )
            else -> throw Exception("Can not get orientation")
        }
        listView.addItemDecoration(
            when (this.resources.configuration.orientation) {
                Configuration.ORIENTATION_PORTRAIT -> RecyclerDecorationPortrait
                Configuration.ORIENTATION_LANDSCAPE -> RecyclerDecorationLandscape
                else -> throw Exception("Can not get orientation")
            }
        )
        findFavoriteFAB()?.visibility = GONE

    }

    private fun findFavoriteFAB(): FloatingActionButton? =
        findViewById(R.id.fab)

    private fun addToFavorite(selectedItem: Int) {
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

    private fun showDetails(movieDto: MovieDto, selectedItem: Int) {
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

            MoviesList.selectedItem?.let {
                MoviesList.previousSelectedItem = MoviesList.selectedItem
            }
            val previousSelectedItem = MoviesList.previousSelectedItem
            MoviesList.selectedItem = indexMovie

            MoviesList.previousSelectedItem?.let { previousSelectedInMain ->
                val previousSelectedInFavorite = moviesListActivity.withIndex()
                    .find { itemInFavoriteList -> itemInFavoriteList.value == MoviesList.movies[previousSelectedInMain] }?.index
                if (previousSelectedInFavorite != null && previousSelectedInFavorite != selectedItem) {
                    moviesListActivity[previousSelectedInFavorite] =
                        moviesListActivity[previousSelectedInFavorite].run {
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