package com.kuchamala.database.entity

import org.jetbrains.exposed.dao.EntityID
import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.IntIdTable

object Events : IntIdTable() {
    val title = varchar("title", 256)
    val eventDetails  = varchar("event_details", 2000)
    val dateTimeStart = datetime("start")
    val dateTimeEnd = datetime("end")
    val weekDay = varchar("week_day", 15)
    val place = varchar("place", 100)
    val address = varchar("address", 100)
    val price = integer("price")
}

class EventEntity(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<EventEntity>(Events)

    var title by Events.title
    var eventDetails by Events.eventDetails
    var dateTimeStart by Events.dateTimeStart
    var dateTimeEnd by Events.dateTimeEnd
    var weekDay by Events.weekDay
    var place by Events.place
    var address by Events.address
    var price by Events.price
}