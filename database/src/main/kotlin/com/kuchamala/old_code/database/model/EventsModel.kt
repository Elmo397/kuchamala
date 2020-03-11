package com.kuchamala.old_code.database.model

import com.kuchamala.old_code.database.entity.EventEntity
import com.kuchamala.old_code.database.entity.Events
import com.kuchamala.old_code.database.transactions.addEvent
import com.kuchamala.old_code.database.transactions.findEvent
import org.joda.time.DateTime
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.joda.time.format.DateTimeFormat

class EventsModel {
    fun addData(
        title: String,
        eventDetails: String,
        date: String,
        weekDay: String,
        timeStart: String,
        timeEnd: String,
        place: String,
        address: String,
        price: Int
    ): EventEntity? {
        val dateTimeStart = DateTime.parse("$date $timeStart", DateTimeFormat.forPattern("dd/mm/yyyy HH:mm"))
        val dateTimeEnd = DateTime.parse("$date $timeEnd", DateTimeFormat.forPattern("dd/mm/yyyy HH:mm"))
        val op = Events.title eq title
        val found = findEvent(op)

        return found ?: addEvent(title, eventDetails, dateTimeStart, dateTimeEnd, weekDay, place, address, price)
    }
}