package com.kuchamala.old_code.database.model

import com.kuchamala.old_code.database.entity.ChildAgeEntity
import com.kuchamala.old_code.database.entity.ChildAges
import com.kuchamala.old_code.database.transactions.addChildAge
import com.kuchamala.old_code.database.transactions.findChildAges
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.and

class ChildAgesModel {
    fun addData(minAge: Int?, maxAge: Int?): ChildAgeEntity? {
        val op = (ChildAges.minAge eq minAge) and (ChildAges.maxAge eq maxAge)
        val found = findChildAges(op)

        return found ?: addChildAge(minAge, maxAge)
    }
}