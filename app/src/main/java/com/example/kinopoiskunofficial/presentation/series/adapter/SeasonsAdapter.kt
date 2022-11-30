package com.example.kinopoiskunofficial.presentation.series.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.example.kinopoiskunofficial.data.seasons.Episode
import com.example.kinopoiskunofficial.databinding.ItemSeriesEpisodeBinding

class SeasonsAdapter : ListAdapter<Episode, SeasonsVewHolder>(SeasonsDiff()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = SeasonsVewHolder(
        ItemSeriesEpisodeBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    )

    override fun onBindViewHolder(holder: SeasonsVewHolder, position: Int) {
        holder.bindItem(getItem(position))
    }
}