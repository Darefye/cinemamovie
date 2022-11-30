package com.example.kinopoiskunofficial.domain

import com.example.kinopoiskunofficial.data.CinemaRepository
import javax.inject.Inject

class GetSeasonsUseCase @Inject constructor(private val repository: CinemaRepository) {
    suspend fun executeSeasons(seriesId: Int) = repository.getSeasonsById(seriesId)
}