package com.rahulraghuwanshi.movieimdb.movie

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.RecyclerView
import com.rahulraghuwanshi.imdb_api.model.MovieResponse
import com.rahulraghuwanshi.imdb_api.model.Search
import com.rahulraghuwanshi.movieimdb.R
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch


@AndroidEntryPoint
class MovieListActivity : AppCompatActivity() {

    private val movieListViewModel by viewModels<MovieListViewModel>()

    lateinit var movieListAdapter: MovieListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_movie_list)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val movieName = intent.getStringExtra("movieName")
        if (movieName != null) {
            movieListViewModel.fetchMovieList(movieName)
        } else {
            // no movie name found
            Toast.makeText(this, "NO movie name found", Toast.LENGTH_SHORT).show()
        }

        observeFlow()
    }

    private fun observeFlow() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                movieListViewModel.movieListFlow.collectLatest {
                    if (it?.isNotEmpty() == true) {
                        setUpRecyclerView(it)
                    } else {
                        Toast.makeText(this@MovieListActivity, "No movie found", Toast.LENGTH_SHORT)
                            .show()
                    }
                }
            }
        }
    }


    private fun setUpRecyclerView(list: List<Search?>) {

        movieListAdapter = MovieListAdapter(list)

        val rvMovieList = findViewById<RecyclerView>(R.id.rvMovieList)
        rvMovieList.adapter = movieListAdapter
    }
}