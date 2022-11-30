package com.example.kinopoiskunofficial.domain

import com.example.kinopoiskunofficial.data.CinemaRepository
import com.example.kinopoiskunofficial.data.staffbyfilmid.ResponseStaffByFilmId
import javax.inject.Inject

class GetActorsListUseCase @Inject constructor(private val repository: CinemaRepository) {
    suspend fun executeActorsList(filmId: Int): List<ResponseStaffByFilmId> {
        return repository.getActorsByFilmId(filmId)
    }
}