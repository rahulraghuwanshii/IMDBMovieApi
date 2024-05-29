package com.rahulraghuwanshi.movieimdb.home

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.rahulraghuwanshi.movieimdb.R
import com.rahulraghuwanshi.movieimdb.movie.MovieListActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }


        val etSearch = findViewById<EditText>(R.id.etSearch)

        findViewById<Button>(R.id.btnSearch).setOnClickListener {
            val searchText = etSearch.text.trim().toString()

            if (searchText.isNotEmpty()) {
                openMovieListScreen(searchText)
            } else {
                Toast.makeText(this, "Please Enter Movie Name", Toast.LENGTH_SHORT).show()
            }

        }
    }

    private fun openMovieListScreen(movieName: String) {
        val intent = Intent(this, MovieListActivity::class.java)
        intent.putExtra("movieName", movieName)

        startActivity(intent)

    }
}