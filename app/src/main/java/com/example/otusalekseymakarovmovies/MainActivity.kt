package com.example.otusalekseymakarovmovies

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ListView
import android.widget.TextView
import com.example.otusalekseymakarovmovies.data.dto.MovieDto

class MainActivity : AppCompatActivity() {
    lateinit var listView: ListView
    private var selectedItem:Int? = null
    private var previousSelectedItem: Int? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        selectedItem =  if (savedInstanceState?.containsKey("SelectedItem") == true) savedInstanceState.getInt("SelectedItem") else null
        setContentView(R.layout.activity_main)
        listView = findViewById<ListView>(R.id.ListViewMovies)
        val moviesListAdapter = MoviesListAdapter(this, ::ShowDetails, selectedItem)
        listView.adapter = moviesListAdapter

        val textView: TextView = TextView(this)


    }

//    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
//        super.onRestoreInstanceState(savedInstanceState)
//        selectedItem =  if (savedInstanceState.containsKey("SelectedItem")) savedInstanceState.getInt("SelectedItem") else null
//    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        selectedItem?.let{outState.putInt("SelectedItem",it)}
    }

    fun ShowDetails(movieDto: MovieDto, selectedItem:Int, selectedView: View): Unit{
        startActivity(Intent(this, MovieDetails::class.java)
            .putExtra("Description", movieDto.description)
            .putExtra("ImageUrl", movieDto.imageUrl)
            .putExtra("Title", movieDto.title)
            .putExtra("AgeRestriction", movieDto.ageRestriction)
            .putExtra("RateScore", movieDto.rateScore))
        selectedView.findViewById<TextView>(R.id.textViewMovieDescription).setTextColor(getColor(R.color.purple_500))
        (listView.adapter as MoviesListAdapter).selectedItem = selectedItem
        this.selectedItem?.let { previousSelectedItem=this.selectedItem }
        this.selectedItem=selectedItem
//        val previousSelectedView = previousSelectedItem?.let{listView.getChildAt(it)}
//        Log.i("PREVIOUS VIEW", previousSelectedView.toString())
        if(previousSelectedItem!=selectedItem){
            for ((i, j) in (listView.firstVisiblePosition..listView.lastVisiblePosition).withIndex()){
                if(j==previousSelectedItem)
                    listView.getChildAt(i)
                        .findViewById<TextView>(R.id.textViewMovieDescription)?.setTextColor(getColor(R.color.black))
            }
        }

//        previousSelectedView?.findViewById<TextView>(R.id.textViewMovieDescription)?.setTextColor(getColor(R.color.black))
        Log.i("PREVIOUS", previousSelectedItem.toString())
        Log.i("SELECTED", this.selectedItem.toString())

    }







}