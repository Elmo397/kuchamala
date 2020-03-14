package com.kuchamala

import com.google.api.client.auth.oauth2.Credential
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport
import com.google.api.client.http.javanet.NetHttpTransport
import com.kuchamala.sheets.GoogleSheetsReader
import com.kuchamala.sheets.GoogleAuthorization
import com.kuchamala.wp.page.*

fun main() {
    val httpTransport: NetHttpTransport = GoogleNetHttpTransport.newTrustedTransport()
    val credential = GoogleAuthorization().getCredentials(httpTransport)

    createMathClassPage(httpTransport, credential)
}

private fun createMathClassPage(httpTransport: NetHttpTransport, credential: Credential) {
    val data = GoogleSheetsReader(httpTransport, credential).run("Математика")

    var isLeft = true
    var descriptionRow = ""
    data.descriptions.forEach { description ->
        descriptionRow += createDescriptionRow(description, isLeft)
        isLeft  = !isLeft
    }

    var teacherRow = ""
    data.teachers.forEach { teacher ->
        teacherRow += createTeacherRow(teacher, isLeft)
        isLeft  = !isLeft
    }

    val headerRow = createHeaderRow(data.title, data.timetableAndPrice, data.image)
    val formRow = createFormRow(data.form, isLeft)

    val page = "$headerRow$descriptionRow$teacherRow$formRow".replace("\"", "\\\"")
    val url = "http://kuchamala.ru/classes-math"

    WordPressDatabaseWriter().run(
        pageContent = page,
        postTitle = "",
        postExcerpt = "",
        postStatus = "draft",
        commentStatus = "closed",
        pingStatus = "closed",
        toPing = "",
        pinged = "",
        postContentFiltered = "",
        guid = url,
        postType = "page"
    )
}
