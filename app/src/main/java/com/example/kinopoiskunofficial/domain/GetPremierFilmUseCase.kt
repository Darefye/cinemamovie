package com.example.kinopoiskunofficial.domain

import com.example.kinopoiskunofficial.data.CinemaRepository
import com.example.kinopoiskunofficial.entity.HomeItem

class GetPremierFilmUseCase(private val repository: CinemaRepository) {

    suspend fun executePremieres(year: Int, month: String): List<HomeItem> {
        return repository.getFilmsPremier(year, month)
    }
}