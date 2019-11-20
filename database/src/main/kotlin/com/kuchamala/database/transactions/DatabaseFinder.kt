package com.kuchamala.database.transactions

import com.kuchamala.database.entity.*
import org.jetbrains.exposed.sql.Op
import org.jetbrains.exposed.sql.transactions.transaction

fun findChildAges(op: Op<Boolean>): ChildAge? {
    var found: ChildAge? = null

    transaction {
        found = ChildAge.find { op }.singleOrNull()
    }

    return found
}

fun findClass(op: Op<Boolean>): Class? {
    var found: Class? = null

    transaction {
        found = Class.find { op }.singleOrNull()
    }

    return found
}

fun findEvent(op: Op<Boolean>): Event? {
    var found: Event? = null

    transaction {
        found = Event.find { op }.singleOrNull()
    }

    return found
}

fun findLocation(op: Op<Boolean>): Location? {
    var found: Location? = null

    transaction {
        found = Location.find { op }.singleOrNull()
    }

    return found
}

fun findTeacherClass(op: Op<Boolean>): TeacherClass? {
    var found: TeacherClass? = null

    transaction {
        found = TeacherClass.find { op }.singleOrNull()
    }

    return found
}

fun findTeacher(op: Op<Boolean>): Teacher? {
    var found: Teacher? = null

    transaction {
        found = Teacher.find { op }.singleOrNull()
    }

    return found
}

fun findTimetable(op: Op<Boolean>): TimetableC? {
    var found: TimetableC? = null

    transaction {
        found = TimetableC.find { op }.singleOrNull()
    }

    return found
}

fun findWeekDay(op: Op<Boolean>): WeekDay? {
    var found: WeekDay? = null

    transaction {
        found = WeekDay.find { op }.singleOrNull()
    }

    return found
}