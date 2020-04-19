package com.kuchamala.wp.page

import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport
import com.google.api.client.http.javanet.NetHttpTransport
import com.kuchamala.sheets.GoogleAuthorization
import com.kuchamala.sheets.GoogleSheetsReader

fun createWordpressPage(tabName: String, url: String, pageStatus: String = "draft") {
    val page = createPage(tabName)

    insertPost(
            pageContent = page,
            postTitle = tabName,
            postExcerpt = "",
            postStatus = pageStatus,
            commentStatus = "closed",
            pingStatus = "closed",
            toPing = "",
            pinged = "",
            postContentFiltered = "",
            postName = url,
            postType = "page"
    )
}

private fun createPage(classTitle: String): String {
    val httpTransport: NetHttpTransport = GoogleNetHttpTransport.newTrustedTransport()
    val credential = GoogleAuthorization().getCredentials(httpTransport)
    val data = GoogleSheetsReader(httpTransport, credential).run(classTitle)

    val timetableAndPrice = """${data.price}
                                |<h4>Расписание:</h4>
                                |${data.timetable}
                                |""".trimMargin()

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

    val headerRow = createHeaderRow(data.title, timetableAndPrice, data.image)
    val formRow = createFormRow(data.form, isLeft)

    return "$headerRow$descriptionRow$teacherRow$formRow".replace("\"", "\\\"")
}
