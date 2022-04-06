package com.example.dataimportservice.model

import javax.persistence.*

@Entity
data class Employee(
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    val id:Long=0,
    val email:String,
    val password:String
){
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Employee

        if (id != other.id) return false

        return true
    }

    override fun hashCode(): Int {
        return id.hashCode()
    }
}
