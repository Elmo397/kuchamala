package com.kuchamala

import com.google.api.client.auth.oauth2.Credential
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport
import com.google.api.client.http.javanet.NetHttpTransport
import com.kuchamala.sheets.GoogleSheetsReader
import com.kuchamala.sheets.GoogleAuthorization
import com.kuchamala.wp.page.ClassPageGenerator

fun main() {
    val httpTransport: NetHttpTransport = GoogleNetHttpTransport.newTrustedTransport()
    val credential = GoogleAuthorization().getCredentials(httpTransport)

    createMathClassPage(httpTransport, credential)
}

private fun createMathClassPage(httpTransport: NetHttpTransport, credential: Credential) {
    val data = GoogleSheetsReader(httpTransport, credential).run("Математика")
    val page = ClassPageGenerator().run(
        data.headerTitle,
        data.headerText,
        data.descriptionTitle,
        data.descriptionParts,
        data.teachers,
        data.imagesId,
        data.formId
    )

    println(page)
}
