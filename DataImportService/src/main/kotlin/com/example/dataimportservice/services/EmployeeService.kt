package com.example.dataimportservice.services

import com.example.dataimportservice.model.Employee
import com.example.dataimportservice.repository.EmployeeRepository
import org.apache.commons.csv.CSVParser
import org.apache.commons.csv.CSVRecord
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class EmployeeService @Autowired constructor(
    val employeeRepository: EmployeeRepository
) : CSVService<Employee>() {
    override fun readData(csvParser: CSVParser, firstLine: String): MutableList<Employee> {
        val employee: MutableList<Employee> = ArrayList()
        val csvRecords: Iterable<CSVRecord> = csvParser.records
        for (csvRecord in csvRecords) {
            val tutorial = Employee(
                email = csvRecord["Employee Email"],
                password = csvRecord["Employee Password"]
            )
            employee.add(tutorial)
        }
        return employee
    }

    override fun save(vacations: MutableList<Employee>) {
        employeeRepository.saveAll(vacations)
    }

}