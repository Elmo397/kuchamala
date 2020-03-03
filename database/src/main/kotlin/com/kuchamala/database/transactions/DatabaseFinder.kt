package com.kuchamala.database.transactions

import com.kuchamala.database.entity.*
import org.jetbrains.exposed.sql.Op
import org.jetbrains.exposed.sql.transactions.transaction

fun findChildAges(op: Op<Boolean>): ChildAgeEntity? {
    var found: ChildAgeEntity? = null

    transaction {
        found = ChildAgeEntity.find { op }.singleOrNull()
    }

    return found
}

fun findClass(op: Op<Boolean>): ClassEntity? {
    var found: ClassEntity? = null

    transaction {
        found = ClassEntity.find { op }.singleOrNull()
    }

    return found
}

fun findEvent(op: Op<Boolean>): EventEntity? {
    var found: EventEntity? = null

    transaction {
        found = EventEntity.find { op }.singleOrNull()
    }

    return found
}

fun findLocation(op: Op<Boolean>): LocationEntity? {
    var found: LocationEntity? = null

    transaction {
        found = LocationEntity.find { op }.singleOrNull()
    }

    return found
}

fun findTeacherClass(op: Op<Boolean>): TeacherClassEntity? {
    var found: TeacherClassEntity? = null

    transaction {
        found = TeacherClassEntity.find { op }.singleOrNull()
    }

    return found
}

fun findTeacher(op: Op<Boolean>): TeacherEntity? {
    var found: TeacherEntity? = null

    transaction {
        found = TeacherEntity.find { op }.singleOrNull()
    }

    return found
}

fun findTimetable(op: Op<Boolean>): TimetableEntity? {
    var found: TimetableEntity? = null

    transaction {
        found = TimetableEntity.find { op }.singleOrNull()
    }

    return found
}

fun findWeekDay(op: Op<Boolean>): WeekDayEntity? {
    var found: WeekDayEntity? = null

    transaction {
        found = WeekDayEntity.find { op }.singleOrNull()
    }

    return found
}