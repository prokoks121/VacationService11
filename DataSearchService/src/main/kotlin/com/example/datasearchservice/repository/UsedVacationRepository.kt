package com.example.datasearchservice.repository

import com.example.datasearchservice.model.UsedVacation
import org.springframework.data.jpa.repository.JpaRepository

interface UsedVacationRepository : JpaRepository<UsedVacation,Long> {
    fun findAllByEmployeeEmail(employeeEmail: String):List<UsedVacation>
}