package com.kuchamala.database.entity

import org.jetbrains.exposed.dao.EntityID
import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.IntIdTable

object Timetable : IntIdTable() {
    val clazz = reference("class", Classes)
    val teacher = reference("teacher", Teachers)
    val age = reference("age", ChildAges)
    val weekDay = reference("week_day", WeekDays)
    val startTime = datetime("start_time") //todo: can i use just time without date?
    val endTime = datetime("end_time") //todo: same question
    val location = reference("location", Locations)
}

class TimetableEntity(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<TimetableEntity>(Timetable)

    var clazz by ClassEntity referencedOn Timetable.clazz
    var teacher by TeacherEntity referencedOn Timetable.teacher
    var age by ChildAgeEntity referencedOn Timetable.age
    var weekDay by WeekDayEntity referencedOn Timetable.weekDay
    var startTime by Timetable.startTime
    var endTime by Timetable.endTime
    var location by LocationEntity referencedOn Timetable.location
}