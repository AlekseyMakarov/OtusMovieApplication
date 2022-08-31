package com.example.otusalekseymakarovmovies

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.otusalekseymakarovmovies.data.dto.MovieDto
import com.example.otusalekseymakarovmovies.data.features.movies.MoviesDataSourceImpl
import com.google.android.material.floatingactionbutton.FloatingActionButton
import android.content.res.Configuration.ORIENTATION_PORTRAIT
import android.content.res.Configuration.ORIENTATION_LANDSCAPE
import android.util.Log
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.FragmentManager
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationView

object MoviesList {
    val movies: MutableList<MovieDto> = MoviesDataSourceImpl().getMovies().toMutableList()
    var selectedItem: Int? = null
    var previousSelectedItem: Int? = null
}

open class MainActivity : AppCompatActivity(), OnCustomDialogDismissClickListener {
    lateinit var listView: RecyclerView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        listView = findViewById(R.id.ListViewMovies)
        val moviesListAdapter =
            MoviesListAdapter(::showDetails, MoviesList.movies, ::addToFavorite)
        listView.adapter = moviesListAdapter
        listView.layoutManager = when (this.resources.configuration.orientation) {
            ORIENTATION_PORTRAIT -> GridLayoutManager(this, 2, RecyclerView.VERTICAL, false)
            ORIENTATION_LANDSCAPE -> GridLayoutManager(this, 3, RecyclerView.VERTICAL, false)
            else -> throw Exception("Can not get orientation")
        }
        listView.addItemDecoration(
            when (this.resources.configuration.orientation) {
                ORIENTATION_PORTRAIT -> RecyclerDecorationPortrait
                ORIENTATION_LANDSCAPE -> RecyclerDecorationLandscape
                else -> throw Exception("Can not get orientation")
            }
        )
        findFavoriteFAB()?.setOnClickListener {
            startActivity(
                Intent(this, FavoriteMoviesActivity::class.java)
            )
        }
    }

    override fun onBackPressed() {
        CustomDialog().show(supportFragmentManager, "dialog")

    }


    override fun onStart() {
        super.onStart()
        listView.adapter?.notifyDataSetChanged()
    }

    private fun findFavoriteFAB(): FloatingActionButton? = findViewById(R.id.fab)


    open fun showDetails(movieDto: MovieDto, selectedItem: Int) {
        startActivity(
            Intent(this, MovieDetails::class.java)
                .putExtra("Description", movieDto.description)
                .putExtra("ImageUrl", movieDto.imageUrl)
                .putExtra("Title", movieDto.title)
                .putExtra("AgeRestriction", movieDto.ageRestriction)
                .putExtra("RateScore", movieDto.rateScore)
        )

        MoviesList.movies[selectedItem] = MoviesList.movies[selectedItem].run {
            MovieDto(
                title,
                description,
                rateScore,
                ageRestriction,
                imageUrl,
                true,
                favourite
            )
        }
        (listView.adapter as? MoviesListAdapter)?.notifyItemChanged(selectedItem)
        MoviesList.selectedItem?.let { MoviesList.previousSelectedItem = MoviesList.selectedItem }
        val previousSelectedItem = MoviesList.previousSelectedItem
        MoviesList.selectedItem = selectedItem
        if (previousSelectedItem != null && previousSelectedItem != selectedItem) {
            MoviesList.movies[previousSelectedItem] = MoviesList.movies[previousSelectedItem].run {
                MovieDto(
                    title,
                    description,
                    rateScore,
                    ageRestriction,
                    imageUrl,
                    false,
                    favourite
                )
            }
            listView.adapter?.notifyItemChanged(previousSelectedItem)
        }
    }

    open fun addToFavorite(selectedItem: Int) {
        MoviesList.movies[selectedItem] = MoviesList.movies[selectedItem].run {
            MovieDto(
                title,
                description,
                rateScore,
                ageRestriction,
                imageUrl,
                selected,
                !favourite
            )
        }
        listView.adapter?.notifyItemChanged(selectedItem)
    }

    override fun onClickDismiss() {
        super.onBackPressed()
    }
}

class DrawerActivity : AppCompatActivity() {



    private lateinit var drawerLayout: DrawerLayout
    private lateinit var navController: NavController
    private lateinit var appBarConfiguration: AppBarConfiguration

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        drawerLayout = findViewById(R.id.drawer_layout)


