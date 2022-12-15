package com.example.kinopoiskunofficial.presentation.home.filmdetail.staffadapter

import android.annotation.SuppressLint
import androidx.recyclerview.widget.DiffUtil
import com.example.kinopoiskunofficial.data.staffbyfilmid.ResponseStaffByFilmId

class DiffStaff : DiffUtil.ItemCallback<ResponseStaffByFilmId>() {
    override fun areItemsTheSame(oldItem: ResponseStaffByFilmId, newItem: ResponseStaffByFilmId) =
        oldItem.staffId == newItem.staffId &&
                oldItem.description == newItem.description &&
                oldItem.professionText == newItem.professionText

    @SuppressLint("DiffUtilEquals")
    override fun areContentsTheSame(
        oldItem: ResponseStaffByFilmId,
        newItem: ResponseStaffByFilmId
    ) = oldItem == newItem
}