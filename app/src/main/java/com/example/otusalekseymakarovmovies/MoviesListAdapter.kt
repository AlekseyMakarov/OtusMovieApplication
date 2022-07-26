package com.example.otusalekseymakarovmovies

import android.content.Context
import android.graphics.Color
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
import coil.load
import coil.transform.BlurTransformation
import com.example.otusalekseymakarovmovies.data.dto.MovieDto
import com.example.otusalekseymakarovmovies.data.features.movies.MoviesDataSourceImpl


class MoviesListAdapter() : BaseAdapter() {
    private val moviesList: List<MovieDto> = MoviesDataSourceImpl().getMovies()
    private var ctx: Context? = null
    private var lInflater: LayoutInflater? = null
    private var callback: ((MovieDto)->Unit)? = null


    constructor(ctx: Context, callback: ((MovieDto)->Unit) ):this(){
        this.ctx = ctx
        this.callback = callback
        lInflater = ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater?
    }

//    fun MoviesListAdapter(context: Context, products: ArrayList<Product?>) {
//        ctx = context
//        objects = products
//        lInflater = ctx
//            .getSystemService(Context.LAYOUT_INFLATER_SERVICE)
//    }


    override fun getCount(): Int {
        return moviesList.size
    }

    override fun getItem(p0: Int): Any {
        return  moviesList[p0]
    }

    override fun getItemId(p0: Int): Long {
        return p0.toLong()
    }

    override fun getView(p0: Int, p1: View?, p2: ViewGroup?): View {


        var itemView: View? = p1
        itemView = itemView?: lInflater?.inflate(R.layout.movie_item, p2, false)


        val movieItem: MovieDto = moviesList[p0]

        // заполняем View в пункте списка данными из товаров: наименование, цена
        // и картинка

        // заполняем View в пункте списка данными из товаров: наименование, цена
        // и картинка
        (itemView?.findViewById(R.id.roundedImageViewMovie) as ImageView).load(movieItem.imageUrl){
            allowHardware(false)
            transformations(BlurTransformation(ctx!!, 20f))
        }
        (itemView.findViewById(R.id.textViewMovieTitle) as TextView).text = movieItem.title

        (itemView.findViewById(R.id.textViewMovieDescription) as TextView).text = movieItem.description
        (itemView.findViewById(R.id.ratingBarMovieRating) as RatingBar).rating=movieItem.rateScore.toFloat()
        (itemView.findViewById(R.id.textViewMovieAgeRestrictions) as TextView)
            .apply {
                val ageRestrict= "${movieItem.ageRestriction}+"
                text = ageRestrict
                Log.i("LESHA", Color.parseColor("blue").toString())
                setTextColor(Color.parseColor("blue"))
            }
        callback?.let { itemView.setOnClickListener { it(movieItem) } }
        return itemView
    }

}