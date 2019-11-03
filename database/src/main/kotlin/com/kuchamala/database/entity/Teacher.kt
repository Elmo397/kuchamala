package com.kuchamala.database.entity

import org.jetbrains.exposed.dao.EntityID
import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.IntIdTable

object Teachers : IntIdTable() {
    val name = varchar("name", 256)
    val surname = varchar("surname", 256)
    val patronymic = varchar("patronymic", 256).nullable()
    val aboutTeacher = text("about_teacher").nullable()
}

class Teacher(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<Teacher>(Teachers)

    var name by Teachers.name
    var surname by Teachers.surname
    var patronymic by Teachers.patronymic
    var aboutTeacher by Teachers.aboutTeacher

    override fun toString(): String {
        val fullName = "$name $surname"

        return if (patronymic == "" || patronymic == null) fullName else "$fullName $patronymic"
    }
}