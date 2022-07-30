package com.example.otusalekseymakarovmovies

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import coil.load
import coil.transform.BlurTransformation
import com.example.otusalekseymakarovmovies.databinding.ActivityMovieDetailsBinding

class MovieDetails : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_details)

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
        findViewById<RatingBar>(R.id.ratingBarMovieRatingDetails).rating = intent.getIntExtra("AgeRestriction", 0).toFloat()
        findViewById<ImageView>(R.id.movie_image).load(imageUrl){
            allowHardware(false)
        }

    }

}