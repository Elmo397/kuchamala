package com.kuchamala.sheets.reader

import com.google.api.services.sheets.v4.Sheets
import com.kuchamala.wp.page.*

class ClassData(private val service: Sheets, private val spreadsheetId: String, private val listTitle: String = "Лист1") {
    fun read() = ClassPageData(
        getHeaderTitle(),
        getPriceText(),
        getTimetable(),
        getImage("D3"),
        getDescriptions(),
        getTeachers(),
        getDiplomas(),
        getFormId()
    )

    private fun getHeaderTitle(): String {
        val range = "$listTitle!B3"
        val response = service.spreadsheets().values().get(spreadsheetId, range).execute()

        return response.getValues()[0][0].toString()
    }

    private fun getPriceText(): String {
        val range = "$listTitle!C3"
        val response = service.spreadsheets().values().get(spreadsheetId, range).execute()

        return response.getValues()[0][0].toString()
    }

    private fun getTimetable(): List<ClassGroup> {
        val groups = mutableListOf<ClassGroup>()

        val listValues = service.spreadsheets().values()
        var rowNumb = 21
        var row = listValues.get(spreadsheetId, "$listTitle!A$rowNumb:Q$rowNumb").execute().getValues()

        while (row != null) {
            groups.add(
                ClassGroup(
                    minAge = row[0][0].toString().toInt(),
                    maxAge = if (row[0][1] != "") row[0][1].toString().toInt() else null,
                    timetable = getGroupTimetable(row[0]),
                    comment = if (row[0][2] != "") row[0][2].toString() else null
                )
            )

            rowNumb++
            row = listValues.get(spreadsheetId, "$listTitle!A$rowNumb:Q$rowNumb").execute().getValues()
        }

        return groups
    }

    private fun getGroupTimetable(row: List<Any>): List<GroupTimetable> {
        val groupsTimetable = mutableListOf<GroupTimetable>()
        val weekDayRow =
            service.spreadsheets().values().get(spreadsheetId, "$listTitle!A19:Q19").execute().getValues()[0]

        for (cell in 3..row.lastIndex step 2) {
            if (row[cell] != "") {
                groupsTimetable.add(
                    GroupTimetable(
                        weekDay = weekDayRow[cell].toString(),
                        timeStart = row[cell].toString().split(","),
                        timeEnd = row[cell + 1].toString().split(",")
                    )
                )
            }
        }

        return groupsTimetable
    }

    private fun getDescriptions(): List<Description> {
        val descriptions = mutableListOf<Description>()
        val titleRange = "$listTitle!B"
        val textRange = "$listTitle!C"

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
                val image = getImage("D$rowNumb")

                descriptions.add(Description(title, text, image))
            }
        }

        return descriptions
    }

    private fun getTeachers(): List<Teacher> {
        val teachers = mutableListOf<Teacher>()
        val nameRange = "$listTitle!B"
        val textRange = "$listTitle!C"
        val spreadsheets = service.spreadsheets().values()

        for (rowNumb in 9..13) {
            val nameRow = spreadsheets.get(spreadsheetId, "$nameRange$rowNumb").execute().getValues()
            if (nameRow != null) {
                val image = getImage("D$rowNumb")
                val name = nameRow[0][0].toString()
                val text = spreadsheets.get(spreadsheetId, "$textRange$rowNumb").execute().getValues()[0][0].toString()

                teachers.add(Teacher(name, text, image))
            }
        }

        return teachers
    }

    private fun getDiplomas(): Diplomas {
        val listValues = service.spreadsheets().values()
        val range = "$listTitle!D"

        val diplomas = mutableListOf<Image>()
        val title = listValues.get(spreadsheetId, "$listTitle!B14").execute().getValues()[0][0].toString()

        for (rowNumb in 14..16) {
            val row = listValues.get(spreadsheetId, "$range$rowNumb").execute().getValues()
            if (row != null) {
                val image = getImage("D$rowNumb")
                diplomas.add(image)
            }
        }

        return Diplomas(title, diplomas)
    }

    private fun getImage(cell: String): Image {
        val range = "$listTitle!$cell"
        val response = service.spreadsheets().values().get(spreadsheetId, range).execute()
        val id = response.getValues()[0][0].toString().toInt()

        return Image(id)
    }

    private fun getFormId(): Form {
        val range = "$listTitle!B17"
        val response = service.spreadsheets().values().get(spreadsheetId, range).execute()
        val id = response.getValues()[0][0].toString().toInt()

        return Form(id)
    }
}