package com.kuchamala.sheets

import com.google.api.client.auth.oauth2.Credential
import com.google.api.client.http.javanet.NetHttpTransport
import com.google.api.client.json.jackson2.JacksonFactory
import com.google.api.services.sheets.v4.Sheets

class GoogleSheetsReader(private val httpTransport: NetHttpTransport, private val credential: Credential) {
    private val spreadsheetId = "1KXT7mqxzMDutsqStKT6U76fVqlTzFAap8bo3xeveJNw"

    data class ClassData(
        val headerTitle: String,
        val headerText: String,
        val descriptionTitle: String,
        val descriptionParts: List<String>,
        val teachers: Map<String, String>,
        val imagesId: List<Int>,
        val formId: Int
    )

    fun run(classListTitle: String): ClassData {
        val jsonFactory = JacksonFactory.getDefaultInstance()
        val service = Sheets
            .Builder(httpTransport, jsonFactory, credential)
            .setApplicationName("КучаМала")
            .build()


        return ClassData(
            getHeaderTitle(service, classListTitle),
            getHeaderText(service, classListTitle),
            getDescriptionTitle(service, classListTitle),
            getDescriptionParts(service, classListTitle),
            getTeachers(service, classListTitle),
            getImages(service, classListTitle),
            getFormId(service, classListTitle)
        )
    }

    private fun getHeaderTitle(service: Sheets, classListTitle: String): String {
        val range = "$classListTitle!B2"
        val response = service.spreadsheets().values().get(spreadsheetId, range).execute()

        return response.getValues()[0][0].toString()
    }

    private fun getHeaderText(service: Sheets, classListTitle: String): String {
        val range = "$classListTitle!C2"
        val response = service.spreadsheets().values().get(spreadsheetId, range).execute()

        return response.getValues()[0][0].toString()
    }

    private fun getDescriptionTitle(service: Sheets, classListTitle: String): String {
        val range = "$classListTitle!B3"
        val response = service.spreadsheets().values().get(spreadsheetId, range).execute()

        return response.getValues()[0][0].toString()
    }

    private fun getDescriptionParts(service: Sheets, classListTitle: String): List<String> {
        val range = "$classListTitle!C3:C7"
        val response = service.spreadsheets().values().get(spreadsheetId, range).execute()
        val rows = response.getValues()
        val descriptionParts = mutableListOf<String>()

        for (row in rows) {
            if (row.size != 0) {
                descriptionParts.add(row[0].toString())
            }
        }

        return descriptionParts
    }

    private fun getTeachers(service: Sheets, classListTitle: String): Map<String, String> {
        val teachers = mutableMapOf<String, String>()
        val nameRange = "$classListTitle!B8:B12"
        val textRange = "$classListTitle!C"

        val response = service.spreadsheets().values().get(spreadsheetId, nameRange).execute()
        val nameRows = response.getValues()

        var rowTextNumber = 8
        for (nameRow in nameRows) {
            if (nameRow.size != 0) {
                val teacherName = nameRow[0].toString()
                val teacherText = service
                    .spreadsheets().values()
                    .get(spreadsheetId, "$textRange$rowTextNumber")
                    .execute()
                    .getValues()[0][0]
                    .toString()

                teachers[teacherName] = teacherText
                rowTextNumber++
            }
        }

        return teachers
    }

    private fun getImages(service: Sheets, classListTitle: String): List<Int> {
        val images = mutableListOf<Int>()
        val range = "$classListTitle!D2:D12"
        val response = service.spreadsheets().values().get(spreadsheetId, range).execute()
        val rows = response.getValues()

        for (row in rows) {
            if (row.size != 0) {
                images.add(row[0].toString().toInt())
            }
        }

        return images
    }

    private fun getFormId(service: Sheets, classListTitle: String): Int {
        val range = "$classListTitle!B13"
        val response = service.spreadsheets().values().get(spreadsheetId, range).execute()

        return response.getValues()[0][0].toString().toInt()
    }
}