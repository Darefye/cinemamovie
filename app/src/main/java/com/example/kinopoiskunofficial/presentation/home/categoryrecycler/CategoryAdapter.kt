package com.example.kinopoiskunofficial.presentation.home.categoryrecycler

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.kinopoiskunofficial.CinemaViewModel
import com.example.kinopoiskunofficial.data.CategoriesFilms
import com.example.kinopoiskunofficial.databinding.ItemCategoryListBinding

class CategoryAdapter(
    private val maxListSize: Int,
    private val category: List<CinemaViewModel.Companion.HomeList>,
    private val clickNextButton: (category: CategoriesFilms) -> Unit,
    private val clickFilms: (filmId: Int) -> Unit
) : RecyclerView.Adapter<CategoryViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = CategoryViewHolder(
        ItemCategoryListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    )

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        holder.bindItem(
            maxListSize,
            category[position],
            { clickNextButton(it) },
            { clickFilms(it) }
        )
    }

    override fun getItemCount() = category.size
}