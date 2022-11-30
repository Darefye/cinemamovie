package com.example.kinopoiskunofficial.presentation.gallery.pageradapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import com.example.kinopoiskunofficial.data.filmgallery.ItemImageGallery
import com.example.kinopoiskunofficial.databinding.ItemGalleryFullscreenBinding
import com.example.kinopoiskunofficial.loadImage
import com.example.kinopoiskunofficial.presentation.gallery.recycleradapter.GalleryFullDiffUtil

class GalleryFullscreenAdapter :
    PagingDataAdapter<ItemImageGallery, GalleryFullscreenViewHolder>(GalleryFullDiffUtil()) {
    override fun onBindViewHolder(holder: GalleryFullscreenViewHolder, position: Int) {
        val item = getItem(position)
        with(holder.binding) {
            item?.let {
                galleryImageFullscreen.loadImage(it.previewUrl)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GalleryFullscreenViewHolder {
        val binding =
            ItemGalleryFullscreenBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return GalleryFullscreenViewHolder(binding)
    }
}