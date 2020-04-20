package com.kuchamala.sheets

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

    fun readAllClassesData(listTitle: String): AllClassesPage {
        val listValues = service.spreadsheets().values()
        val classes = getClasses(listValues, listTitle)
        val header = listValues.get(spreadsheetId, "$listTitle!A3:C3").execute().getValues()

        return AllClassesPage(
            title = header[0][0].toString(),
            text = header[0][1].toString(),
            classesPreview = classes,
            image = Image(header[0][2].toString().toInt())
        )
    }

    fun readClassData(classListTitle: String) = ClassData(
        getHeaderTitle(service, classListTitle),
        getPriceText(service, classListTitle),
        getTimetableText(service, classListTitle),
        getImage(service, classListTitle, "D2"),
        getDescriptions(service, classListTitle),
        getTeachers(service, classListTitle),
        getFormId(service, classListTitle)
    )

    private fun getClasses(listValues: Sheets.Spreadsheets.Values, listTitle: String): List<ClassPreview> {
        val classes = mutableListOf<ClassPreview>()

        var rowNumb = 7
        var row = listValues.get(spreadsheetId, "$listTitle!A$rowNumb:F$rowNumb").execute().getValues()

        while (row != null) {
            val title = row[0][0].toString()

            classes.add(
                ClassPreview(
                    title = title,
                    description = row[0][1].toString(),
                    duration = row[0][2].toString().toInt(),
                    minAge = row[0][3].toString().toInt(),
                    maxAge = row[0][4].toString().toInt(),
                    className = getPostName(title),
                    image = Image(row[0][5].toString().toInt())
                )
            )

            rowNumb++
            row = listValues.get(spreadsheetId, "$listTitle!A$rowNumb:F$rowNumb").execute().getValues()
        }

        return classes
    }

    private fun getHeaderTitle(service: Sheets, classListTitle: String): String {
        val range = "$classListTitle!B2"
        val response = service.spreadsheets().values().get(spreadsheetId, range).execute()

        return response.getValues()[0][0].toString()
    }

    private fun getPriceText(service: Sheets, classListTitle: String): String {
        val range = "$classListTitle!C2"
        val response = service.spreadsheets().values().get(spreadsheetId, range).execute()

        return response.getValues()[0][0].toString()
    }

    private fun getTimetableText(service: Sheets, classListTitle: String): String {
        val range = "$classListTitle!C3"
        val response = service.spreadsheets().values().get(spreadsheetId, range).execute()

        return response.getValues()[0][0].toString()
    }

    private fun getDescriptions(service: Sheets, classListTitle: String): List<Description> {
        val descriptions = mutableListOf<Description>()
        val titleRange = "$classListTitle!B"
        val textRange = "$classListTitle!C"

        for (rowNumb in 4..8) {
            var title: String? = null
            val titleRow = service.spreadsheets().values()
                .get(spreadsheetId, "$titleRange$rowNumb")
                .execute().getValues()

            if (titleRow != null) {
                title = titleRow[0][0].toString()
            }

            val textRow = service.spreadsheets().values()
                .get(spreadsheetId, "$textRange$rowNumb")
                .execute().getValues()

            if (textRow != null) {
                val text = textRow[0][0].toString()
                val image = getImage(service, classListTitle, "D$rowNumb")

                descriptions.add(Description(title, text, image))
            }
        }

        return descriptions
    }

    private fun getTeachers(service: Sheets, classListTitle: String): List<Teacher> {
        val teachers = mutableListOf<Teacher>()
        val nameRange = "$classListTitle!B"
        val textRange = "$classListTitle!C"
        val spreadsheets = service.spreadsheets().values()

        for (rowNumb in 9..13) {
            val nameRow = spreadsheets.get(spreadsheetId, "$nameRange$rowNumb").execute().getValues()
            if (nameRow != null) {
                val image = getImage(service, classListTitle, "D$rowNumb")
                val name = nameRow[0][0].toString()
                val text = spreadsheets.get(spreadsheetId, "$textRange$rowNumb").execute().getValues()[0][0].toString()

                teachers.add(Teacher(name, text, image))
            }
        }

        return teachers
    }

    private fun getImage(service: Sheets, classListTitle: String, cell: String): Image {
        val range = "$classListTitle!$cell"
        val response = service.spreadsheets().values().get(spreadsheetId, range).execute()
        val id = response.getValues()[0][0].toString().toInt()

        return Image(id)
    }

    private fun getFormId(service: Sheets, classListTitle: String): Form {
        val range = "$classListTitle!B14"
        val response = service.spreadsheets().values().get(spreadsheetId, range).execute()
        val id = response.getValues()[0][0].toString().toInt()

        return Form(id)
    }
}