package com.kuchamala.old_code.database.model

import com.kuchamala.old_code.database.entity.Classes
import com.kuchamala.old_code.database.entity.TeacherEntity
import com.kuchamala.old_code.database.transactions.addTimetable
import com.kuchamala.old_code.database.transactions.findClass
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.joda.time.DateTime
import org.joda.time.format.DateTimeFormat

class TimetableModel {
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
        val age = ChildAgesModel().addData(minAge, maxAge)
        val weekDay = WeekDaysModel().addData(weekDayStr)
        val startTime = DateTime.parse(startTimeStr, DateTimeFormat.forPattern("HH:mm"))
        val endTime = DateTime.parse(endTimeStr, DateTimeFormat.forPattern("HH:mm"))
        val location = LocationsModel().addData(place, metroStation, street, houseNumber, building)

        require(clazz != null && age != null && weekDay != null && location != null)
        addTimetable(clazz, teacher, age, weekDay, startTime, endTime, location)
    }

    private fun parseTeacherName(teacherStr: String): TeacherEntity {
        val items = teacherStr.split(" ")
        val found = getTeacher(items)

        require(found != null)
        return found
    }
}