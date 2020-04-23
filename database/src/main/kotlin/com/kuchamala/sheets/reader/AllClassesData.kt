package com.kuchamala.sheets.reader

import com.google.api.services.sheets.v4.Sheets
import com.kuchamala.wp.database.getPostName
import com.kuchamala.wp.page.AllClassesPage
import com.kuchamala.wp.page.ClassPreview
import com.kuchamala.wp.page.Image
import java.text.SimpleDateFormat
import java.time.LocalTime
import java.time.temporal.ChronoUnit


class AllClassesData(private val service: Sheets, private val listTitle: String, private val spreadsheetId: String) {
    fun read(): AllClassesPage {
        val listValues = service.spreadsheets().values()
        val classes = getClasses(listValues)
        val header = listValues.get(spreadsheetId, "$listTitle!A3:C3").execute().getValues()

        return AllClassesPage(
            title = header[0][0].toString(),
            text = header[0][1].toString(),
            classesPreview = classes,
            image = Image(header[0][2].toString().toInt())
        )
    }

    private fun getClasses(listValues: Sheets.Spreadsheets.Values): List<ClassPreview> {
        val classes = mutableListOf<ClassPreview>()

        var rowNumb = 6
        var row = listValues.get(spreadsheetId, "$listTitle!A$rowNumb:F$rowNumb").execute().getValues()

        while (row != null) {
            val title = row[0][0].toString()

            classes.add(
                ClassPreview(
                    title = title,
                    description = row[0][1].toString(),
                    duration = getDuration(title),
                    minAge = getMinAge(title),
                    maxAge = getMaxAge(title),
                    className = getPostName(title),
                    image = getImage(title)
                )
            )

            rowNumb++
            row = listValues.get(spreadsheetId, "$listTitle!A$rowNumb:F$rowNumb").execute().getValues()
        }

        return classes
    }

    private fun getImage(classTitle: String): Image {
        val range = "$classTitle!D3"
        val response = service.spreadsheets().values().get(spreadsheetId, range).execute()
        val id = response.getValues()[0][0].toString().toInt()

        return Image(id)
    }

    private fun getMinAge(classTitle: String) = createAgesList(classTitle, "A").min()!!

    private fun getMaxAge(classTitle: String) = createAgesList(classTitle, "B").max()!!

    private fun createAgesList(classTitle: String, column: String): List<Int> {
        val ages = mutableListOf<Int>()
        val listValues = service.spreadsheets().values()
        var rowNumb = 21
        var row = listValues.get(spreadsheetId, "$classTitle!$column$rowNumb").execute().getValues()

        while (row != null) {
            ages.add(row[0][0].toString().toInt())
            rowNumb++
            row = listValues.get(spreadsheetId, "$classTitle!$column$rowNumb").execute().getValues()
        }

        return ages
    }

    private fun getDuration(classTitle: String): Int {
        val row = service.spreadsheets().values().get(spreadsheetId, "$classTitle!D21:Q21").execute().getValues()
        val times = row[0].filter { it != "" }
        val startTime = LocalTime.parse(times[0].toString())
        val endTime = LocalTime.parse(times[1].toString())

        return ChronoUnit.MINUTES.between(startTime, endTime).toInt()
    }
}