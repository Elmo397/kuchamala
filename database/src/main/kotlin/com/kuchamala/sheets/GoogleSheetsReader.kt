package com.kuchamala.sheets

import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport
import com.google.api.client.http.javanet.NetHttpTransport
import com.google.api.services.sheets.v4.Sheets
import com.google.api.client.json.jackson2.JacksonFactory
import com.kuchamala.database.TablesKeys
import com.kuchamala.database.addClass
import com.kuchamala.database.addTeacher
import com.kuchamala.database.entity.Location
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle


class GoogleSheetsReader {
    private val httpTransport: NetHttpTransport = GoogleNetHttpTransport.newTrustedTransport()
    private val spreadsheetId = "1iPctpz-fYfvkur94kQa1ZGUNmdTa_1E-Wz2KIPh0ypU"

    fun read() {
        getData("Classes!A2:F", TablesKeys.Classes)
        getData("Teachers!A2:D", TablesKeys.Teachers)
        //getData("Timetable!A2:L", TablesKeys.Timetable)
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
            for (row in rows) {
                setDataToDb(row, key)
            }
        }
    }

    private fun setDataToDb(row: List<Any>, key: TablesKeys) {
        when (key) {
            TablesKeys.Classes -> setClassesData(row)
            TablesKeys.Teachers -> setTeachersData(row)
            TablesKeys.Timetable -> setTimetableData(row)
            TablesKeys.Events -> setEventsData(row)
            TablesKeys.Contacts -> setContactsData(row)
        }
    }

    private fun setClassesData(row: List<Any>) {
        val title = row[0].toString()
        val description = if (row[1] == "") null else row[1] as String?
        val duration = row[2].toString().toInt()
        val subscriptionPrice = if (row[3] == "") null else row[3].toString().toInt()
        val singleClassPrice = row[4].toString().toInt()
        val freeFirstClass = row[5] == "+"

        addClass(title, description, duration, subscriptionPrice, singleClassPrice, freeFirstClass)
    }

    private fun setTeachersData(row: List<Any>) {
        val surname = row[0].toString()
        val name = row[1].toString()
        val patronymic = if (row[2] == "") null else row[2].toString()
        val aboutTeacher = if (row[3] == "") null else row[3].toString()

        addTeacher(name, surname, patronymic, aboutTeacher)
    }

    private fun setTimetableData(row: List<Any>) {
        val clazz = row[0].toString()
        val teacher = row[1].toString()
        val age = addAge(row)
        val weekDay = row[3].toString()
        val startTime =  LocalTime.parse(row[4].toString(), DateTimeFormatter.ofLocalizedTime(FormatStyle.SHORT))
        val endTime = LocalTime.parse(row[5].toString(), DateTimeFormatter.ofLocalizedTime(FormatStyle.SHORT))
        val location = addLocation(row)

        //todo: find week day or add if not exist
        //todo: find class by title in classes entity
        //todo: find teacher in teacher entity
        //todo: call method to add
    }

    private fun addAge(row: List<Any>) {
        //todo: get, create, add...
    }

    //todo: create transaction for add
    private fun addLocation(row: List<Any>) {
        val place = row[6]
        val metroStation = row[7]
        val street = row[8]
        val houseNumber = row[9]
        val building = row[10]

        //todo: call method to add
    }

    private fun setEventsData(row: List<Any>) {
        //todo
    }

    private fun setContactsData(row: List<Any>) {
        //todo
    }
}