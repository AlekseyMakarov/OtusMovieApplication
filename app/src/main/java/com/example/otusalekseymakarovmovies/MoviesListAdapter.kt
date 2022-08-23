package com.example.otusalekseymakarovmovies

import android.graphics.drawable.Drawable
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.otusalekseymakarovmovies.data.dto.MovieDto


class MoviesListAdapter(
    private val callback: (MovieDto, Int) -> Unit,
    private val moviesList: List<MovieDto>,
    private val favoriteCallback: (Int) -> Unit
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
        private val favorite: ImageView = itemView.findViewById(R.id.favorite)

        fun bind(
            movie: MovieDto,
            callback: ((MovieDto, Int) -> Unit)?,
            favoriteCallback: (Int) -> Unit
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
                        layoutPosition
                    )
                }
            }
            if (!movie.selected) movieDescription.setTextColor(itemView.context.getColor(R.color.black))
            else movieDescription.setTextColor(itemView.context.getColor(R.color.purple_500))


            favorite.setOnClickListener { favoriteCallback(layoutPosition) }
            if (movie.favourite) favorite.apply {
                setColorFilter(itemView.context.getColor(R.color.favorite))
                setImageResource(R.drawable.ic_baseline_favorite_24)
            } else favorite.apply {
                setColorFilter(itemView.context.getColor(R.color.white))
                setImageResource(R.drawable.ic_baseline_favorite_border_24)
            }
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
        getItem(position).let { holder.bind(it, callback, favoriteCallback) }
        Log.i("onBindViewHolder", position.toString())
    }

    override fun getItemCount(): Int {
        return moviesList.size
    }

}