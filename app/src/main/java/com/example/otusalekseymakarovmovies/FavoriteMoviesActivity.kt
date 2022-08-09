package com.example.otusalekseymakarovmovies

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.View.GONE
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.otusalekseymakarovmovies.data.dto.MovieDto
import com.example.otusalekseymakarovmovies.data.features.movies.MoviesDataSourceImpl

class FavoriteMoviesActivity : MainActivity() {
    override val moviesListActivity: MutableList<MovieDto>
        get() = MoviesList.favoriteMovies

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        findFavoriteFAB().visibility = GONE
    }

    override fun addToFavorite(selectedItem: Int) {
        val buf = MoviesList.favoriteMovies[selectedItem]
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
                true -> MoviesList.favoriteMovies.add(MoviesList.movies[indexMovie])
                false -> MoviesList.favoriteMovies.remove(buf)
            }
        }








        listView.adapter?.notifyDataSetChanged()
        //listView.adapter?.notifyItemChanged(selectedItem)
    }
}