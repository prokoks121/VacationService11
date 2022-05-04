package com.example.dataimportservice.controller

import com.example.dataimportservice.model.SuccessResponse
import com.example.dataimportservice.services.DataImportServices
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.multipart.MultipartFile


@RestController("/upload")
class AdminCsvController @Autowired constructor(
    val dataImportServices: DataImportServices
) {

    @PostMapping("/employee")
    fun uploadEmployee(@RequestParam("file") file: MultipartFile): ResponseEntity<SuccessResponse> {
        return  dataImportServices.addEmployees(file,true)
    }

    @PostMapping("/used-vacation")
    fun uploadUsedVacations(@RequestParam("file") file: MultipartFile): ResponseEntity<SuccessResponse> {
              return dataImportServices.addVacations(file,false)
    }

    @PostMapping("/vacation")
    fun uploadVacations(@RequestParam("file") file: MultipartFile): ResponseEntity<SuccessResponse> {
        return dataImportServices.addUsedVacations(file,true)
    }

}