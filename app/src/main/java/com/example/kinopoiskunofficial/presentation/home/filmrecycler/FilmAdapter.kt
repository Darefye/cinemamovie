package com.example.kinopoiskunofficial.presentation.home.filmrecycler

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.example.kinopoiskunofficial.databinding.ItemFilmBinding
import com.example.kinopoiskunofficial.entity.HomeItem

class FilmAdapter(
    private val maxListSize: Int,
    private val clickNextButton: () -> Unit,
    private val clickFilms: (filmId: Int) -> Unit
) : ListAdapter<HomeItem, FilmViewHolder>(DiffFilm()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = FilmViewHolder(
        ItemFilmBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    )

    override fun onBindViewHolder(holder: FilmViewHolder, position: Int) {
        if (position == maxListSize - 1) {
            holder.bindNextShow { clickNextButton() }
        } else {
            holder.bindItem(getItem(position)) { clickFilms(it) }
        }
    }
}