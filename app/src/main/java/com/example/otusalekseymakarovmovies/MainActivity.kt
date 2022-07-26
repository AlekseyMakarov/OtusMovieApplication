package com.example.otusalekseymakarovmovies

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ListView
import android.widget.TextView
import com.example.otusalekseymakarovmovies.data.dto.MovieDto

class MainActivity : AppCompatActivity() {
    lateinit var listView: ListView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val listView = findViewById<ListView>(R.id.ListViewMovies)
        val moviesListAdapter = MoviesListAdapter(this, ::ShowDetails)
        listView.adapter = moviesListAdapter
        val textView: TextView = TextView(this)


    }

    fun ShowDetails(movieDto: MovieDto): Unit{
        startActivity(Intent(this, MovieDetails::class.java)
            .putExtra("Description", movieDto.description)
            .putExtra("ImageUrl", movieDto.imageUrl)
            .putExtra("Title", movieDto.title)
            .putExtra("AgeRestriction", movieDto.ageRestriction)
            .putExtra("RateScore", movieDto.rateScore))

    }







}