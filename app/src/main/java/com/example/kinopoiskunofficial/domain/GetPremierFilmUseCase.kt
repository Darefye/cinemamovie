package com.example.kinopoiskunofficial.domain

import com.example.kinopoiskunofficial.data.CinemaRepository
import com.example.kinopoiskunofficial.entity.HomeItem
import javax.inject.Inject

class GetPremierFilmUseCase @Inject constructor(private val repository: CinemaRepository) {

    suspend fun executePremieres(year: Int, month: String): List<HomeItem> {
        return repository.getFilmsPremier(year, month)
    }
}