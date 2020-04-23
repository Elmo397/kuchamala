package com.kuchamala.sheets.reader

import com.google.api.client.auth.oauth2.Credential
import com.google.api.client.http.javanet.NetHttpTransport
import com.google.api.client.json.jackson2.JacksonFactory
import com.google.api.services.sheets.v4.Sheets
import com.kuchamala.wp.database.getPostName
import com.kuchamala.wp.page.*

class GoogleSheetsReader(httpTransport: NetHttpTransport, credential: Credential) {
    private val spreadsheetId = "1KXT7mqxzMDutsqStKT6U76fVqlTzFAap8bo3xeveJNw"
    private var service: Sheets

    init {
        val jsonFactory = JacksonFactory.getDefaultInstance()
        service = Sheets
            .Builder(httpTransport, jsonFactory, credential)
            .setApplicationName("КучаМала")
            .build()
    }

    fun readAllClassesData(listTitle: String) = AllClassesData(service, listTitle, spreadsheetId).read()

    fun readClassData(classListTitle: String) = ClassData(service, classListTitle, spreadsheetId).read()
}