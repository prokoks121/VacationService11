package com.example.datasearchservice.model

import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id


@Entity
data class UsedVacation(
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    val id:Long = 0,
    @Column(name = "employee_email")
    val employeeEmail:String,
    @Column(name = "vacation_start")
    val vacationStart:String,
    @Column(name = "vacation_end")
    val vacationEnd: String
): VacationDate {
    override val vacationStartDate: LocalDate
        get() {
            val formatter = DateTimeFormatter.ofLocalizedDate(FormatStyle.FULL)
            return LocalDate.parse(vacationStart, formatter)
        }
    override val vacationEndDate: LocalDate
        get() {
            val formatter = DateTimeFormatter.ofLocalizedDate(FormatStyle.FULL)
            return LocalDate.parse(vacationEnd, formatter)
        }
}
interface VacationDate{
    val vacationStartDate:LocalDate
    val vacationEndDate:LocalDate
}
