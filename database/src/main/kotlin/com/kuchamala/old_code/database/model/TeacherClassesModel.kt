package com.kuchamala.old_code.database.model

import com.kuchamala.old_code.database.entity.*
import com.kuchamala.old_code.database.entity.Classes
import com.kuchamala.old_code.database.transactions.addTeacherClasses
import com.kuchamala.old_code.database.transactions.findClass
import com.kuchamala.old_code.database.transactions.findTeacherClass
import org.jetbrains.exposed.dao.EntityID
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.and

class TeacherClassesModel {
    fun addData(classTitle: String, teachersFullName: String): TeacherClassEntity? {
        val classOp = Classes.title eq classTitle
        val clazz = findClass(classOp)
        val teachers = parseTeachers(teachersFullName)

        teachers.forEach { teacher ->
            require(teacher != null && clazz != null)

            val op = (TeacherClasses.teacher eq teacher.id) and (TeacherClasses.clazz eq clazz.id)
            val found = findTeacherClass(op)

            return found ?: addTeacherClasses(teacher, clazz)
        }

        return null
    }

    fun readTeacherClass(classId: EntityID<Int>) = findTeacherClass(TeacherClasses.clazz eq classId)

    private fun parseTeachers(teachers: String): List<TeacherEntity?> {
        val result: MutableList<TeacherEntity?> = mutableListOf()

        teachers
            .split(";", ",")
            .forEach { teacher -> //todo: rewrite this crutch!!!
                val items = teacher.trim().split(" ")
                val found = getTeacher(items)

                result.add(found)
            }

        return result
    }
}