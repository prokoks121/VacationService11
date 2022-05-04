package com.example.dataimportservice.services

import com.example.dataimportservice.model.SuccessResponse
import org.apache.commons.csv.CSVFormat
import org.apache.commons.csv.CSVParser
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.multipart.MultipartFile
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStream
import java.io.InputStreamReader

abstract class CSVService<T> {
    var TYPE = "text/csv"

    private fun hasCSVFormat(file: MultipartFile): Boolean {
        return TYPE == file.contentType
    }

    fun csvToData(inputStream: InputStream, readFirstLine: Boolean): MutableList<T> {
        var firstLine = ""
        try {
            BufferedReader(InputStreamReader(inputStream, "UTF-8")).use { fileReader ->
                if (readFirstLine) {
                    firstLine = fileReader.readLine().toString().split(",")[1]
                }
                CSVParser(
                    fileReader,
                    CSVFormat.DEFAULT.withFirstRecordAsHeader().withIgnoreHeaderCase().withTrim()
                ).use { csvParser ->
                    return readData(csvParser, firstLine)
                }
            }
        } catch (e: IOException) {
            throw RuntimeException("fail to parse CSV file: " + e.message)
        }
    }

    abstract fun readData(csvParser: CSVParser, firstLine: String = ""): MutableList<T>
    protected abstract fun save(vacations: MutableList<T>)
    private fun save(file: MultipartFile, readFirstLine: Boolean = false) {
        try {
            val list: MutableList<T> = csvToData(file.inputStream, readFirstLine)
            save(list)
        } catch (e: IOException) {
            throw RuntimeException("fail to store csv data: " + e.message)
        }
    }

    fun readCSVData(file: MultipartFile, readFirstLine: Boolean = false): ResponseEntity<SuccessResponse> {
        var message = ""
        if (hasCSVFormat(file)) {
            return try {
                save(file, readFirstLine)
                message = "Uploaded the file successfully: " + file.originalFilename
                ResponseEntity(SuccessResponse(HttpStatus.CREATED, message), HttpStatus.CREATED)
            } catch (e: Exception) {
                message = "Could not upload the file: " + file.originalFilename + "! " + e.message
                ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(message)
                ResponseEntity(SuccessResponse(HttpStatus.EXPECTATION_FAILED, message), HttpStatus.EXPECTATION_FAILED)
            }
        }
        message = "Please upload a csv file!"
        return ResponseEntity(SuccessResponse(HttpStatus.BAD_REQUEST, message), HttpStatus.BAD_REQUEST)
    }
}