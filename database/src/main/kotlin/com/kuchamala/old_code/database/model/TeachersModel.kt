package com.kuchamala.old_code.database.model

import com.kuchamala.old_code.database.entity.TeacherEntity
import com.kuchamala.old_code.database.entity.Teachers
import com.kuchamala.old_code.database.transactions.addTeacher
import com.kuchamala.old_code.database.transactions.findTeacher
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.and

class TeachersModel {
    fun addData(surname: String, name: String, patronymic: String?, aboutTeacher: String?): TeacherEntity? {
        val op = (Teachers.name eq name) and (Teachers.surname eq surname)
        val found = findTeacher(op)

        return found ?: addTeacher(name, surname, patronymic, aboutTeacher)
    }
}