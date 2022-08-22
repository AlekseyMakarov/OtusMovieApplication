package com.example.otusalekseymakarovmovies

import android.content.Intent
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import coil.load
import coil.transform.BlurTransformation

class MovieDetails : AppCompatActivity() {
    private var scaledRatingDrawable: Drawable? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_details)
        if (scaledRatingDrawable == null) {
            scaledRatingDrawable = createScaledRatingDrawable(this)
        }
        val imageUrl = intent.getStringExtra("ImageUrl")
        findViewById<ImageView>(R.id.movie_details_background)
            .load(imageUrl) {
                allowHardware(false)
                transformations(BlurTransformation(this@MovieDetails, 25f))
            }
        findViewById<TextView>(R.id.textViewMovieAgeRestrictions).text =
            intent.getIntExtra("AgeRestriction", 0).let { "+$it" }
        findViewById<TextView>(R.id.textViewFilmName).text = intent.getStringExtra("Title")
        findViewById<TextView>(R.id.textViewDescription).text = intent.getStringExtra("Description")
        findViewById<RatingBar>(R.id.ratingBarMovieRatingDetails).apply {
            setProgressDrawableTiled(scaledRatingDrawable)
            rating = intent.getIntExtra("RateScore", 0).toFloat()
        }
        findViewById<ImageView>(R.id.movie_image).load(imageUrl) {
            allowHardware(false)
        }
        findViewById<ImageView>(R.id.share_movie).setOnClickListener {
            startActivity(
                Intent(Intent.ACTION_SEND)
                    .setType("text/plain")
                    .putExtra(
                        Intent.EXTRA_TEXT,
                        getString(R.string.share_text_activity_movie_details) + intent.getStringExtra("Title") + "!"
                    )
            )
        }
    }
}

