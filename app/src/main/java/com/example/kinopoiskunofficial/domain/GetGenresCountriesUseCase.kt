package com.example.kinopoiskunofficial.domain

import com.example.kinopoiskunofficial.data.CinemaRepository
import com.example.kinopoiskunofficial.data.filmbyfilter.ResponseGenresCountries
import javax.inject.Inject

class GetGenresCountriesUseCase @Inject constructor(private val repository: CinemaRepository) {
    suspend fun executeGenresCountries(): ResponseGenresCountries {
        return repository.getGenresCountries()
    }
}