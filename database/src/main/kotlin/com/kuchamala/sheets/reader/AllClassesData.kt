package com.kuchamala.sheets.reader

import com.google.api.services.sheets.v4.Sheets
import com.kuchamala.wp.database.getPostName
import com.kuchamala.wp.page.AllClassesPageData
import com.kuchamala.wp.page.ClassPreview
import com.kuchamala.wp.page.Image
import java.time.LocalTime
import java.time.temporal.ChronoUnit


class AllClassesData(private val service: Sheets,  private val spreadsheetId: String, private val listTitle: String = "Лист1") {
    fun read(allTablesId: Map<String, String>): AllClassesPageData {
        val listValues = service.spreadsheets().values()
        val classes = getClasses(listValues, allTablesId)
        val header = listValues.get(spreadsheetId, "$listTitle!A3:C3").execute().getValues()

        return AllClassesPageData(
            title = header[0][0].toString(),
            text = header[0][1].toString(),
            classesPreview = classes,
            image = Image(header[0][2].toString().toInt())
        )
    }

    private fun getClasses(listValues: Sheets.Spreadsheets.Values, allTablesId: Map<String, String>): List<ClassPreview> {
        val classes = mutableListOf<ClassPreview>()

        var rowNumb = 6
        var row = listValues.get(spreadsheetId, "$listTitle!A$rowNumb:F$rowNumb").execute().getValues()

        while (row != null) {
            val title = row[0][0].toString()
            val tableId = allTablesId[title.toLowerCase()] ?: error("table id with name \"$title\" is not exist")
            println(title)

            classes.add(
                ClassPreview(
                    title = title,
                    description = row[0][1].toString(),
                    duration = getDuration(tableId),
                    minAge = getMinAge(tableId),
                    maxAge = getMaxAge(tableId),
                    className = getPostName(title),
                    image = getImage(tableId)
                )
            )

            rowNumb++
            row = listValues.get(spreadsheetId, "$listTitle!A$rowNumb:F$rowNumb").execute().getValues()
            Thread.sleep(60000) //TODO: !!!
        }

        return classes
    }

    private fun getImage(tableId: String, classTitle: String = "Лист1"): Image {
        val range = "$classTitle!D3"
        val response = service.spreadsheets().values().get(tableId, range).execute()
        val id = response.getValues()[0][0].toString().toInt()

        return Image(id)
    }

    private fun getMinAge(tableId: String, classTitle: String = "Лист1") = createAgesList(classTitle, "A", tableId).min()!!

    private fun getMaxAge(tableId: String, classTitle: String = "Лист1") = createAgesList(classTitle, "B", tableId).max()!!

    private fun createAgesList(classTitle: String, column: String, tableId: String): List<Int> {
        val ages = mutableListOf<Int>()
        val listValues = service.spreadsheets().values()
        var rowNumb = 21
        var row = listValues.get(tableId, "$classTitle!$column$rowNumb").execute().getValues()

        while (row != null) {
            ages.add(row[0][0].toString().toInt())
            rowNumb++
            row = listValues.get(tableId, "$classTitle!$column$rowNumb").execute().getValues()
        }

        return ages
    }

    private fun getDuration(tableId: String, classTitle: String = "Лист1"): Int {
        val row = service.spreadsheets().values().get(tableId, "$classTitle!D21:Q21").execute().getValues()
        val times = row[0].filter { it != "" }
        val startTime = LocalTime.parse(times[0].toString())
        val endTime = LocalTime.parse(times[1].toString())

        return ChronoUnit.MINUTES.between(startTime, endTime).toInt()
    }
}