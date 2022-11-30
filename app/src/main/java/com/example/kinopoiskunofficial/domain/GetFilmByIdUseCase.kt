package com.example.kinopoiskunofficial.domain

import com.example.kinopoiskunofficial.data.CinemaRepository
import com.example.kinopoiskunofficial.data.filmbyid.ResponseCurrentFilm
import javax.inject.Inject


class GetFilmByIdUseCase @Inject constructor(private val repository: CinemaRepository) {
    suspend fun executeFilmById(filmId: Int): ResponseCurrentFilm {
        return repository.getFilmById(filmId)
    }
}