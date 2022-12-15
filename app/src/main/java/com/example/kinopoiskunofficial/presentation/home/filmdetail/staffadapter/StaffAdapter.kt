package com.example.kinopoiskunofficial.presentation.home.filmdetail.staffadapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.example.kinopoiskunofficial.data.staffbyfilmid.ResponseStaffByFilmId
import com.example.kinopoiskunofficial.databinding.ItemStaffDetailFilmBinding

class StaffAdapter(
    private val clickActor: (filmId: ResponseStaffByFilmId) -> Unit
) : ListAdapter<ResponseStaffByFilmId, StaffViewHolder>(DiffStaff()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = StaffViewHolder(
        ItemStaffDetailFilmBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    )

    override fun onBindViewHolder(holder: StaffViewHolder, position: Int) {
        holder.bindItem(getItem(position)) { clickActor(getItem(position)) }
    }
}