package com.example.otusalekseymakarovmovies

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.os.Parcel
import android.os.Parcelable
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.transform.BlurTransformation
import com.example.otusalekseymakarovmovies.data.dto.MovieDto
import com.example.otusalekseymakarovmovies.data.features.movies.MoviesDataSourceImpl


class MoviesListAdapter(
    private val callback: (MovieDto, Int) -> Unit, private val moviesList: List<MovieDto>
) : RecyclerView.Adapter<MoviesListAdapter.ViewHolder>() {

    private var scaledRatingDrawable: Drawable? = null

    private fun getItem(p0: Int): MovieDto {
        return moviesList[p0]
    }

    override fun onViewAttachedToWindow(holder: ViewHolder) {
        super.onViewAttachedToWindow(holder)
        Log.i("onViewAttachedToWindow", "")
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val movieImage: ImageView = itemView.findViewById(R.id.roundedImageViewMovie)
        private val movieTitle: TextView = itemView.findViewById(R.id.textViewMovieTitle)
        private val movieDescription: TextView =
            itemView.findViewById(R.id.textViewMovieDescription)
        private val movieRating: RatingBar = itemView.findViewById(R.id.ratingBarMovieRating)
        private val movieAgeRestriction: TextView =
            itemView.findViewById(R.id.textViewMovieAgeRestrictions)

        fun bind(
            movie: MovieDto,
            callback: ((MovieDto, Int) -> Unit)?,
            selectedItem: Int,
        ) {
            movieImage.load(movie.imageUrl) {
                allowHardware(false)
            }
            movieTitle.text = movie.title
            movieDescription.text = movie.description
            movieRating.rating = movie.rateScore.toFloat()
            movieAgeRestriction.text = movie.ageRestriction.toString() + "+"
            callback?.let {
                itemView.setOnClickListener {
                    callback(
                        movie,
                        selectedItem
                    )
                }
            }
            if (!movie.selected) movieDescription.setTextColor(itemView.context.getColor(R.color.black))
            else movieDescription.setTextColor(itemView.context.getColor(R.color.purple_500))
        }


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        if (scaledRatingDrawable == null) {
            scaledRatingDrawable = createScaledRatingDrawable(parent.context)
        }

        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.movie_item,
                parent,
                false
            ).apply {
                this.findViewById<RatingBar>(R.id.ratingBarMovieRating).apply {
                    setProgressDrawableTiled(scaledRatingDrawable)
                }
            }
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        getItem(position).let { holder.bind(it, callback, position) }
        Log.i("onBindViewHolder", position.toString())
    }

    override fun getItemCount(): Int {
        return moviesList.size
    }

}