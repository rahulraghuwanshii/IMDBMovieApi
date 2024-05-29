package com.rahulraghuwanshi.movieimdb.movie_list

import android.content.Intent
import android.os.Bundle
import android.widget.ProgressBar
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.isVisible
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.rahulraghuwanshi.imdb_api.model.Search
import com.rahulraghuwanshi.imdb_api.util.RestClientResult
import com.rahulraghuwanshi.movieimdb.R
import com.rahulraghuwanshi.movieimdb.movie_detail.MovieDetailActivity
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
        enableEdgeToEdge()
        setContentView(R.layout.activity_movie_list)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }


        val movieName = intent.getStringExtra("movieName")
        setUpUi(movieName)
        if (movieName != null) {
            movieListViewModel.fetchMovieList(movieName)
        } else {
            // no movie name found
            Toast.makeText(this, "NO movie name found", Toast.LENGTH_SHORT).show()
        }

        collectFlow()
    }

    private fun setUpUi(movieName: String?) {
        progressBar = findViewById(R.id.progressbar)

        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        toolbar.setNavigationOnClickListener {
            onBackPressed()
        }

        toolbar.setTitle("Search Result for: $movieName")
    }

    private fun collectFlow() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                movieListViewModel.movieListFlow.collectLatest {
                    when (it.status) {
                        RestClientResult.Status.SUCCESS -> {
                            progressBar.isVisible = false

                            val searchList = it.data?.Search
                            if (!searchList.isNullOrEmpty()) {
                                setUpRecyclerView(searchList)
                            } else {
                                Toast.makeText(
                                    this@MovieListActivity,
                                    "No movie found",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        }

                        RestClientResult.Status.ERROR -> {
                            progressBar.isVisible = false
                            Toast.makeText(this@MovieListActivity,"Something went wrong!!",Toast.LENGTH_SHORT).show()
                        }

                        RestClientResult.Status.LOADING -> {
                            progressBar.isVisible = true
                        }

                        RestClientResult.Status.NONE -> {}
                    }
                }
            }
        }
    }


    private fun setUpRecyclerView(list: List<Search?>) {

        movieListAdapter = MovieListAdapter(
            list,
            onClick = {
                openMovieDetailActivity(it)
            }
        )

        val rvMovieList = findViewById<RecyclerView>(R.id.rvMovieList)
        rvMovieList.adapter = movieListAdapter
        rvMovieList.layoutManager = LinearLayoutManager(this)

    }

    private fun openMovieDetailActivity(imdbId: String?) {
        if (imdbId != null) {

            val intent = Intent(this, MovieDetailActivity::class.java)
            intent.putExtra("imdbId", imdbId)
            startActivity(intent)
        } else {
            Toast.makeText(this, "ImdbId is required", Toast.LENGTH_SHORT).show()
        }
    }
}