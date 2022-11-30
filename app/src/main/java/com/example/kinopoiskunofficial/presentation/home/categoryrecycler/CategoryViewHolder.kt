package com.example.kinopoiskunofficial.presentation.home.categoryrecycler

import androidx.core.view.isInvisible
import androidx.recyclerview.widget.RecyclerView
import com.example.kinopoiskunofficial.CinemaViewModel
import com.example.kinopoiskunofficial.data.CategoriesFilms
import com.example.kinopoiskunofficial.databinding.ItemCategoryListBinding
import com.example.kinopoiskunofficial.presentation.home.filmrecycler.FilmAdapter

class CategoryViewHolder(
    private val binding: ItemCategoryListBinding
) :
    RecyclerView.ViewHolder(binding.root) {

    fun bindItem(
        maxListSize: Int,
        item: CinemaViewModel.Companion.HomeList,
        clickNextButton: (category: CategoriesFilms) -> Unit,
        clickFilms: (filmId: Int) -> Unit
    ) {
        val adapter =
            FilmAdapter(maxListSize, { clickNextButton(item.category) }, { clickFilms(it) })
        adapter.submitList(item.filmList)

        binding.apply {
            itemCategory.text = item.category.text
            filmList.adapter = adapter
        }
        binding.tvBtnAll.apply {
            this.isInvisible = item.filmList.size < maxListSize
            this.setOnClickListener { clickNextButton(item.category) }
        }
    }
}