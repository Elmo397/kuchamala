package com.kuchamala.sheets

import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport
import com.google.api.client.http.javanet.NetHttpTransport
import com.google.api.services.sheets.v4.Sheets
import com.google.api.client.json.jackson2.JacksonFactory
import com.kuchamala.database.TablesKeys
import com.kuchamala.database.model.*
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle


class GoogleSheetsReader {
    private val httpTransport: NetHttpTransport = GoogleNetHttpTransport.newTrustedTransport()
    private val spreadsheetId = "1iPctpz-fYfvkur94kQa1ZGUNmdTa_1E-Wz2KIPh0ypU"

    fun read() {
        val startPos = "A2"
        val classesEndPos = "L"
        val teachersEndPos = "D"
        val timetableEndPos = "L"
        val eventsEndPos = "I"

        getData("${TablesKeys.Teachers}!$startPos:$teachersEndPos", TablesKeys.Teachers)
        getData("${TablesKeys.Classes}!$startPos:$classesEndPos", TablesKeys.Classes)
        getData("${TablesKeys.Timetable}!$startPos:$timetableEndPos", TablesKeys.Timetable)
        getData("${TablesKeys.Events}!$startPos:$eventsEndPos", TablesKeys.Events)
    }

    private fun getData(range: String, key: TablesKeys) {
        val credential = GoogleAuthorization().getCredentials(httpTransport)
        val jsonFactory = JacksonFactory.getDefaultInstance()

        val service = Sheets
            .Builder(httpTransport, jsonFactory, credential)
            .setApplicationName("kuchamala")
            .build()

        val response = service.spreadsheets().values().get(spreadsheetId, range).execute()
        val rows = response.getValues()

        if (rows == null || rows.isEmpty()) {
            println("No data found.")
        } else {
            rows.forEach { row -> setDataToDb(row, key) }
        }
    }

    private fun setDataToDb(row: List<Any>, key: TablesKeys) {
        when (key) {
            TablesKeys.Classes -> {
                Classes().addData(
                    title = row[0].toString(),
                    description = if (row[1] == "") null else row[1].toString(),
                    duration = row[2].toString().toInt(),
                    subscriptionPrice = if (row[3] == "") null else row[3].toString().toInt(),
                    singleClassPrice = row[4].toString().toInt(),
                    freeFirstClassVal = row[5].toString(),
                    minAge = if (row[6] == "") null else row[6].toString().toInt(),
                    maxAge = if (row[7] == "") null else row[7].toString().toInt()
                )

                TeacherClasses().addData(
                    classTitle = row[0].toString(),
                    teachersFullName = row[8].toString()
                )
            }
            TablesKeys.Teachers -> {
                Teachers().addData(
                    surname = row[0].toString(),
                    name = row[1].toString(),
                    patronymic = if (row[2] == "") null else row[2].toString(),
                    aboutTeacher = if (row[3] == "") null else row[3].toString()
                )
            }
            TablesKeys.Timetable -> {
                Timetable().addData(
                    classTitle = row[0].toString(),
                    teacherStr = row[1].toString(),
                    minAge = if (row[2] == "") null else row[2].toString().toInt(),
                    maxAge = if (row[3] == "") null else row[3].toString().toInt(),
                    weekDayStr = row[4].toString(),
                    startTimeStr = row[5].toString(),
                    endTimeStr = row[6].toString(),
                    place = row[7].toString(),
                    metroStation = if (row[8] == "") null else row[8].toString(),
                    street = if (row[9] == "") null else row[9].toString(),
                    houseNumber = if (row[10] == "") null else row[10].toString().toInt(),
                    building = if (row[11] == "") null else row[11].toString().toInt()
                )
            }
            TablesKeys.Events -> {
                Events().addData(
                    title = row[0].toString(),
                    eventDetails = row[1].toString(),
                    date = row[2].toString(),
                    weekDay = row[3].toString(),
                    timeStart = row[4].toString(),
                    timeEnd = row[5].toString(),
                    place = row[6].toString(),
                    address = row[7].toString(),
                    price = row[8].toString().toInt()
                )
            }
        }
    }
}