package com.example.kinopoiskunofficial.presentation.filmdetail.galleryadapter

import androidx.recyclerview.widget.RecyclerView
import com.example.kinopoiskunofficial.data.filmgallery.ItemImageGallery
import com.example.kinopoiskunofficial.databinding.ItemGalleryFilmDetailBinding
import com.example.kinopoiskunofficial.loadImage

class GalleryViewHolder(
    private val binding: ItemGalleryFilmDetailBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun bindItem(item: ItemImageGallery) {
        binding.galleryImageFilmDetail.loadImage(item.previewUrl)
    }
}