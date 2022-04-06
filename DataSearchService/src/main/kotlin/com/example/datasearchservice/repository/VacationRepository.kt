package com.example.datasearchservice.repository

import com.example.datasearchservice.model.Vacation
import org.springframework.data.jpa.repository.JpaRepository

interface VacationRepository:JpaRepository<Vacation,Long> {
    fun findAllByEmployeeEmail(employeeEmail: String):List<Vacation>
}