package com.example.datasearchservice.controller

import com.example.datasearchservice.model.UsedVacation
import com.example.datasearchservice.repository.EmployeeRepository
import com.example.datasearchservice.repository.UsedVacationRepository
import com.example.datasearchservice.repository.VacationRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.web.bind.annotation.*
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle
import java.time.temporal.ChronoUnit

@RestController
@RequestMapping("/api/employee")
class DataSearchController @Autowired constructor(
    val employeeRepository: EmployeeRepository,
    val vacationRepository: VacationRepository,
    val usedVacationRepository: UsedVacationRepository
) {

    @GetMapping("/vacation")
    fun getEmployeeVacation(@RequestParam(required = false) type:String?,
                            @AuthenticationPrincipal user:UserDetails):HashMap<String,Int>{
        val response = HashMap<String, Int>()
        if (employeeRepository.existsByEmail(user.username)){
            val vacations = vacationRepository.findAllByEmployeeEmail(user.username)
            val usedVacations = usedVacationRepository.findAllByEmployeeEmail(user.username)
            var total = 0
            var totalUsed = 0
            vacations.forEach{
                total += it.totalDays
            }
            usedVacations.forEach {
                totalUsed += ChronoUnit.DAYS.between(it.vacationStart, it.vacationEnd).toInt()+1
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

    @GetMapping("/vacation/used")
    fun getListOfUsedVacations(
        @AuthenticationPrincipal user:UserDetails,
        @RequestParam(required = true) dateStart:String,
        @RequestParam(required = true) dateEnd:String
    ):List<UsedVacation>{
        if(validateDataFormat(dateStart,dateEnd)){
            val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            return usedVacationRepository
                .findAllBetweenDates(
                    user.username,
                    LocalDate.parse(dateStart, formatter),
                    LocalDate.parse(dateEnd, formatter))
        }else{
            throw RuntimeException("Data format exceptio")
        }
    }

    @PostMapping("/vacation/used")
    fun addUsedVacation(
        @RequestBody data:List<String>,
        @AuthenticationPrincipal user:UserDetails
    ): UsedVacation {
        if (employeeRepository.existsByEmail(user.username) && validateDataFormat(data[0],data[1])) {
            val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            val usedVacation = UsedVacation(
                employeeEmail = user.username,
                vacationStart = LocalDate.parse(data[0], formatter),
                vacationEnd = LocalDate.parse(data[1], formatter)
            )
            usedVacationRepository.save(usedVacation)
            return usedVacation
        }
        else
            throw java.lang.RuntimeException("Korisnik ne postoji")
    }

    fun validateDataFormat(start:String,end:String):Boolean{
        return try {
            val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
            LocalDate.parse(start, formatter)
            LocalDate.parse(end, formatter)
            true

        }catch (e:Exception){
            false
        }
    }

}