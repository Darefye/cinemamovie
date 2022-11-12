package com.example.kinopoiskunofficial.entity

import com.example.kinopoiskunofficial.data.filmbyfilter.Genre


interface HomeItem {
    val filmId: Int
    val posterUrlPreview: String
    val nameRu: String
    val rating: String?
    val genres: List<Genre>
}