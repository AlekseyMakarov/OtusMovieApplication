package com.example.otusalekseymakarovmovies

import android.content.Intent
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import androidx.fragment.app.Fragment
import coil.load
import coil.transform.BlurTransformation
import com.example.otusalekseymakarovmovies.data.dto.MovieDto

class MovieDetailsFragment : Fragment() {
    private var scaledRatingDrawable: Drawable? = null


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_movie_details, container, false)
        if (scaledRatingDrawable == null) {
            scaledRatingDrawable = createScaledRatingDrawable(requireContext())
        }

        val args = requireArguments()

        val imageUrl = args.getString(IMAGE_URL_ARG)
        root.findViewById<ImageView>(R.id.movie_details_background)
            .load(imageUrl) {
                allowHardware(false)
                transformations(BlurTransformation(requireContext(), 25f))
            }
        root.findViewById<TextView>(R.id.textViewMovieAgeRestrictions).text =
            args.getInt(AGE_RESTRICTION_ARG, 0).let { "+$it" }
        root.findViewById<TextView>(R.id.textViewFilmName).text = args.getString(TITLE_ARG)
        root.findViewById<TextView>(R.id.textViewDescription).text = args.getString(DESCRIPTION_ARG)
        root.findViewById<RatingBar>(R.id.ratingBarMovieRatingDetails).apply {
            setProgressDrawableTiled(scaledRatingDrawable)
            rating = args.getInt(RATING_ARG, 0).toFloat()
        }
        root.findViewById<ImageView>(R.id.movie_image).load(imageUrl) {
            allowHardware(false)
        }
        root.findViewById<ImageView>(R.id.share_movie).setOnClickListener {
            startActivity(
                Intent(Intent.ACTION_SEND)
                    .setType("text/plain")
                    .putExtra(
                        Intent.EXTRA_TEXT,
                        getString(R.string.share_text_activity_movie_details) + args.getString(
                            TITLE_ARG) + "!"
                    )
            )
        }
        return root
    }

    companion object{
        const val DESCRIPTION_ARG = "description"
        const val TITLE_ARG = "title"
        const val RATING_ARG = "rating"
        const val AGE_RESTRICTION_ARG = "age_restriction"
        const val IMAGE_URL_ARG = "image_url"

        fun newInstance(movie: MovieDto): MovieDetailsFragment{
            val args = Bundle()
            args.putString(DESCRIPTION_ARG, movie.description)
            args.putString(TITLE_ARG, movie.title)
            args.putInt(RATING_ARG, movie.rateScore)
            args.putInt(AGE_RESTRICTION_ARG, movie.ageRestriction)
            args.putString(IMAGE_URL_ARG, movie.imageUrl)
            return MovieDetailsFragment().also { it.arguments = args }
        }
    }
}

