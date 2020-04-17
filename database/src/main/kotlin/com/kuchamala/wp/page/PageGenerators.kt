package com.kuchamala.wp.page

import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport
import com.google.api.client.http.javanet.NetHttpTransport
import com.kuchamala.sheets.GoogleAuthorization
import com.kuchamala.sheets.GoogleSheetsReader

fun createEmptyClassPage() {
    val page = createPage("EmptyClass", "empty")
    val nameForUrl = "classes-empty"
    val pageStatus = "draft"

    insertPost(
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

fun createMathClassPage() {
    val page = createPage("Математика", "math")
    val nameForUrl = "classes-math"
    val pageStatus = "draft"

    insertPost(
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

private fun createPage(classTitle: String, className: String): String {
    val httpTransport: NetHttpTransport = GoogleNetHttpTransport.newTrustedTransport()
    val credential = GoogleAuthorization().getCredentials(httpTransport)
    val data = GoogleSheetsReader(httpTransport, credential).run(classTitle)

    val priceId = "$className-price"
    val timetableId = "$className-timetable"

    createTextBlocks(data.price.replace("\"", "\\\""), priceId, "publish", priceId)
    createTextBlocks(data.timetable.replace("\"", "\\\""), timetableId, "publish", timetableId)

    val timetableAndPrice = """[text-blocks id="$priceId"]
                                |<h4>Расписание:</h4>
                                |[text-blocks id="$timetableId"]""".trimMargin()

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

private fun createTextBlocks(content: String, title: String, status: String, name: String) {
    insertPost(
        pageContent = content,
        postTitle = title,
        postExcerpt = "",
        postStatus = status,
        commentStatus = "closed",
        pingStatus = "closed",
        toPing = "",
        pinged = "",
        postContentFiltered = "",
        postName = name,
        postType = "text-blocks"
    )
}
