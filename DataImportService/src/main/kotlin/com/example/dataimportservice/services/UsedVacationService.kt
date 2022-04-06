package com.example.dataimportservice.services

import com.example.dataimportservice.model.UsedVacation
import com.example.dataimportservice.model.Vacation
import com.example.dataimportservice.repository.UsedVacationRepository
import org.apache.commons.csv.CSVFormat
import org.apache.commons.csv.CSVParser
import org.apache.commons.csv.CSVRecord
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStream
import java.io.InputStreamReader
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle

@Service
class UsedVacationService @Autowired constructor(val usedVacationRepository: UsedVacationRepository) {

   /* var TYPE = "text/csv"

    fun hasCSVFormat(file: MultipartFile): Boolean {
        return TYPE == file.contentType
    }

    fun csvToTutorials(inputStream: InputStream): List<UsedVacation> {
        try {
            BufferedReader(InputStreamReader(inputStream, "UTF-8")).use { fileReader ->
                CSVParser(
                    fileReader,
                    CSVFormat.DEFAULT.withFirstRecordAsHeader().withIgnoreHeaderCase().withTrim()
                ).use { csvParser ->
                    val usedVacations: MutableList<UsedVacation> = ArrayList()
                    val csvRecords: Iterable<CSVRecord> = csvParser.records
                    for (csvRecord in csvRecords) {
                        if (validateDataFormat(csvRecord["Vacation start date"],csvRecord["Vacation end date"])){
                            val usedVacation = UsedVacation(
                                employeeEmail = csvRecord["Employee"],
                                vacationEnd = csvRecord["Vacation end date"],
                                vacationStart = csvRecord["Vacation start date"]
                            )
                            usedVacations.add(usedVacation)
                        }else{
                            throw RuntimeException("Invalid data format: ${csvRecord["Vacation start date"]} , ${csvRecord["Vacation end date"]}")
                        }
                    }
                    return usedVacations
                }
            }
        } catch (e: IOException) {
            throw RuntimeException("fail to parse CSV file: " + e.message)
        }
    }

    fun validateDataFormat(start:String,end:String):Boolean{
        return try {
            val formatter = DateTimeFormatter.ofLocalizedDate(FormatStyle.FULL)
            LocalDate.parse(start, formatter)
            LocalDate.parse(end, formatter)
            true

        }catch (e:Exception){
            false
        }
    }

    fun save(file: MultipartFile) {
        try {
            val usedVacations: List<UsedVacation> = csvToTutorials(file.inputStream)
            usedVacationRepository.saveAll(usedVacations)
        } catch (e: IOException) {
            throw RuntimeException("fail to store csv data: " + e.message)
        }
    }*/

    val formatter = DateTimeFormatter.ofLocalizedDate(FormatStyle.FULL)

    fun readData(csvParser:CSVParser):MutableList<UsedVacation>{
        val usedVacations: MutableList<UsedVacation> = ArrayList()
        val csvRecords: Iterable<CSVRecord> = csvParser.records
        for (csvRecord in csvRecords) {
            if (validateDataFormat(csvRecord["Vacation start date"],csvRecord["Vacation end date"])){
                val usedVacation = UsedVacation(
                    employeeEmail = csvRecord["Employee"],
                    vacationEnd = LocalDate.parse(csvRecord["Vacation end date"], formatter),
                    vacationStart = LocalDate.parse(csvRecord["Vacation start date"], formatter)
                )
                usedVacations.add(usedVacation)
            }else{
                throw RuntimeException("Invalid data format: ${csvRecord["Vacation start date"]} , ${csvRecord["Vacation end date"]}")
            }
        }
        return usedVacations
    }


    fun save(vacations: List<UsedVacation>) {
        usedVacationRepository.saveAll(vacations)
    }

    fun validateDataFormat(start:String,end:String):Boolean{
        return try {
            val formatter = DateTimeFormatter.ofLocalizedDate(FormatStyle.FULL)
            LocalDate.parse(start, formatter)
            LocalDate.parse(end, formatter)
            true

        }catch (e:Exception){
            false
        }
    }

}