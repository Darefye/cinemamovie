package com.example.kinopoiskunofficial.presentation.gallery.recycleradapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import com.example.kinopoiskunofficial.data.filmgallery.ItemImageGallery
import com.example.kinopoiskunofficial.databinding.ItemGalleryImageBinding
import com.example.kinopoiskunofficial.loadImage

class GalleryFullAdapter(
    private val onClick: (Int) -> Unit
) : PagingDataAdapter<ItemImageGallery, GalleryFullViewHolder>(GalleryFullDiffUtil()) {
    override fun onBindViewHolder(holder: GalleryFullViewHolder, position: Int) {
        val item = getItem(position)
        with(holder.binding) {
            item?.let {
                galleryImage.loadImage(it.previewUrl)
            }
            holder.binding.galleryImage.setOnClickListener { onClick(position) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GalleryFullViewHolder {
        val binding =
            ItemGalleryImageBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return GalleryFullViewHolder(binding)
    }
}