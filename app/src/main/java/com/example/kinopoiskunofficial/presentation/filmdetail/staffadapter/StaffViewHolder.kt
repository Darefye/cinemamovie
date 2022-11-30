package com.example.kinopoiskunofficial.presentation.filmdetail.staffadapter

import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.kinopoiskunofficial.R
import com.example.kinopoiskunofficial.data.staffbyfilmid.ResponseStaffByFilmId
import com.example.kinopoiskunofficial.databinding.ItemStaffDetailFilmBinding
import com.example.kinopoiskunofficial.loadImage

class StaffViewHolder(
    private val binding: ItemStaffDetailFilmBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun bindItem(item: ResponseStaffByFilmId, clickActor: (actor: ResponseStaffByFilmId) -> Unit) {
        binding.apply {
            actorAvatarFilmDetail.loadImage(item.posterUrl)
            actorNameFilmDetail.text = item.nameRu ?: item.nameEn ?: "Не указан"
            actorRoleFilmDetail.text = item.description ?: "Не указан"

        }
        binding.root.setOnClickListener { clickActor(item) }
    }
}