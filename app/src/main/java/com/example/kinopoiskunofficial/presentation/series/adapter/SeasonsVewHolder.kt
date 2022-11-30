package com.example.kinopoiskunofficial.presentation.series.adapter

import androidx.recyclerview.widget.RecyclerView
import com.example.kinopoiskunofficial.R
import com.example.kinopoiskunofficial.data.seasons.Episode
import com.example.kinopoiskunofficial.databinding.ItemSeriesEpisodeBinding

class SeasonsVewHolder(
    private val binding: ItemSeriesEpisodeBinding
) : RecyclerView.ViewHolder(binding.root) {

    private var isClicked = false

    fun bindItem(item: Episode) {
        val text = binding.root.resources.getString(
            R.string.season_episode_name, item.episodeNumber, item.nameRu ?: item.nameEn
        )
        binding.episodeNumberName.text = text
        binding.episodeDate.text = formatReleaseDate(item.releaseDate.toString())
        binding.episodeDescription.text = item.synopsis
        binding.episodeBtn.setOnClickListener {

        }
    }

    private fun formatReleaseDate(date: String): String {
        val temp1 = date.split("-").reversed().toMutableList()
        temp1[1] = when(temp1[1]) {
            "01" -> "января"
            "02" -> "февраля"
            "03" -> "марта"
            "04" -> "апреля"
            "05" -> "мая"
            "06" -> "июня"
            "07" -> "июля"
            "08" -> "августа"
            "09" -> "сентября"
            "10" -> "октября"
            "11" -> "ноября"
            "12" -> "декабря"
            else -> ""
        }
        return temp1.joinToString(" ")
    }
}