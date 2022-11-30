package com.example.kinopoiskunofficial.domain

import com.example.kinopoiskunofficial.data.CinemaRepository
import com.example.kinopoiskunofficial.data.staffbyid.ResponseStaffById
import javax.inject.Inject

class GetStaffByIdUseCase @Inject constructor(private val repository: CinemaRepository) {
    suspend fun executeStaffById(staffId: Int): ResponseStaffById {
        return repository.getStaffById(staffId)
    }
}