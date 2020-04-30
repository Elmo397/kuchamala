package com.kuchamala.sheets.reader

import com.google.api.client.auth.oauth2.Credential
import com.google.api.client.http.javanet.NetHttpTransport
import com.google.api.client.json.jackson2.JacksonFactory
import com.google.api.services.sheets.v4.Sheets
import com.kuchamala.wp.page.AllClassesPageData
import com.kuchamala.wp.page.ClassPageData

class GoogleSheetsReader(httpTransport: NetHttpTransport, credential: Credential) {
    private val spreadsheetId = "1-TlqDYv3GAOWcQNA8-ks6v4e6igI9KRTDgf2LUSgjVM"
    private var service: Sheets

    init {
        val jsonFactory = JacksonFactory.getDefaultInstance()
        service = Sheets
            .Builder(httpTransport, jsonFactory, credential)
            .setApplicationName("Общая")
            .build()
    }

    fun readAllClassesData(tableTitle: String): AllClassesPageData {
        val tableId = findId(tableTitle)
        val allTablesId = getAllTablesId()

        return AllClassesData(service, tableId).read(allTablesId)
    }

    fun readClassData(classTableTitle: String): ClassPageData {
        val tableId = findId(classTableTitle)

        return ClassData(service, tableId).read()
    }

    private fun findId(tableTitle: String): String {
        var rowNumb = 2
        var tableIdRow = service.spreadsheets().values().get(spreadsheetId, "Лист1!A$rowNumb:B$rowNumb").execute().getValues()
        var tableId = ""

        while (tableIdRow != null) {
            if (tableIdRow[0][0].toString().toLowerCase() == tableTitle.toLowerCase()) {
                tableId = tableIdRow[0][1].toString()
                break
            }

            rowNumb++
            tableIdRow = service.spreadsheets().values().get(spreadsheetId, "Лист1!A$rowNumb:B$rowNumb").execute().getValues()
        }

        return tableId
    }

    private fun getAllTablesId(): Map<String, String> {
        val allTablesId = mutableMapOf<String, String>()

        var rowNumb = 2
        var tableIdRow = service.spreadsheets().values().get(spreadsheetId, "Лист1!A$rowNumb:B$rowNumb").execute().getValues()
        while (tableIdRow != null) {
            allTablesId[tableIdRow[0][0].toString().toLowerCase()] = tableIdRow[0][1].toString()

            rowNumb++
            tableIdRow = service.spreadsheets().values().get(spreadsheetId, "Лист1!A$rowNumb:B$rowNumb").execute().getValues()
        }

        return allTablesId
    }
}