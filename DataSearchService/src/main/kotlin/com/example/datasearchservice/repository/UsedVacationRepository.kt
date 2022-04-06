package com.example.datasearchservice.repository

import com.example.datasearchservice.model.UsedVacation
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import java.time.LocalDate

interface UsedVacationRepository : JpaRepository<UsedVacation,Long> {
    fun findAllByEmployeeEmail(employeeEmail: String):List<UsedVacation>
    @Query("SELECT u FROM UsedVacation u WHERE u.employeeEmail=?1 AND (u.vacationStart BETWEEN ?2 AND ?3 OR u.vacationEnd BETWEEN ?2 AND ?3)")
    fun findAllBetweenDates(employeeEmail: String,from:LocalDate,to:LocalDate):List<UsedVacation>

}