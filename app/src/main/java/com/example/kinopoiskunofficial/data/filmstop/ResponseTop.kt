package ru.zhdanon.skillcinema.data.filmstop

import com.example.kinopoiskunofficial.data.filmstop.FilmTop
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ResponseTop(
    @Json(name = "films") val films: List<FilmTop>,
    @Json(name = "pagesCount") val pagesCount: Int
)