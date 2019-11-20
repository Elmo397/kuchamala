package com.kuchamala.database.model

import com.kuchamala.database.entity.Teacher
import com.kuchamala.database.entity.Teachers
import com.kuchamala.database.transactions.addTeacher
import com.kuchamala.database.transactions.connectToDb
import com.kuchamala.database.transactions.findTeacher
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.and
import org.jetbrains.exposed.sql.transactions.transaction

class Teachers {
    fun addData(surname: String, name: String, patronymic: String?, aboutTeacher: String?): Teacher? {
        val op = (Teachers.name eq name) and (Teachers.surname eq surname)
        val found = findTeacher(op)

        return found ?: addTeacher(name, surname, patronymic, aboutTeacher)
    }
}