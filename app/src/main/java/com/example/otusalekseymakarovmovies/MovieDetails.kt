package com.example.otusalekseymakarovmovies

import android.content.Context
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.graphics.drawable.LayerDrawable
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.graphics.drawable.toBitmap
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import coil.load
import coil.transform.BlurTransformation
import com.example.otusalekseymakarovmovies.extensions.dpToPx

class MovieDetails : AppCompatActivity() {
    private var scaledRatingDrawable: Drawable? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_details)
        if (scaledRatingDrawable == null) {
            scaledRatingDrawable = createScaledRatingDrawable(this)
        }

        val title = intent.getStringExtra("Title")
        val imageUrl = intent.getStringExtra("ImageUrl")
        Log.i("HELLL", title!!)
        findViewById<ImageView>(R.id.movie_details_background)
            .load(imageUrl){
            allowHardware(false)
            transformations(BlurTransformation(this@MovieDetails, 25f))
        }
        findViewById<TextView>(R.id.textViewMovieAgeRestrictions).text = intent.getIntExtra("AgeRestriction", 0).let { "+$it" }
        findViewById<TextView>(R.id.textViewFilmName).text = intent.getStringExtra("Title")
        findViewById<TextView>(R.id.textViewDescription).text=intent.getStringExtra("Description")
        findViewById<RatingBar>(R.id.ratingBarMovieRatingDetails).apply {
            setProgressDrawableTiled(scaledRatingDrawable)
            rating = intent.getIntExtra("RateScore", 0).toFloat()
        }
//        = intent.getIntExtra("AgeRestriction", 0).toFloat()
        findViewById<ImageView>(R.id.movie_image).load(imageUrl){
            allowHardware(false)
        }


    }

    private fun createScaledRatingDrawable(context: Context): Drawable {

        val starEmptyBitmap = AppCompatResources.getDrawable(context, R.drawable.ic_star_empty)
            ?.toBitmap(context.dpToPx(15F), context.dpToPx(15F))
        val starBitmap = AppCompatResources.getDrawable(context, R.drawable.ic_star)
            ?.toBitmap(context.dpToPx(15F), context.dpToPx(15F))
        val newStarEmptyDrawable = BitmapDrawable(context.resources, starEmptyBitmap)
        val newStarDrawable = BitmapDrawable(context.resources, starBitmap)
        val finalDrawable = LayerDrawable(
            arrayOf(
                newStarEmptyDrawable,
                newStarEmptyDrawable,
                newStarDrawable,
            )
        )
        finalDrawable.setId(0, android.R.id.background)
        finalDrawable.setId(1, android.R.id.secondaryProgress)
        finalDrawable.setId(2, android.R.id.progress)
        return finalDrawable
    }

}