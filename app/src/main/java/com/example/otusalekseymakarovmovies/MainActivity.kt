package com.example.otusalekseymakarovmovies

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.otusalekseymakarovmovies.data.dto.MovieDto
import com.example.otusalekseymakarovmovies.data.features.movies.MoviesDataSourceImpl

class MainActivity : AppCompatActivity() {
    lateinit var listView: RecyclerView
    private var selectedItem: Int? = null
    private var previousSelectedItem: Int? = null
    private val movies: MutableList<MovieDto> = MoviesDataSourceImpl().getMovies().toMutableList()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        selectedItem = if (savedInstanceState?.containsKey("SelectedItem") == true)
            savedInstanceState.getInt("SelectedItem")
        else null
        setContentView(R.layout.activity_main)
        listView = findViewById(R.id.ListViewMovies)
        val moviesListAdapter = MoviesListAdapter(::ShowDetails, movies, ::addToFavorite)

        listView.adapter = moviesListAdapter
        listView.layoutManager = GridLayoutManager(this, 2, RecyclerView.VERTICAL, false)
        listView.addItemDecoration(RecyclerDecoration)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        selectedItem?.let { outState.putInt("SelectedItem", it) }
    }

    fun ShowDetails(movieDto: MovieDto, selectedItem: Int) {
        startActivity(
            Intent(this, MovieDetails::class.java)
                .putExtra("Description", movieDto.description)
                .putExtra("ImageUrl", movieDto.imageUrl)
                .putExtra("Title", movieDto.title)
                .putExtra("AgeRestriction", movieDto.ageRestriction)
                .putExtra("RateScore", movieDto.rateScore)
        )

        movies[selectedItem] = movies[selectedItem].run {
            MovieDto(
                title,
                description,
                rateScore,
                ageRestriction,
                imageUrl,
                true
            )
        }
        (listView.adapter as? MoviesListAdapter)?.notifyItemChanged(selectedItem)
        this.selectedItem?.let { previousSelectedItem = this.selectedItem }
        val previousSelectedItem = this.previousSelectedItem
        this.selectedItem = selectedItem
        if (previousSelectedItem != null && previousSelectedItem != selectedItem) {
            movies[previousSelectedItem] = movies[previousSelectedItem].run {
                MovieDto(
                    title,
                    description,
                    rateScore,
                    ageRestriction,
                    imageUrl,
                    false
                )
            }
            (listView.adapter as? MoviesListAdapter)?.notifyItemChanged(previousSelectedItem)
        }
    }

    fun addToFavorite(selectedItem: Int) {
        movies[selectedItem] = movies[selectedItem].run {
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
        (listView.adapter as? MoviesListAdapter)?.notifyItemChanged(selectedItem)
    }
}