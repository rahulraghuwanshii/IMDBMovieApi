package com.rahulraghuwanshi.movieimdb.movie_list

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.rahulraghuwanshi.imdb_api.model.Search
import com.rahulraghuwanshi.movieimdb.R

class MovieListAdapter(
    private val list: List<Search?>,
    private val onClick: (String?) -> Unit
) : RecyclerView.Adapter<MovieListAdapter.MovieListViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieListViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_movie, parent, false)

        return MovieListViewHolder(view)
    }

    override fun getItemCount() = list.size

    override fun onBindViewHolder(holder: MovieListViewHolder, position: Int) {
        val movie = list[position]

        holder.txtImdbId.text = "ID: ${movie?.imdbID}"
        holder.txtImdbTitle.text = "Title: ${movie?.Title}"
        holder.txtImdbYear.text = "Year: ${movie?.Year}"

        Glide.with(holder.itemView.context).load(movie?.Poster).into(holder.imgMovie)

        holder.itemView.setOnClickListener {
            onClick(movie?.imdbID)
        }
    }


    class MovieListViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        lateinit var txtImdbId: TextView
        lateinit var txtImdbTitle: TextView
        lateinit var txtImdbYear: TextView
        lateinit var imgMovie: ImageView

        init {
            // Define click listener for the ViewHolder's View
            txtImdbId = view.findViewById(R.id.txtImdbID)
            txtImdbTitle = view.findViewById(R.id.txtImdbTitle)
            txtImdbYear = view.findViewById(R.id.txtYear)
            imgMovie = view.findViewById(R.id.imgMovie)
        }
    }
}