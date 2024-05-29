package com.rahulraghuwanshi.movieimdb.movie

import android.os.Bundle
import android.util.Log
import android.widget.ProgressBar
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.isVisible
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.RecyclerView
import com.rahulraghuwanshi.imdb_api.model.Search
import com.rahulraghuwanshi.imdb_api.util.RestClientResult
import com.rahulraghuwanshi.movieimdb.R
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch


@AndroidEntryPoint
class MovieListActivity : AppCompatActivity() {

    private val movieListViewModel by viewModels<MovieListViewModel>()

    private lateinit var movieListAdapter: MovieListAdapter

    private lateinit var progressBar: ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d("MAJAMA", "onCreate:")
        enableEdgeToEdge()
        setContentView(R.layout.activity_movie_list)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        progressBar = findViewById<ProgressBar>(R.id.progressbar)

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
                    when (it.status) {
                        RestClientResult.Status.SUCCESS -> {
                            progressBar.isVisible = false

                            Log.d("MAJAMA", "observeFlow: SUCCESS")
                            val searchList = it.data?.Search
                            if (!searchList.isNullOrEmpty()) {
                                setUpRecyclerView(searchList)
                            } else {
                                Toast.makeText(this@MovieListActivity, "No movie found", Toast.LENGTH_SHORT).show()
                            }
                        }

                        RestClientResult.Status.ERROR -> {
                            Log.d("MAJAMA", "observeFlow: ERROR")
                            progressBar.isVisible = false
                        }

                        RestClientResult.Status.LOADING -> {
                            Log.d("MAJAMA", "observeFlow: LOADING")
                            progressBar.isVisible = true
                        }

                        RestClientResult.Status.NONE -> {}
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