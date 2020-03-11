package com.kuchamala.old_code.database.entity

import org.jetbrains.exposed.dao.EntityID
import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.IntIdTable

object WeekDays : IntIdTable() {
    val title = varchar("title", 15)
}

class WeekDayEntity(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<WeekDayEntity>(WeekDays)

    var title by WeekDays.title
}