package com.example.otusalekseymakarovmovies

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.otusalekseymakarovmovies.data.dto.MovieDto
import com.example.otusalekseymakarovmovies.data.features.movies.MoviesDataSourceImpl
import com.google.android.material.floatingactionbutton.FloatingActionButton
import android.content.res.Configuration.ORIENTATION_PORTRAIT
import android.content.res.Configuration.ORIENTATION_LANDSCAPE

object MoviesList {
    val movies: MutableList<MovieDto> = MoviesDataSourceImpl().getMovies().toMutableList()
    var selectedItem: Int? = null
    var previousSelectedItem: Int? = null
}

open class MainActivity : AppCompatActivity(), OnCustomDialogDismissClickListener {
    lateinit var listView: RecyclerView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        listView = findViewById(R.id.ListViewMovies)
        val moviesListAdapter =
            MoviesListAdapter(::showDetails, MoviesList.movies, ::addToFavorite)
        listView.adapter = moviesListAdapter
        listView.layoutManager = when (this.resources.configuration.orientation) {
            ORIENTATION_PORTRAIT -> GridLayoutManager(this, 2, RecyclerView.VERTICAL, false)
            ORIENTATION_LANDSCAPE -> GridLayoutManager(this, 3, RecyclerView.VERTICAL, false)
            else -> throw Exception("Can not get orientation")
        }
        listView.addItemDecoration(
            when (this.resources.configuration.orientation) {
                ORIENTATION_PORTRAIT -> RecyclerDecorationPortrait
                ORIENTATION_LANDSCAPE -> RecyclerDecorationLandscape
                else -> throw Exception("Can not get orientation")
            }
        )
        findFavoriteFAB()?.setOnClickListener {
            startActivity(
                Intent(this, FavoriteMoviesActivity::class.java)
            )
        }
    }

    override fun onBackPressed() {
        CustomDialog().show(supportFragmentManager, "dialog")

    }


    override fun onStart() {
        super.onStart()
        listView.adapter?.notifyDataSetChanged()
    }

    private fun findFavoriteFAB(): FloatingActionButton? = findViewById(R.id.fab)


    open fun showDetails(movieDto: MovieDto, selectedItem: Int) {
        startActivity(
            Intent(this, MovieDetails::class.java)
                .putExtra("Description", movieDto.description)
                .putExtra("ImageUrl", movieDto.imageUrl)
                .putExtra("Title", movieDto.title)
                .putExtra("AgeRestriction", movieDto.ageRestriction)
                .putExtra("RateScore", movieDto.rateScore)
        )

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
        (listView.adapter as? MoviesListAdapter)?.notifyItemChanged(selectedItem)
        MoviesList.selectedItem?.let { MoviesList.previousSelectedItem = MoviesList.selectedItem }
        val previousSelectedItem = MoviesList.previousSelectedItem
        MoviesList.selectedItem = selectedItem
        if (previousSelectedItem != null && previousSelectedItem != selectedItem) {
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
            listView.adapter?.notifyItemChanged(previousSelectedItem)
        }
    }

    open fun addToFavorite(selectedItem: Int) {
        MoviesList.movies[selectedItem] = MoviesList.movies[selectedItem].run {
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
        listView.adapter?.notifyItemChanged(selectedItem)
    }

    override fun onClickDismiss() {
       super.onBackPressed()
    }
}