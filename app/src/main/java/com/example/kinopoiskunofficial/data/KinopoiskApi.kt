package com.example.kinopoiskunofficial.data

import com.example.kinopoiskunofficial.data.filmbyfilter.ResponseByFilter
import com.example.kinopoiskunofficial.data.filmbyid.ResponseCurrentFilm
import com.example.kinopoiskunofficial.data.filmgallery.ResponseFilmGallery
import com.example.kinopoiskunofficial.data.filmspremier.ResponsePremier
import com.example.kinopoiskunofficial.data.filmstop.ResponseTop
import com.example.kinopoiskunofficial.data.seasons.ResponseSeasons
import com.example.kinopoiskunofficial.data.similarfilm.ResponseSimilarFilms
import com.example.kinopoiskunofficial.data.staffbyfilmid.ResponseStaffByFilmId
import com.example.kinopoiskunofficial.data.staffbyid.ResponseStaffById
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

interface KinopoiskApi {

    @Headers("X-API-KEY: $API_KEY")
    @GET("v2.2/films/{id}")
    suspend fun getCurrentFilm(
        @Path("id") id: Int
    ): ResponseCurrentFilm

    @Headers("X-API-KEY: $API_KEY")
    @GET("v1/staff")
    suspend fun getActors(
        @Query("filmId") filmId: Int
    ): List<ResponseStaffByFilmId>

    @Headers("X-API-KEY: $API_KEY")
    @GET("v2.2/films/{id}/images")
    suspend fun getFilmImages(
        @Path("id") id: Int,
        @Query("type") type: String = "STILL",
        @Query("page") page: Int
    ): ResponseFilmGallery

    @Headers("X-API-KEY: $API_KEY")
    @GET("v2.2/films/{id}/seasons")
    suspend fun getSeasons(
        @Path("id") id: Int
    ): ResponseSeasons

    @Headers("X-API-KEY: $API_KEY")
    @GET("v2.2/films/{id}/similars")
    suspend fun getSimilarFilms(
        @Path("id") id: Int
    ): ResponseSimilarFilms

    @Headers("X-API-KEY: $API_KEY")
    @GET("v1/staff/{id}")
    suspend fun getStaff(
        @Path("id") id: Int
    ): ResponseStaffById

    @Headers("X-API-KEY: $API_KEY")
    @GET("v2.2/films/top")
    suspend fun getFilmsTop(
        @Query("type") type: String,
        @Query("page") page: Int
    ): ResponseTop

    @Headers("X-API-KEY: $API_KEY")
    @GET("v2.2/films/premieres")
    suspend fun getPremier(
        @Query("year") year: Int,
        @Query("month") month: String
    ): ResponsePremier

    @Headers("X-API-KEY: $API_KEY")
    @GET("v2.2/films/")
    suspend fun getFilmsByFilter(
        @Query("countries") countries: String,
        @Query("genres") genres: String,
        @Query("order") order: String,
        @Query("type") type: String,
        @Query("ratingFrom") ratingFrom: Int,
        @Query("ratingTo") ratingTo: Int,
        @Query("yearFrom") yearFrom: Int,
        @Query("yearTo") yearTo: Int,
        @Query("imdbId") imdbId: String?,
        @Query("keyword") keyword: String,
        @Query("page") page: Int
    ): ResponseByFilter

    companion object {
        private const val API_KEY = "028062cb-4a58-42ab-be58-a93fdbe81036"
    }
}