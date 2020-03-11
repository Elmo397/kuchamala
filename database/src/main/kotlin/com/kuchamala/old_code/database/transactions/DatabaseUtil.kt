package com.kuchamala.old_code.database.transactions

import com.kuchamala.old_code.database.entity.*
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.StdOutSqlLogger
import org.jetbrains.exposed.sql.addLogger
import org.jetbrains.exposed.sql.transactions.transaction

fun connectToDb() {
    Database.connect(
        "jdbc:postgresql://localhost:5432/postgres",
        driver = "org.postgresql.Driver",
        user = "postgres",
        password = "postgres"
    )
}

fun createTables() {
    transaction {
        addLogger(StdOutSqlLogger)

        SchemaUtils.create(Teachers, ChildAges, Classes, Locations, WeekDays, Timetable, TeacherClasses, Events)
        commit()
    }
}