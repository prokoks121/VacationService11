package com.example.datasearchservice.services

import com.example.datasearchservice.repository.EmployeeRepository
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service


@Service
class UserDetailsServiceImpl constructor(
    val employeeRepository: EmployeeRepository
): UserDetailsService {


    override fun loadUserByUsername(email: String): UserDetails {

        return if (employeeRepository.existsByEmail(email)) {
            val employee = employeeRepository.findByEmail(email)
            User.builder()
                .username(email) //change here to store encoded password in db
                .password(employee.password)
                .disabled(false)
                .accountExpired(false)
                .accountLocked(false)
                .credentialsExpired(false)
                .roles("EMPLOYEE")
                .build()
        } else {
            throw UsernameNotFoundException("User Name is not Found")
        }
    }
}