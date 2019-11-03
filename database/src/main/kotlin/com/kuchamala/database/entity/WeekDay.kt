package com.kuchamala.database.entity

import org.jetbrains.exposed.dao.EntityID
import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.IntIdTable

object WeekDays : IntIdTable() {
    val title = varchar("title", 256)
}

class WeekDay(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<WeekDay>(WeekDays)

    var title by WeekDays.title
}