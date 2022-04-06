package com.example.dataimportservice.services

import com.example.dataimportservice.model.Vacation
import com.example.dataimportservice.repository.VacationRepository
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


@Service
class VacationService @Autowired constructor(val vacationRepository: VacationRepository) {

 /*   var TYPE = "text/csv"

    fun hasCSVFormat(file: MultipartFile): Boolean {
        return TYPE == file.contentType
    }

    fun csvToVacations(inputStream: InputStream): List<Vacation> {

        try {
            BufferedReader(InputStreamReader(inputStream, "UTF-8")).use { fileReader ->
                val firstLine = fileReader.readLine().toString().split(",")[1]
                CSVParser(
                    fileReader,
                    CSVFormat.DEFAULT.withFirstRecordAsHeader().withIgnoreHeaderCase().withTrim()
                ).use { csvParser ->
                    val vacations: MutableList<Vacation> = ArrayList()
                    val csvRecords: Iterable<CSVRecord> = csvParser.records
                    for (csvRecord in csvRecords) {
                            val vacation = Vacation(
                                employeeEmail = csvRecord["Employee"],
                                totalDays = csvRecord["Total vacation days"].toInt(),
                                year = firstLine,
                            )
                            vacations.add(vacation)
                    }
                    return vacations
                }
            }
        } catch (e: IOException) {
            throw RuntimeException("fail to parse CSV file: " + e.message)
        }
    }


    fun save(file: MultipartFile) {
        try {
            val vacations: List<Vacation> = csvToVacations(file.inputStream)
            vacationRepository.saveAll(vacations)
        } catch (e: IOException) {
            throw RuntimeException("fail to store csv data: " + e.message)
        }
    }
*/

    fun readData(csvParser:CSVParser, firstLine:String = ""):MutableList<Vacation>{
        val vacations: MutableList<Vacation> = ArrayList()
        val csvRecords: Iterable<CSVRecord> = csvParser.records
        for (csvRecord in csvRecords) {
            val vacation = Vacation(
                employeeEmail = csvRecord["Employee"],
                totalDays = csvRecord["Total vacation days"].toInt(),
                year = firstLine,
            )
            vacations.add(vacation)
        }
        return vacations
    }


    fun save(vacations: List<Vacation>) {
            vacationRepository.saveAll(vacations)
    }

}