package com.kuchamala.database.model

import com.kuchamala.database.entity.Teacher
import com.kuchamala.database.entity.Teachers
import com.kuchamala.database.transactions.findTeacher
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.and

fun getTeacher(items: List<String>): Teacher? {
    var name = items[0]
    var surname = items[1]
    var teacherOp = (Teachers.name eq name) and (Teachers.surname eq surname)
    var found = findTeacher(teacherOp)

    if(found == null) {
        name = items[1]
        surname = items[0]
        teacherOp = (Teachers.name eq name) and (Teachers.surname eq surname)
        found = findTeacher(teacherOp)
    }

    return found
}