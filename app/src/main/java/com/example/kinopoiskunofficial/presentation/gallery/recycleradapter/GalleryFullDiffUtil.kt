package com.example.kinopoiskunofficial.presentation.gallery.recycleradapter

import android.annotation.SuppressLint
import androidx.recyclerview.widget.DiffUtil
import com.example.kinopoiskunofficial.data.filmgallery.ItemImageGallery

class GalleryFullDiffUtil : DiffUtil.ItemCallback<ItemImageGallery>() {
    override fun areItemsTheSame(
        oldItem: ItemImageGallery,
        newItem: ItemImageGallery
    ) = oldItem.previewUrl == newItem.previewUrl

    @SuppressLint("DiffUtilEquals")
    override fun areContentsTheSame(
        oldItem: ItemImageGallery,
        newItem: ItemImageGallery
    ) = oldItem == newItem
}