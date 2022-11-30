package com.example.kinopoiskunofficial.domain

import com.example.kinopoiskunofficial.data.CinemaRepository
import com.example.kinopoiskunofficial.data.similarfilm.ResponseSimilarFilms
import javax.inject.Inject

class GetSimilarFilmsUseCase @Inject constructor(private val repository: CinemaRepository) {
    suspend fun executeSimilarFilms(filmId: Int): ResponseSimilarFilms {
        return repository.getSimilarByFilmId(filmId)
    }
}