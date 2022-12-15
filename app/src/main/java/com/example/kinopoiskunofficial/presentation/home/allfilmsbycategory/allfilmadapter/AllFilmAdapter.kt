package com.example.kinopoiskunofficial.presentation.home.allfilmsbycategory.allfilmadapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import com.example.kinopoiskunofficial.databinding.ItemFilmBinding
import com.example.kinopoiskunofficial.entity.HomeItem
import com.example.kinopoiskunofficial.loadImage

class AllFilmAdapter(
    private val onClick: (Int) -> Unit
) : PagingDataAdapter<HomeItem, AllFilmViewHolder>(AllFilmsDiffUtil()) {
    override fun onBindViewHolder(holder: AllFilmViewHolder, position: Int) {
        val item = getItem(position)

        with(holder.binding) {
            item?.let {
                holder.binding.apply {
                    itemFilmPreview.loadImage(item.posterUrlPreview)
                    itemNameFilm.text = item.nameRu
                    itemFilmRating.text = item.rating
                    itemGenreFilm.text = item.genres.firstOrNull()?.genre ?: ""

                    itemFilmPreview.setOnClickListener {
                        onClick(item.filmId)
                    }
                }
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AllFilmViewHolder  {
        val binding =
            ItemFilmBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return AllFilmViewHolder(binding)
    }
}