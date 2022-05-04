package com.example.dataimportservice.services

import com.example.dataimportservice.model.SuccessResponse
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Component
import org.springframework.web.multipart.MultipartFile

@Component
class DataImportServices @Autowired constructor(
    val employeeService: EmployeeService,
    val usedVacationService: UsedVacationService,
    val vacationService: VacationService
) {
    fun addVacations(file: MultipartFile, readFirstLine: Boolean): ResponseEntity<SuccessResponse> {
        return vacationService.readCSVData(file, readFirstLine)
    }

    fun addEmployees(file: MultipartFile, readFirstLine: Boolean): ResponseEntity<SuccessResponse> {
        return employeeService.readCSVData(file, readFirstLine)
    }

    fun addUsedVacations(file: MultipartFile, readFirstLine: Boolean): ResponseEntity<SuccessResponse> {
        return usedVacationService.readCSVData(file, readFirstLine)
    }
}