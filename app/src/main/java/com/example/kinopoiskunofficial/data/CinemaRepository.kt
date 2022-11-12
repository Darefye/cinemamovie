package com.example.kinopoiskunofficial.data

import com.example.kinopoiskunofficial.data.filmbyfilter.FilmByFilter
import com.example.kinopoiskunofficial.data.filmspremier.FilmPremier
import com.example.kinopoiskunofficial.entity.HomeItem
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

class CinemaRepository {
    private val retrofit: KinopoiskApi = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(MoshiConverterFactory.create())
        .build()
        .create(KinopoiskApi::class.java)

    suspend fun getFilmById(filmId: Int) = retrofit.getCurrentFilm(filmId)

    suspend fun getFilmsByFilter(
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
        return retrofit.getFilmsByFilter(
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
        ).items
    }

    suspend fun getFilmsTop(topType: String, page: Int): List<HomeItem> {
        return retrofit.getFilmsTop(type = topType, page = page).films
    }

    suspend fun getFilmsPremier(
        year: Int,
        month: String
    ): List<FilmPremier> {
        return retrofit.getPremier(year, month).items
    }

    companion object {
        private const val BASE_URL = "https://kinopoiskapiunofficial.tech/api/v2.2/"
    }
}