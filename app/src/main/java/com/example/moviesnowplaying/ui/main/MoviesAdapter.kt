package com.example.moviesnowplaying.ui.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.moviesnowplaying.data.models.MovieShort
import com.example.moviesnowplaying.databinding.ItemMovieShortBinding

class MoviesAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val _items = mutableListOf<MovieShort>()

    fun update(items: List<MovieShort>) {
        _items.clear()
        _items.addAll(items)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val binding = ItemMovieShortBinding.inflate(
            LayoutInflater.from(viewGroup.context),
            viewGroup,
            false
        )
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int = _items.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as ViewHolder).bind(_items[position])
    }

    class ViewHolder(val binding: ItemMovieShortBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(movieShort: MovieShort) {
            with(binding) {

                // TODO: load with Glide - binding.imgMoviePoster

                txtTitle.text = movieShort.title
                txtDate.text = movieShort.releaseDate
                txtOverview.text = movieShort.overview
            }
        }
    }
}