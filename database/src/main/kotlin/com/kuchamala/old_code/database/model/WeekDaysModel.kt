package com.kuchamala.old_code.database.model

import com.kuchamala.old_code.database.entity.WeekDayEntity
import com.kuchamala.old_code.database.entity.WeekDays
import com.kuchamala.old_code.database.transactions.addWeekDay
import com.kuchamala.old_code.database.transactions.findWeekDay
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq

class WeekDaysModel {
    fun addData(day: String): WeekDayEntity? {
        val op = WeekDays.title eq day
        val found = findWeekDay(op)

        return found ?: addWeekDay(day)
    }
}