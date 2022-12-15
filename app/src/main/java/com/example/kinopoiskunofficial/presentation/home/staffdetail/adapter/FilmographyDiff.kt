package com.example.kinopoiskunofficial.presentation.home.staffdetail.adapter

import android.annotation.SuppressLint
import androidx.recyclerview.widget.DiffUtil
import com.example.kinopoiskunofficial.data.staffbyid.StaffsFilms

class FilmographyDiff : DiffUtil.ItemCallback<StaffsFilms>() {
    override fun areItemsTheSame(oldItem: StaffsFilms, newItem: StaffsFilms) =
        oldItem.filmId == newItem.filmId

    @SuppressLint("DiffUtilEquals")
    override fun areContentsTheSame(
        oldItem: StaffsFilms,
        newItem: StaffsFilms
    ) = oldItem == newItem
}