package com.example.dataimportservice.repository

import com.example.dataimportservice.model.Vacation
import org.springframework.data.jpa.repository.JpaRepository

interface VacationRepository:JpaRepository<Vacation,Long> {
}