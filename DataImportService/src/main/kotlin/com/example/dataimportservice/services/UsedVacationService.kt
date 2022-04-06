package com.example.dataimportservice.services

import com.example.dataimportservice.model.UsedVacation
import com.example.dataimportservice.repository.UsedVacationRepository
import org.apache.commons.csv.CSVParser
import org.apache.commons.csv.CSVRecord
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle

@Service
class UsedVacationService @Autowired constructor(val usedVacationRepository: UsedVacationRepository) {

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