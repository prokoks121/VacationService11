package com.example.datasearchservice.model

import org.springframework.http.HttpStatus

data class ErrorResponse(
    val status: HttpStatus,
    val message:String,
    val errors:List<String>
)
