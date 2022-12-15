package com.example.kinopoiskunofficial.presentation.home.series.adapter

import android.annotation.SuppressLint
import androidx.recyclerview.widget.DiffUtil
import com.example.kinopoiskunofficial.data.seasons.Episode

class SeasonsDiff : DiffUtil.ItemCallback<Episode>() {
    override fun areItemsTheSame(oldItem: Episode, newItem: Episode) =
        oldItem.episodeNumber == newItem.episodeNumber

    @SuppressLint("DiffUtilEquals")
    override fun areContentsTheSame(
        oldItem: Episode,
        newItem: Episode
    ) = oldItem == newItem
}