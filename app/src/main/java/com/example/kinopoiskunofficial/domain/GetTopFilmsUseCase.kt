package com.example.kinopoiskunofficial.domain

import com.example.kinopoiskunofficial.data.CinemaRepository
import com.example.kinopoiskunofficial.entity.HomeItem
import javax.inject.Inject

class GetTopFilmsUseCase @Inject constructor(private val repository: CinemaRepository) {
    suspend fun executeTopFilms(topType: String, page: Int): List<HomeItem> {
        return repository.getFilmsTop(topType, page)
    }
}