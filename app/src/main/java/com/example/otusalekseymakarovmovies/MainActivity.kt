package com.example.otusalekseymakarovmovies

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ListView
import android.widget.TextView

class MainActivity : AppCompatActivity() {
    lateinit var listView: ListView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val listView = findViewById<ListView>(R.id.ListViewMovies)
        val moviesListAdapter = MoviesListAdapter(this)
        listView.adapter = moviesListAdapter
        val textView: TextView = TextView(this)


    }







}