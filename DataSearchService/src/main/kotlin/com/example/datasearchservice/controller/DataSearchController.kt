package com.example.datasearchservice.controller

import com.example.datasearchservice.repository.EmployeeRepository
import com.example.datasearchservice.repository.UsedVacationRepository
import com.example.datasearchservice.repository.VacationRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*
import java.time.temporal.ChronoUnit

@RestController
@RequestMapping("/api/employee")
class DataSearchController @Autowired constructor(
    val employeeRepository: EmployeeRepository,
    val vacationRepository: VacationRepository,
    val usedVacationRepository: UsedVacationRepository
) {

    @GetMapping("/{email}/vacation")
    fun getEmployeeVacation(@PathVariable email: String,
                            @RequestParam(required = false) type:String?):HashMap<String,Int>{
        val response = HashMap<String, Int>()
        if (employeeRepository.existsByEmail(email)){
            val vacations = vacationRepository.findAllByEmployeeEmail(email)
            val usedVacations = usedVacationRepository.findAllByEmployeeEmail(email)
            var total = 0
            var totalUsed = 0
            vacations.forEach{
                total += it.totalDays
            }
            usedVacations.forEach {
                totalUsed += ChronoUnit.DAYS.between(it.vacationStartDate, it.vacationEndDate).toInt()+1
            }
            type?.let {
                when (it) {
                    "total" -> {
                        response["total_vacation"] = total
                        return response
                    }
                    "totalUsed" -> {
                        response["total_used_vacation"] = totalUsed
                        return response
                    }
                    "totalFree" -> {
                        response["total_free_vacation"] = total - totalUsed
                        return response
                    }
                    else -> {}
                }
            }
            response["total_vacation"] = total
            response["total_used_vacation"] = totalUsed
            response["total_free_vacation"] = total - totalUsed
        }
        return response
    }


}