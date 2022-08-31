package com.example.otusalekseymakarovmovies

import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.otusalekseymakarovmovies.data.dto.MovieDto
import com.google.android.material.floatingactionbutton.FloatingActionButton

class FavoriteMoviesFragment: Fragment() {
    lateinit var listView: RecyclerView
    private val moviesListActivity = MoviesList.movies.filter { it.favourite }.toMutableList()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
    val root = inflater.inflate(R.layout.fragment_movies_list, container, false)
//        val textView: TextView = root.findViewById(R.id.text_home)
//        homeViewModel.text.observe(viewLifecycleOwner, Observer {
//            textView.text = it
//        })

        listView = root.findViewById(R.id.ListViewMovies)
        val moviesListAdapter =
            MoviesListAdapter(::showDetails, moviesListActivity, ::addToFavorite)
        listView.adapter = moviesListAdapter
        listView.layoutManager = when (this.resources.configuration.orientation) {
            Configuration.ORIENTATION_PORTRAIT -> GridLayoutManager(
                requireContext(),
                2,
                RecyclerView.VERTICAL,
                false
            )
            Configuration.ORIENTATION_LANDSCAPE -> GridLayoutManager(
                requireContext(),
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
        findFavoriteFAB()?.visibility = View.GONE
    return root
}
    private fun findFavoriteFAB(): FloatingActionButton? =
        view?.findViewById(R.id.fab)

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
        val selectedInMain = MoviesList.movies.withIndex().find { it.value == buf }?.index
//        startActivity(
//            Intent(this, MovieDetails::class.java)
//                .putExtra("Description", movieDto.description)
//                .putExtra("ImageUrl", movieDto.imageUrl)
//                .putExtra("Title", movieDto.title)
//                .putExtra("AgeRestriction", movieDto.ageRestriction)
//                .putExtra("RateScore", movieDto.rateScore)
//        )
        selectedInMain?.let {
            selectInMainActivity(it)

            MoviesList.selectedItem?.let {
                MoviesList.previousSelectedItem = MoviesList.selectedItem
            }
            MoviesList.selectedItem = it

            unselectPreviousInFavorite(selectedItem)
            unselectPreviousInMainActivity(it)
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
    private fun selectInMainActivity(selectedItem: Int) {
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

    private fun unselectPreviousInMainActivity(selectedItem: Int) {
        val previousSelectedItem = MoviesList.previousSelectedItem
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

    private fun unselectPreviousInFavorite(selectedItem: Int){
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
    }
}