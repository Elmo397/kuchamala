package com.kuchamala.database

import com.kuchamala.database.entity.*
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.StdOutSqlLogger
import org.jetbrains.exposed.sql.addLogger
import org.jetbrains.exposed.sql.transactions.transaction

fun createTables() {
    connectToDb()

    transaction {
        addLogger(StdOutSqlLogger)

        SchemaUtils.create(Teachers, ChildAge, Classes, Locations, WeekDays, Timetable)
        commit()
    }
}

fun createTimetableRow(
    classTitle: String,
    teacherName: String,
    teacherSurname: String,
    age: String?,
    weekDay: String,
    time: String,
    location: String
) {
    connectToDb()

    transaction {
        addLogger(StdOutSqlLogger)
    }
}

fun addClass(
    title: String,
    description: String?,
    duration: Int,
    subscriptionPrice: Int?,
    singleClassPrice: Int,
    freeFirstClass: Boolean
) {
    connectToDb()
    //todo: check if exist
    transaction {
        addLogger(StdOutSqlLogger)

        Class.new {
            this.title = title
            this.description = description
            this.duration = duration
            this.subscriptionPrice = subscriptionPrice
            this.singleClassPrice = singleClassPrice
            this.freeFirstClass = freeFirstClass
        }

        commit()
    }
}

fun addTeacher(
    name: String,
    surname: String,
    patronymic: String?,
    aboutTeacher: String?
) {
    connectToDb()
    //todo: check if exist
    transaction {
        addLogger(StdOutSqlLogger)

        Teacher.new {
            this.name = name
            this.surname = surname
            this.patronymic = patronymic
            this.aboutTeacher = aboutTeacher
        }
    }
}

private fun connectToDb() {
    Database.connect(
        "jdbc:postgresql://localhost:5432/postgres",
        driver = "org.postgresql.Driver",
        user = "postgres",
        password = "postgres"
    )
}