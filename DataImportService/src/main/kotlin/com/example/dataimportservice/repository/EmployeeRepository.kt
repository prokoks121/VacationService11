package com.example.dataimportservice.repository

import com.example.dataimportservice.model.Employee
import org.springframework.data.jpa.repository.JpaRepository

interface EmployeeRepository:JpaRepository<Employee,Long>{
    fun existsByEmail(email:String):Boolean
}

