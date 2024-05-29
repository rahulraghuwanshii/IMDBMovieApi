package com.rahulraghuwanshi.movieimdb.movie_detail

import android.media.Image
import android.os.Bundle
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.isVisible
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.bumptech.glide.Glide
import com.rahulraghuwanshi.imdb_api.model.MovieDetail
import com.rahulraghuwanshi.imdb_api.util.RestClientResult
import com.rahulraghuwanshi.movieimdb.R
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MovieDetailActivity : AppCompatActivity() {

    private val movieDetailViewModel by viewModels<MovieDetailViewModel>()

    private lateinit var progressBar: ProgressBar
    private lateinit var container: ConstraintLayout
    private lateinit var txtImdbId: TextView
    private lateinit var txtImdbTitle: TextView
    private lateinit var txtImdbYear: TextView
    private lateinit var imgPoster: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_movie_detail)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val imdbId = intent.getStringExtra("imdbId")
        setUpUi(imdbId)

        if (imdbId != null) {
            movieDetailViewModel.fetchMovieDetails(imdbId)
        } else {
            Toast.makeText(this, "Imdb id not found", Toast.LENGTH_SHORT).show()
        }

        collectFlow()
    }

    private fun setUpUi(imdbId: String?) {
        progressBar = findViewById(R.id.progressbar)
        container = findViewById(R.id.container)
        txtImdbId = findViewById(R.id.txtImdbID)
        txtImdbTitle = findViewById(R.id.txtImdbTitle)
        txtImdbYear = findViewById(R.id.txtYear)
        imgPoster = findViewById(R.id.imgMovie)

        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        toolbar.setNavigationOnClickListener {
            onBackPressed()
        }

        toolbar.setTitle("ImdbId: $imdbId")
    }

    private fun collectFlow() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                movieDetailViewModel.movieDetailFlow.collectLatest {
                    when (it.status) {
                        RestClientResult.Status.SUCCESS -> {
                            progressBar.isVisible = false
                            container.isVisible = true
                            setData(it.data)
                        }

                        RestClientResult.Status.ERROR -> {
                            progressBar.isVisible = false
                            Toast.makeText(
                                this@MovieDetailActivity,
                                "Something went wrong!!",
                                Toast.LENGTH_SHORT
                            ).show()
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

    private fun setData(movieDetails: MovieDetail?) {

        txtImdbId.text = "Imdb Id: ${movieDetails?.imdbID}"
        txtImdbTitle.text = "Imdb Title: ${movieDetails?.Title}"
        txtImdbYear.text = "Imdb Year: ${movieDetails?.Year}"

        Glide.with(this).load(movieDetails?.Poster).into(imgPoster)

    }
}