package com.example.otusalekseymakarovmovies

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.otusalekseymakarovmovies.data.dto.MovieDto
import com.example.otusalekseymakarovmovies.data.features.movies.MoviesDataSourceImpl
import com.google.android.material.floatingactionbutton.FloatingActionButton

object MoviesList{
    val movies: MutableList<MovieDto> = MoviesDataSourceImpl().getMovies().toMutableList()
    var selectedItem: Int? = null
    var previousSelectedItem: Int? = null
    val favoriteMovies = mutableListOf<MovieDto>()
}

open class MainActivity : AppCompatActivity() {
    lateinit var listView: RecyclerView
    open val moviesListActivity = MoviesList.movies
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        listView = findViewById(R.id.ListViewMovies)
        val moviesListAdapter = MoviesListAdapter(::ShowDetails, moviesListActivity, ::addToFavorite)
        listView.adapter = moviesListAdapter
        listView.layoutManager = GridLayoutManager(this, 2, RecyclerView.VERTICAL, false)
        listView.addItemDecoration(RecyclerDecoration)
        findFavoriteFAB().setOnClickListener{startActivity(
            Intent(this, FavoriteMoviesActivity::class.java))}
    }


    override fun onStart() {
        super.onStart()
        listView.adapter?.notifyDataSetChanged()
    }

    fun findFavoriteFAB() = findViewById<FloatingActionButton>(R.id.fab)


    open fun ShowDetails(movieDto: MovieDto, selectedItem: Int) {
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
        val buf = MoviesList.movies[selectedItem]

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
        when (MoviesList.movies[selectedItem].favourite){
            true -> MoviesList.favoriteMovies.add(MoviesList.movies[selectedItem])
            false -> MoviesList.favoriteMovies.remove(buf)

        }
        listView.adapter?.notifyDataSetChanged()
    }
}