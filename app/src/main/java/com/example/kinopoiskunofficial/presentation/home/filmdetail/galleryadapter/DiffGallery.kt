package com.example.kinopoiskunofficial.presentation.home.filmdetail.galleryadapter

import android.annotation.SuppressLint
import androidx.recyclerview.widget.DiffUtil
import com.example.kinopoiskunofficial.data.filmgallery.ItemImageGallery

class DiffGallery : DiffUtil.ItemCallback<ItemImageGallery>() {
    override fun areItemsTheSame(oldItem: ItemImageGallery, newItem: ItemImageGallery) =
        oldItem.previewUrl == newItem.previewUrl

    @SuppressLint("DiffUtilEquals")
    override fun areContentsTheSame(
        oldItem: ItemImageGallery,
        newItem: ItemImageGallery
    ) = oldItem == newItem
}