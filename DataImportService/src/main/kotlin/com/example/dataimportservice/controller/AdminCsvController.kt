package com.example.dataimportservice.controller

import com.example.dataimportservice.model.Employee
import com.example.dataimportservice.model.SuccessResponse
import com.example.dataimportservice.model.UsedVacation
import com.example.dataimportservice.model.Vacation
import com.example.dataimportservice.services.CSVService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.multipart.MultipartFile


@RestController
class AdminCsvController @Autowired constructor(
    val service:CSVService
) {

    @PostMapping("/upload/employee")
    fun uploadEmployee(@RequestParam("file") file: MultipartFile): ResponseEntity<SuccessResponse> {
        return  service.readCSVData(file,true,Employee::class)
    }

    @PostMapping("/upload/used-vacation")
    fun uploadUsedVacations(@RequestParam("file") file: MultipartFile): ResponseEntity<SuccessResponse> {
              return service.readCSVData(file,false,UsedVacation::class)
    }

    @PostMapping("/upload/vacation")
    fun uploadVacations(@RequestParam("file") file: MultipartFile): ResponseEntity<SuccessResponse> {
        return service.readCSVData(file,true,Vacation::class)
    }

}