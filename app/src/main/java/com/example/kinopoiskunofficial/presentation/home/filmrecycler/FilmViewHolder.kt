package com.example.kinopoiskunofficial.presentation.home.filmrecycler

import androidx.core.view.isInvisible
import androidx.recyclerview.widget.RecyclerView
import com.example.kinopoiskunofficial.databinding.ItemFilmBinding
import com.example.kinopoiskunofficial.entity.HomeItem
import com.example.kinopoiskunofficial.loadImage

class FilmViewHolder(private val binding: ItemFilmBinding) : RecyclerView.ViewHolder(binding.root) {

    fun bindNextShow(clickNextButton: () -> Unit) {
        binding.apply {
            showAll.isInvisible = false
            itemFilm.isInvisible = true
        }
        binding.btnShowAll.setOnClickListener { clickNextButton() }
    }

    fun bindItem(
        item: HomeItem,
        clickFilms: (filmId: Int) -> Unit
    ) {
        binding.apply {

            itemFilmPreview.loadImage(item.posterUrlPreview)

            showAll.isInvisible = true
            itemFilm.isInvisible = false
            itemNameFilm.text = item.nameRu
            itemGenreFilm.text = item.genres.firstOrNull()?.genre ?: ""
            if (item.rating != null) {
                itemFilmRating.isInvisible = false
                itemFilmRating.text = item.rating
            } else itemFilmRating.isInvisible = true
        }
        binding.itemFilm.setOnClickListener { clickFilms(item.filmId) }
    }
}