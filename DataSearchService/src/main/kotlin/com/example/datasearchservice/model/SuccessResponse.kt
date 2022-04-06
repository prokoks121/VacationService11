package com.example.datasearchservice.model

import org.springframework.http.HttpStatus

data class SuccessResponse(
    val status: HttpStatus,
    val message:String
)
