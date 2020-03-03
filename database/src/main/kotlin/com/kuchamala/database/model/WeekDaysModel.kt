package com.kuchamala.database.model

import com.kuchamala.database.entity.WeekDayEntity
import com.kuchamala.database.entity.WeekDays
import com.kuchamala.database.transactions.addWeekDay
import com.kuchamala.database.transactions.findWeekDay
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq

class WeekDaysModel {
    fun addData(day: String): WeekDayEntity? {
        val op = WeekDays.title eq day
        val found = findWeekDay(op)

        return found ?: addWeekDay(day)
    }
}