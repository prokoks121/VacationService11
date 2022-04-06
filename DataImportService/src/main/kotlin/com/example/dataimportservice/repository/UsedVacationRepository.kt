package com.example.dataimportservice.repository

import com.example.dataimportservice.model.UsedVacation
import org.springframework.data.jpa.repository.JpaRepository

interface UsedVacationRepository : JpaRepository<UsedVacation,Long> {
}