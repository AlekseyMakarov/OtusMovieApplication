package com.example.otusalekseymakarovmovies

import android.os.Bundle
import android.util.Log
import android.widget.ImageView
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
        val backgroundImage = findViewById<ImageView>(R.id.movie_details_background)
        val ctx = this
        backgroundImage.load(imageUrl){
            allowHardware(false)
            transformations(BlurTransformation(ctx, 25f))

        }

    }

}