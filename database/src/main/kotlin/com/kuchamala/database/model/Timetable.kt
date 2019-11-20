package com.kuchamala.database.model

import com.kuchamala.database.entity.Classes
import com.kuchamala.database.entity.Teacher
import com.kuchamala.database.transactions.addTimetable
import com.kuchamala.database.transactions.findClass
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.joda.time.DateTime
import org.joda.time.format.DateTimeFormat

class Timetable {
    fun addData(
        classTitle: String,
        teacherStr: String,
        minAge: Int?,
        maxAge: Int?,
        weekDayStr: String,
        startTimeStr: String,
        endTimeStr: String,
        place: String,
        metroStation: String?,
        street: String?,
        houseNumber: Int?,
        building: Int?
    ) {
        val classOp = Classes.title eq classTitle
        val clazz = findClass(classOp)
        val teacher = parseTeacherName(teacherStr)
        val age = ChildAges().addData(minAge, maxAge)
        val weekDay = WeekDays().addData(weekDayStr)
        val startTime = DateTime.parse(startTimeStr, DateTimeFormat.forPattern("HH:mm"))
        val endTime = DateTime.parse(endTimeStr, DateTimeFormat.forPattern("HH:mm"))
        val location = Locations().addData(place, metroStation, street, houseNumber, building)

        require(clazz != null && age != null && weekDay != null && location != null)
        addTimetable(clazz, teacher, age, weekDay, startTime, endTime, location)
    }

    private fun parseTeacherName(teacherStr: String): Teacher {
        val items = teacherStr.split(" ")
        val found = getTeacher(items)

        require(found != null)
        return found
    }
}