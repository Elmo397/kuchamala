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

    createEmptyClassPage(httpTransport, credential)
//    createMathClassPage(httpTransport, credential)
}

private fun createEmptyClassPage(httpTransport: NetHttpTransport, credential: Credential) {
    val page = createPage(httpTransport, credential, "EmptyClass")
    val nameForUrl = "classes-empty"
    val pageStatus = "draft"

    println(page)
    WordPressDatabaseWriter().run(
        pageContent = page,
        postTitle = "",
        postExcerpt = "",
        postStatus = pageStatus,
        commentStatus = "closed",
        pingStatus = "closed",
        toPing = "",
        pinged = "",
        postContentFiltered = "",
        postName = nameForUrl,
        postType = "page"
    )
}

private fun createMathClassPage(httpTransport: NetHttpTransport, credential: Credential) {
    val page = createPage(httpTransport, credential, "Математика")
    val nameForUrl = "classes-math"
    val pageStatus = "draft"

    WordPressDatabaseWriter().run(
        pageContent = page,
        postTitle = "",
        postExcerpt = "",
        postStatus = pageStatus,
        commentStatus = "closed",
        pingStatus = "closed",
        toPing = "",
        pinged = "",
        postContentFiltered = "",
        postName = nameForUrl,
        postType = "page"
    )
}

private fun createPage(httpTransport: NetHttpTransport, credential: Credential, classTitle: String): String {
    val data = GoogleSheetsReader(httpTransport, credential).run(classTitle)

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

    return "$headerRow$descriptionRow$teacherRow$formRow".replace("\"", "\\\"")
}
