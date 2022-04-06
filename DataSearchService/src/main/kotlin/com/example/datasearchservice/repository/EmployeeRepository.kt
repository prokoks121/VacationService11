package com.example.datasearchservice.repository

import com.example.datasearchservice.model.Employee
import org.springframework.data.jpa.repository.JpaRepository

interface EmployeeRepository:JpaRepository<Employee,Long>{
    fun existsByEmail(email:String):Boolean
    fun findByEmail(email:String): Employee
}

