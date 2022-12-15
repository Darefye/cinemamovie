package com.example.kinopoiskunofficial.domain

import com.example.kinopoiskunofficial.data.CinemaRepository
import com.example.kinopoiskunofficial.data.FilmFilterParams
import com.example.kinopoiskunofficial.data.ParamsFilterFilm
import com.example.kinopoiskunofficial.data.filmbyfilter.FilmByFilter
import com.example.kinopoiskunofficial.data.filmbyfilter.ResponseByFilter
import javax.inject.Inject

class GetFilmListUseCase @Inject constructor(private val repository: CinemaRepository) {

    suspend fun executeFilmsByFilter(
        filters: ParamsFilterFilm,
        page: Int
    ): List<FilmByFilter> {
        return repository.getFilmsByFilter(
            filters = filters,
            page = page
        ).items
    }

    suspend fun executeFilmsByFilterCount(
        filters: ParamsFilterFilm,
        page: Int
    ): ResponseByFilter {
        return repository.getFilmsByFilter(filters, page)
    }
}