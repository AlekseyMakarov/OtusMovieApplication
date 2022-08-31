package com.example.otusalekseymakarovmovies

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.otusalekseymakarovmovies.data.dto.MovieDto
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MoviesFragment: Fragment() {
    lateinit var listView: RecyclerView

    companion object{
        const val DESCRIPTION_ARG = "description"
        const val TITLE_ARG = "title"
        const val RATING_ARG = "rating"
        const val AGE_RESTRICTION_ARG = "age_restriction"
        const val IMAGE_URL_ARG = "image_url"
    }
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
            MoviesListAdapter(::showDetails, MoviesList.movies, ::addToFavorite)
        listView.adapter = moviesListAdapter
        listView.layoutManager = when (this.resources.configuration.orientation) {
            Configuration.ORIENTATION_PORTRAIT -> GridLayoutManager(requireContext(), 2, RecyclerView.VERTICAL, false)
            Configuration.ORIENTATION_LANDSCAPE -> GridLayoutManager(requireContext(), 3, RecyclerView.VERTICAL, false)
            else -> throw Exception("Can not get orientation")
        }
        listView.addItemDecoration(
            when (this.resources.configuration.orientation) {
                Configuration.ORIENTATION_PORTRAIT -> RecyclerDecorationPortrait
                Configuration.ORIENTATION_LANDSCAPE -> RecyclerDecorationLandscape
                else -> throw Exception("Can not get orientation")
            }
        )
        findFavoriteFAB()?.setOnClickListener {
//            startActivity(
//                Intent(this, FavoriteMoviesActivity::class.java)
//            )
        }

        Log.i(this.javaClass.name, "onCreateView")
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.i(this.javaClass.name, "onViewCreated")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.i(this.javaClass.name, "onCreate")
    }

    override fun onStart() {
        super.onStart()
        Log.i(this.javaClass.name, "onStart")
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        Log.i(this.javaClass.name, "onAttach")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.i(this.javaClass.name, "onDestroy")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        Log.i(this.javaClass.name, "onDestriyView")
    }

    override fun onPause() {
        super.onPause()
        Log.i(this.javaClass.name, "onPause")
    }

    override fun onDetach() {
        super.onDetach()
        Log.i(this.javaClass.name, "onDetach")
    }

    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedInstanceState)
        Log.i(this.javaClass.name, "onViewStateRestored")
    }

    override fun onResume() {
        super.onResume()
        Log.i(this.javaClass.name, "onResume")
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        Log.i(this.javaClass.name, "onSaveInstanceState")
    }

    override fun onStop() {
        super.onStop()
        Log.i(this.javaClass.name, "onStop")
    }

    private fun findFavoriteFAB(): FloatingActionButton? = view?.findViewById(R.id.fab)


    fun showDetails(movieDto: MovieDto, selectedItem: Int) {
//        startActivity(
//            Intent(this, MovieDetails::class.java)
//                .putExtra("Description", movieDto.description)
//                .putExtra("ImageUrl", movieDto.imageUrl)
//                .putExtra("Title", movieDto.title)
//                .putExtra("AgeRestriction", movieDto.ageRestriction)
//                .putExtra("RateScore", movieDto.rateScore)
//        )

//        parentFragmentManager.beginTransaction()
//            .setReorderingAllowed(true)
//            .addToBackStack("home")
//            .replace(R.id.fragment, MovieDetailsFragment.newInstance(movieDto), "")
//            .commit()
//        val bundle = bundleOf(USERNAME_KEY to myDataset[position])
        val args = Bundle()
        args.putString(MovieDetailsFragment.DESCRIPTION_ARG, movieDto.description)
        args.putString(MovieDetailsFragment.TITLE_ARG, movieDto.title)
        args.putInt(MovieDetailsFragment.RATING_ARG, movieDto.rateScore)
        args.putInt(MovieDetailsFragment.AGE_RESTRICTION_ARG, movieDto.ageRestriction)
        args.putString(MovieDetailsFragment.IMAGE_URL_ARG, movieDto.imageUrl)
        findNavController().navigate(R.id.action_movies_list_to_details, args)




//        holder.item.findNavController().navigate(
//            R.id.action_leaderboard_to_userProfile,
//            bundle)



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

    fun addToFavorite(selectedItem: Int) {
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
}