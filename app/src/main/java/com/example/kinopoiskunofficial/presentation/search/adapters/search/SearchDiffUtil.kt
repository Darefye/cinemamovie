package com.example.kinopoiskunofficial.presentation.search.adapters.search

import android.annotation.SuppressLint
import androidx.recyclerview.widget.DiffUtil
import com.example.kinopoiskunofficial.entity.HomeItem

class SearchDiffUtil : DiffUtil.ItemCallback<HomeItem>() {
    override fun areItemsTheSame(
        oldItem: HomeItem,
        newItem: HomeItem
    ) = oldItem.filmId == newItem.filmId

    @SuppressLint("DiffUtilEquals")
    override fun areContentsTheSame(
        oldItem: HomeItem,
        newItem: HomeItem
    ) = oldItem == newItem
}