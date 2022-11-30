package com.example.kinopoiskunofficial.domain

import com.example.kinopoiskunofficial.data.CinemaRepository
import com.example.kinopoiskunofficial.data.FilmFilterParams
import com.example.kinopoiskunofficial.data.filmbyfilter.FilmByFilter
import javax.inject.Inject

class GetFilmListUseCase @Inject constructor(private val repository: CinemaRepository) {

    suspend fun executeFilmsByFilter(
        filters: FilmFilterParams,
        page: Int
    ): List<FilmByFilter> {
        return repository.getFilmsByFilter(
            filters = filters,
            page = page
        )
    }
}