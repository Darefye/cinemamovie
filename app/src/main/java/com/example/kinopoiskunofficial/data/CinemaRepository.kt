package com.example.kinopoiskunofficial.data

import com.example.kinopoiskunofficial.data.filmbyfilter.FilmByFilter
import com.example.kinopoiskunofficial.data.filmbyfilter.ResponseByFilter
import com.example.kinopoiskunofficial.data.filmbyfilter.ResponseGenresCountries
import com.example.kinopoiskunofficial.data.filmspremier.FilmPremier
import com.example.kinopoiskunofficial.entity.HomeItem
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Inject

class CinemaRepository @Inject constructor() {
    private val retrofit: KinopoiskApi =
        Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(MoshiConverterFactory.create())
            .build().create(KinopoiskApi::class.java)

    suspend fun getFilmById(filmId: Int) = retrofit.getCurrentFilm(filmId)

    suspend fun getSeasonsById(seriesId: Int) = retrofit.getSeasons(seriesId)

    suspend fun getActorsByFilmId(filmId: Int) = retrofit.getActors(filmId)

    suspend fun getGalleryByFilmId(filmId: Int, type: String, page: Int) =
        retrofit.getFilmImages(filmId, type, page)

    suspend fun getSimilarByFilmId(filmId: Int) = retrofit.getSimilarFilms(filmId)

    suspend fun getStaffById(staffId: Int) = retrofit.getStaff(staffId)

    suspend fun getFilmsTop(topType: String, page: Int): List<HomeItem> {
        return retrofit.getFilmsTop(type = topType, page = page).films
    }

    suspend fun getFilmsPremier(year: Int, month: String): List<FilmPremier> {
        return retrofit.getPremier(year, month).items
    }

    // FragmentSearch
    suspend fun getFilmsByFilter(filters: ParamsFilterFilm, page: Int): ResponseByFilter {
        return retrofit.getFilmsByFilter(
            countries = if (filters.countries.isNotEmpty()) filters.countries.keys.first().toString() else "",
            genres = if (filters.genres.isNotEmpty()) filters.genres.keys.first().toString() else "",
            order = filters.order,
            type = filters.type,
            ratingFrom = filters.ratingFrom,
            ratingTo = filters.ratingTo,
            yearFrom = filters.yearFrom,
            yearTo = filters.yearTo,
            imdbId = filters.imdbId,
            keyword = filters.keyword,
            page = page
        )
    }

    suspend fun getGenresCountries(): ResponseGenresCountries {
        return retrofit.getGenresCountries()
    }

    companion object {
        private const val BASE_URL = "https://kinopoiskapiunofficial.tech/api/"
    }
}