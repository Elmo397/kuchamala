package com.kuchamala.wp.page

import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport
import com.google.api.client.http.javanet.NetHttpTransport
import com.kuchamala.sheets.GoogleAuthorization
import com.kuchamala.sheets.GoogleSheetsReader
import com.kuchamala.wp.database.writeToDatabase

fun createClassPage(tabName: String, className: String, pageStatus: String = "draft") {
    val page = generatePage(tabName)

    writeToDatabase(
        postContent = page,
        postTitle = tabName,
        postStatus = pageStatus,
        postName = className,
        postType = "page"
    )
}

fun createAllClassesPage() {
    val httpTransport: NetHttpTransport = GoogleNetHttpTransport.newTrustedTransport()
    val credential = GoogleAuthorization().getCredentials(httpTransport)
    val data = GoogleSheetsReader(httpTransport, credential).readAllClassesData("Все занятия")

    val page = generateAllClassesPage(data).replace("\"", "\\\"")
    writeToDatabase(
        postContent = page,
        postTitle = "Все занятия",
        postStatus = "draft",
        postName = "classes",
        postType = "page"
    )
}