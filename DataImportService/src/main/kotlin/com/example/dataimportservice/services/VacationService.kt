package com.example.dataimportservice.services

import com.example.dataimportservice.model.Vacation
import com.example.dataimportservice.repository.VacationRepository
import org.apache.commons.csv.CSVParser
import org.apache.commons.csv.CSVRecord
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class VacationService @Autowired constructor(val vacationRepository: VacationRepository) {

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