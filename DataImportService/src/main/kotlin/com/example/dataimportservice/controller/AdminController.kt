package com.example.dataimportservice.controller

import com.example.dataimportservice.model.Employee
import com.example.dataimportservice.model.UsedVacation
import com.example.dataimportservice.model.Vacation
import com.example.dataimportservice.repository.EmployeeRepository
import com.example.dataimportservice.repository.UsedVacationRepository
import com.example.dataimportservice.repository.VacationRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/admin")
class AdminController @Autowired constructor(
    val employeeRepository: EmployeeRepository,
    val vacationRepository: VacationRepository,
    val usedVacationRepository: UsedVacationRepository
) {

    @PostMapping("/add/employee")
    fun addEmployee(
        @RequestBody employee: Employee
    ):ResponseEntity<Employee>{
        if (!employeeRepository.existsByEmail(employee.email)){
            employeeRepository.save(employee)
        }else{
             throw java.lang.RuntimeException("Email already exist")
        }
        return ResponseEntity(employee,HttpStatus.CREATED)
    }


    @PostMapping("/add/vacation")
    fun addVacation(
        @RequestBody vacation: Vacation
    ): Vacation {
        if (employeeRepository.existsByEmail(vacation.employeeEmail))
            vacationRepository.save(vacation)
        else
            throw java.lang.RuntimeException("Korisnik ne postoji")
        return vacation
    }

    @PostMapping("/add/used-vacation")
    fun addUsedVacation(
        @RequestBody usedVacation: UsedVacation
    ): UsedVacation {
        if (employeeRepository.existsByEmail(usedVacation.employeeEmail))
        usedVacationRepository.save(usedVacation)
        else
            throw java.lang.RuntimeException("Korisnik ne postoji")
    return usedVacation
    }
}