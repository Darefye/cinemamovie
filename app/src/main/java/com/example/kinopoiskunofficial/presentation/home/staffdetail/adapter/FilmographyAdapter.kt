package com.example.kinopoiskunofficial.presentation.home.staffdetail.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.example.kinopoiskunofficial.data.PROFESSIONS
import com.example.kinopoiskunofficial.data.staffbyid.StaffsFilms
import com.example.kinopoiskunofficial.databinding.ItemFilmographyFilmBinding
import com.example.kinopoiskunofficial.loadImage


//PagingDataAdapter
class FilmographyAdapter(
    private val onFilmClick: (Int) -> Unit
) : ListAdapter<StaffsFilms, FilmographyViewHolder>(FilmographyDiff()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = FilmographyViewHolder(
        ItemFilmographyFilmBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    )

    override fun onBindViewHolder(holder: FilmographyViewHolder, position: Int) {
        val item = getItem(position)
        holder.binding.apply {
            itemFilmographyImage.loadImage(item.posterUrlPreview)
            itemFilmographyName.text = item.nameRu ?: item.nameEn
            itemFilmographyGenre.text = PROFESSIONS[item.professionKey]
            itemFilmographyRating.text = item.rating
        }
        holder.binding.root.setOnClickListener { onFilmClick(item.filmId) }
    }
}