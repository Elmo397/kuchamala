package com.kuchamala.database.transactions

import com.kuchamala.database.entity.*
import org.jetbrains.exposed.sql.StdOutSqlLogger
import org.jetbrains.exposed.sql.addLogger
import org.jetbrains.exposed.sql.transactions.transaction
import org.joda.time.DateTime

fun addClass(
    title: String,
    description: String?,
    duration: Int,
    subscriptionPrice: Int?,
    singleClassPrice: Int,
    freeFirstClass: Boolean,
    age: ChildAgeEntity
): ClassEntity? {
    var result: ClassEntity? = null

    transaction {
        addLogger(StdOutSqlLogger)

        result = ClassEntity.new {
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
): TeacherEntity? {
    var result: TeacherEntity? = null

    transaction {
        addLogger(StdOutSqlLogger)

        result = TeacherEntity.new {
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
): ChildAgeEntity? {
    var result: ChildAgeEntity? = null

    transaction {
        addLogger(StdOutSqlLogger)

        result = ChildAgeEntity.new {
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
): LocationEntity? {
    var result: LocationEntity? = null

    transaction {
        addLogger(StdOutSqlLogger)

        result = LocationEntity.new {
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

fun addWeekDay(title: String): WeekDayEntity? {
    var result: WeekDayEntity? = null

    transaction {
        addLogger(StdOutSqlLogger)

        result = WeekDayEntity.new {
            this.title = title
        }

        commit()
    }

    return result
}

fun addTeacherClasses(
    teacher: TeacherEntity,
    clazz: ClassEntity
): TeacherClassEntity? {
    var result: TeacherClassEntity? = null

    transaction {
        addLogger(StdOutSqlLogger)

        result = TeacherClassEntity.new {
            this.teacher = teacher
            this.clazz = clazz
        }

        commit()
    }

    return result
}

fun addTimetable(
    clazz: ClassEntity,
    teacher: TeacherEntity,
    age: ChildAgeEntity,
    weekDay: WeekDayEntity,
    startTime: DateTime,
    endTime: DateTime,
    location: LocationEntity
): TimetableEntity? {
    var result: TimetableEntity? = null

    transaction {
        addLogger(StdOutSqlLogger)

        result = TimetableEntity.new {
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
): EventEntity? {
    var result: EventEntity? = null

    transaction {
        addLogger(StdOutSqlLogger)

        result = EventEntity.new {
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