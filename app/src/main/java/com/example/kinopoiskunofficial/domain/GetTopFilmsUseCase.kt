package com.example.kinopoiskunofficial.domain

import com.example.kinopoiskunofficial.data.CinemaRepository
import com.example.kinopoiskunofficial.entity.HomeItem


class GetTopFilmsUseCase(private val cinemaRepository: CinemaRepository) {

    suspend fun executeTopFilms(topType: String, page: Int): List<HomeItem> {
        return cinemaRepository.getFilmsTop(topType, page)
    }
}