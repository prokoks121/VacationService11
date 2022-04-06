package com.example.dataimportservice.security

import org.springframework.security.web.authentication.preauth.AbstractPreAuthenticatedProcessingFilter
import javax.servlet.http.HttpServletRequest

class APIKeyAuthFilter(var principalRequestHeader: String): AbstractPreAuthenticatedProcessingFilter() {


    override fun getPreAuthenticatedPrincipal(request: HttpServletRequest): Any {
        return  request.getHeader(principalRequestHeader)
    }

    override fun getPreAuthenticatedCredentials(request: HttpServletRequest): Any {
        return "N/A";
    }
}