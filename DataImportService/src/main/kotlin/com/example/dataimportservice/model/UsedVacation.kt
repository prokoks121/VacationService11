package com.example.dataimportservice.model

import java.time.LocalDate
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
    val vacationStart:LocalDate,
    @Column(name = "vacation_end")
    val vacationEnd: LocalDate
)