package com.rahulraghuwanshi.movieimdb.movie

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.rahulraghuwanshi.imdb_api.model.Movie
import com.rahulraghuwanshi.movieimdb.R

class MovieListAdapter(
    private val list: List<Movie?>
) : RecyclerView.Adapter<MovieListAdapter.MovieListViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieListViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_movie, parent, false)

        return MovieListViewHolder(view)
    }

    override fun getItemCount() = list.size

    override fun onBindViewHolder(holder: MovieListViewHolder, position: Int) {
        val movie = list[position]

        holder.txtImdbId.text = "Imdb ID: ${movie?.imdbID}"
        holder.txtImdbRating.text = "Imdb Rating: ${movie?.imdbRating}"
        holder.txtImdbDirectorName.text = "Director: ${movie?.Director}"
    }


    class MovieListViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        lateinit var txtImdbId: TextView
        lateinit var txtImdbRating: TextView
        lateinit var txtImdbDirectorName: TextView

        init {
            // Define click listener for the ViewHolder's View
            txtImdbId = view.findViewById(R.id.txtImdbID)
            txtImdbRating = view.findViewById(R.id.txtImdbRating)
            txtImdbDirectorName = view.findViewById(R.id.txtDirectorName)
        }
    }
}