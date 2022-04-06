package com.example.dataimportservice.services

import com.example.dataimportservice.model.Employee
import com.example.dataimportservice.model.UsedVacation
import com.example.dataimportservice.model.Vacation
import org.apache.commons.csv.CSVFormat
import org.apache.commons.csv.CSVParser
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Component
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStream
import java.io.InputStreamReader
import kotlin.reflect.KClass

@Component
class CSVService @Autowired constructor(
    val employeeService: EmployeeService,
    val usedVacationService: UsedVacationService,
    val vacationService: VacationService
)
{
    var TYPE = "text/csv"

    fun hasCSVFormat(file: MultipartFile): Boolean {
        return TYPE == file.contentType
    }
    fun <T:Any> csvToVacations(inputStream: InputStream,readFirstLine:Boolean,type:KClass<T>): List<T> {
        var firstLine:String = ""
        try {
            BufferedReader(InputStreamReader(inputStream, "UTF-8")).use { fileReader ->
                if (readFirstLine){
                    firstLine = fileReader.readLine().toString().split(",")[1]
                }
                CSVParser(
                    fileReader,
                    CSVFormat.DEFAULT.withFirstRecordAsHeader().withIgnoreHeaderCase().withTrim()
                ).use { csvParser ->
                    return when(type){
                        Vacation::class -> {
                            vacationService.readData(csvParser,firstLine) as List<T>
                        }
                        UsedVacation::class ->{
                            usedVacationService.readData(csvParser) as List<T>
                        }
                        Employee::class ->{
                            employeeService.readData(csvParser) as List<T>
                        }
                        else-> throw RuntimeException("fail type: ")

                    }
                }
            }
        } catch (e: IOException) {
            throw RuntimeException("fail to parse CSV file: " + e.message)
        }
    }


    fun <T:Any>  save(file: MultipartFile,readFirstLine:Boolean = false,type: KClass<T>) {
        try {
            val list: List<T> = csvToVacations(file.inputStream,readFirstLine,type)
             when(type){
                Vacation::class -> {
                    vacationService.save(list as List<Vacation>)
                }
                UsedVacation::class ->{
                    usedVacationService.save(list as List<UsedVacation>)
                }
                Employee::class ->{
                    employeeService.save(list as List<Employee>)
                }
                else-> throw RuntimeException("fail type: ")
            }

        } catch (e: IOException) {
            throw RuntimeException("fail to store csv data: " + e.message)
        }
    }

    fun <T:Any> readCSVData(file: MultipartFile,readFirstLine:Boolean = false,type: KClass<T>):ResponseEntity<String>{
        var message = ""
        if (hasCSVFormat(file)) {
            return try {
                save(file,readFirstLine,type)
                message = "Uploaded the file successfully: " + file.originalFilename
                ResponseEntity.status(HttpStatus.OK).body<String>(message)
            } catch (e: Exception) {
                message = "Could not upload the file: " + file.originalFilename + "! "  + e.message
                ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body<String>(message)
            }
        }
        message = "Please upload a csv file!"
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body<String>(message)
    }


}