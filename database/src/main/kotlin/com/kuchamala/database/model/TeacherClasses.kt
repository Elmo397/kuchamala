package com.kuchamala.database.model

import com.kuchamala.database.entity.*
import com.kuchamala.database.entity.Classes
import com.kuchamala.database.transactions.addTeacherClasses
import com.kuchamala.database.transactions.findClass
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq

class TeacherClasses {
    fun addData(classTitle: String, teachersFullName: String): TeacherClass? {
        val classOp = Classes.title eq classTitle
        val clazz = findClass(classOp)
        val teachers = parseTeachers(teachersFullName)

        teachers.forEach { teacher ->
            require(teacher != null && clazz != null)

            return addTeacherClasses(teacher, clazz)
        }

        return null
    }

    private fun parseTeachers(teachers: String): List<Teacher?> {
        val result: MutableList<Teacher?> = mutableListOf()

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