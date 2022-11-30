package com.example.kinopoiskunofficial.presentation.filmdetail.galleryadapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.example.kinopoiskunofficial.data.filmgallery.ItemImageGallery
import com.example.kinopoiskunofficial.databinding.ItemGalleryFilmDetailBinding

class GalleryAdapter : ListAdapter<ItemImageGallery, GalleryViewHolder>(DiffGallery()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = GalleryViewHolder(
        ItemGalleryFilmDetailBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    )

    override fun onBindViewHolder(holder: GalleryViewHolder, position: Int) {
        holder.bindItem(getItem(position))
    }
}