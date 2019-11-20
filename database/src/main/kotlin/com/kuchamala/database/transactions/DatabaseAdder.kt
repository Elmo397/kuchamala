package com.kuchamala.database.transactions

import com.kuchamala.database.entity.*
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SizedCollection
import org.jetbrains.exposed.sql.StdOutSqlLogger
import org.jetbrains.exposed.sql.addLogger
import org.jetbrains.exposed.sql.transactions.transaction
import org.joda.time.DateTime
import java.time.LocalTime

fun addClass(
    title: String,
    description: String?,
    duration: Int,
    subscriptionPrice: Int?,
    singleClassPrice: Int,
    freeFirstClass: Boolean,
    age: ChildAge
): Class? {
    var result: Class? = null

    transaction {
        addLogger(StdOutSqlLogger)

        result = Class.new {
            this.title = title
            this.description = description
            this.duration = duration
            this.subscriptionPrice = subscriptionPrice
            this.singleClassPrice = singleClassPrice
            this.freeFirstClass = freeFirstClass
            this.age = age
        }

        commit()
    }

    return result
}

fun addTeacher(
    name: String,
    surname: String,
    patronymic: String?,
    aboutTeacher: String?
): Teacher? {
    var result: Teacher? = null

    transaction {
        addLogger(StdOutSqlLogger)

        result = Teacher.new {
            this.name = name
            this.surname = surname
            this.patronymic = patronymic
            this.aboutTeacher = aboutTeacher
        }

        commit()
    }

    return result
}

fun addChildAge(
    minAge: Int?,
    maxAge: Int?
): ChildAge? {
    var result: ChildAge? = null

    transaction {
        addLogger(StdOutSqlLogger)

        result = ChildAge.new {
            this.minAge = minAge
            this.maxAge = maxAge
        }

        commit()
    }

    return result
}

fun addLocation(
    place: String,
    metroStation: String?,
    street: String?,
    houseNumber: Int?,
    building: Int?
): Location? {
    var result: Location? = null

    transaction {
        addLogger(StdOutSqlLogger)

        result = Location.new {
            this.place = place
            this.metroStation = metroStation
            this.street = street
            this.houseNumber = houseNumber
            this.building = building
        }

        commit()
    }

    return result
}

fun addWeekDay(title: String): WeekDay? {
    var result: WeekDay? = null

    transaction {
        addLogger(StdOutSqlLogger)

        result = WeekDay.new {
            this.title = title
        }

        commit()
    }

    return result
}

fun addTeacherClasses(
    teacher: Teacher,
    clazz: Class
): TeacherClass? {
    var result: TeacherClass? = null

    transaction {
        addLogger(StdOutSqlLogger)

        result = TeacherClass.new {
            this.teacher = teacher
            this.clazz = clazz
        }

        commit()
    }

    return result
}

fun addTimetable(
    clazz: Class,
    teacher: Teacher,
    age: ChildAge,
    weekDay: WeekDay,
    startTime: DateTime,
    endTime: DateTime,
    location: Location
): TimetableC? {
    var result: TimetableC? = null

    transaction {
        addLogger(StdOutSqlLogger)

        result = TimetableC.new {
            this.clazz = clazz
            this.teacher = teacher
            this.age = age
            this.weekDay = weekDay
            this.startTime = startTime
            this.endTime = endTime
            this.location = location
        }

        commit()
    }

    return result
}

fun addEvent(
    title: String,
    eventDetails: String,
    dateTimeStart: DateTime,
    dateTimeEnd: DateTime,
    weekDay: String,
    place: String,
    address: String,
    price: Int
): Event? {
    var result: Event? = null

    transaction {
        addLogger(StdOutSqlLogger)

        result = Event.new {
            this.title = title
            this.eventDetails = eventDetails
            this.dateTimeStart = dateTimeStart
            this.dateTimeEnd = dateTimeEnd
            this.weekDay = weekDay
            this.place = place
            this.address = address
            this.price = price
        }

        commit()
    }

    return result
}