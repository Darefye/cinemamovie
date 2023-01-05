package com.example.kinopoiskunofficial.presentation.search.adapters.search

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import com.example.kinopoiskunofficial.databinding.ItemFilmographyFilmBinding
import com.example.kinopoiskunofficial.entity.HomeItem
import com.example.kinopoiskunofficial.loadImage

class SearchAdapter(
    private val onClick: (Int) -> Unit
) : PagingDataAdapter<HomeItem, SearchViewHolder>(SearchDiffUtil()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = SearchViewHolder(
        ItemFilmographyFilmBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    )

    override fun onBindViewHolder(holder: SearchViewHolder, position: Int) {
        val item = getItem(position)
        with(holder.binding) {
            item?.let {
                holder.binding.apply {
                    itemFilmographyImage.loadImage(item.posterUrlPreview)
                    itemFilmographyName.text = item.nameRu ?: "No name"
                    itemFilmographyGenre.text = item.genres.firstOrNull()?.genre ?: ""
                    itemFilmographyRating.text = item.rating
                    itemDe.setOnClickListener {
                        onClick(item.filmId)
                    }
                }
            }
        }

    }
}