        ///////////////////////////////////////////
        //////////////////////////////////////////
        val navHostFragment = supportFragmentManager.findFragmentById(
            R.id.nav_host_fragment
        ) as NavHostFragment
        navController = navHostFragment.navController

        // Setup the bottom navigation view with navController
        val navigationView = findViewById<NavigationView>(R.id.nav_view)
        navigationView.setupWithNavController(navController)

        // Setup the ActionBar with navController and 3 top level destinations
        appBarConfiguration = AppBarConfiguration(
            setOf(R.id.movies_favorite_fragment, R.id.movies_screen),
            drawerLayout
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp(appBarConfiguration)
    }
}



        ///////////////////////////////////////////
        //////////////////////////////////////////




















//        val toolbar: Toolbar = findViewById(R.id.toolbar)
//        setSupportActionBar(toolbar)
//
////        val tab: TabLayout = findViewById (R.id.tab_layout)
//
//
//        supportActionBar?.setDisplayHomeAsUpEnabled(true)
//        drawerLayout = findViewById(R.id.drawer_layout)
//
//
//        val toggle = ActionBarDrawerToggle(
//            this,
//            drawerLayout,
//            toolbar,
//            R.string.navigation_drawer_open,
//            R.string.navigation_drawer_close
//        )
//        drawerLayout.addDrawerListener(toggle)
//
//        val navView: NavigationView = findViewById(R.id.nav_view)
//
//
//
//        navView.setNavigationItemSelectedListener {
//            when (selectedItem) {
//                R.id.nav_home -> {
//                    supportFragmentManager.saveBackStack("home")
//                    selectedItem = it.itemId
//                }
//                R.id.nav_favorite -> {
//                    supportFragmentManager.saveBackStack("favotite")
//                    selectedItem = it.itemId
//                }
//            }
//
//
//
//            when (it.itemId) {
//                R.id.nav_home -> {
//                    Log.i("FUCK", "FUCK")
////                    supportFragmentManager
////                        .beginTransaction()
////                        .setReorderingAllowed(true)
////                        .replace(
////                            R.id.fragment,
////                            supportFragmentManager.findFragmentByTag("movies") ?: MoviesFragment()
////                        )
////                        .addToBackStack("home")
////                        .commitNow()
//                    supportFragmentManager.restoreBackStack("home")
//
////                    if (supportFragmentManager.backStackEntryCount == 0){
////                        supportFragmentManager
////                            .beginTransaction()
////                            .replace(R.id.fragment, MoviesFragment(), "")
////                            .commit()
////                    }
//                }
//
//                R.id.nav_favorite -> {
//                    supportFragmentManager
//                        .beginTransaction()
//                        .replace(R.id.fragment, FavoriteMoviesFragment())
//                        .commit()
//                }
////                R.id.nav_slideshow -> {
////                    supportFragmentManager
////                        .beginTransaction()
////                        .replace(R.id.fragment, SlideshowFragment(), "")
////                        .commit()
////                }
////                R.id.nav_tools -> {
////                }
////                R.id.nav_gallery2 -> {
////                }
////                R.id.nav_share -> {
////                }
////                R.id.nav_send -> {
////                }
//                else -> return@setNavigationItemSelectedListener false
//            }
//            drawerLayout.closeDrawer(GravityCompat.START)
//            true
//        }
//
//        toggle.syncState()
//
//
//        if (savedInstanceState == null) {
//
//            navView.setCheckedItem(R.id.nav_home)
//
//            supportFragmentManager
//                .beginTransaction()
//
//                .setReorderingAllowed(true)
//                .replace(R.id.fragment, MoviesFragment(), "movies")
//                .addToBackStack("home")
//                .commit()
//
//
//        }
//
////        with(navView.menu.findItem(R.id.nav_share).actionView as TextView) {
////            text = "99"
////            gravity = Gravity.CENTER_VERTICAL
////        }
//    }
//
//
//
//
//
//
//
//
//    override fun onSupportNavigateUp(): Boolean {
//        onBackPressed()
//
//        return true
//    }
//
//    override fun onBackPressed() {
//        if (drawerLayout.isDrawerOpen(GravityCompat.START)) drawerLayout.closeDrawer(GravityCompat.START)
//        else when {
//            supportFragmentManager.backStackEntryCount > 1 -> super.onBackPressed()
//            supportFragmentManager.backStackEntryCount == 1 -> {
//                super.onBackPressed()
//
//                super.onBackPressed()
//
//            }
//        }
//    }
//}