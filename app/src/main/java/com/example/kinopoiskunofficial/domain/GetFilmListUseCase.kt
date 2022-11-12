package com.example.kinopoiskunofficial.domain

import com.example.kinopoiskunofficial.data.CinemaRepository
import com.example.kinopoiskunofficial.data.filmbyfilter.FilmByFilter

class GetFilmListUseCase(private val repository: CinemaRepository) {

    suspend fun executeFilmsByFilter(
        countries: String = "",
        genres: String = "",
        order: String = "YEAR",
        type: String = "",
        ratingFrom: Int = 0,
        ratingTo: Int = 10,
        yearFrom: Int = 1000,
        yearTo: Int = 3000,
        imdbId: String? = null,
        keyword: String = "",
        page: Int
    ): List<FilmByFilter> {
        return repository.getFilmsByFilter(
            countries = countries,
            genres = genres,
            order = order,
            type = type,
            ratingFrom = ratingFrom,
            ratingTo = ratingTo,
            yearFrom = yearFrom,
            yearTo = yearTo,
            imdbId = imdbId,
            keyword = keyword,
            page = page
        )
    }
}