package com.kuchamala.old_code.database.entity

import org.jetbrains.exposed.dao.EntityID
import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.IntIdTable

object TeacherClasses  : IntIdTable() {
    val teacher = reference("teacher", Teachers)
    val clazz = reference("class", Classes)
}

class TeacherClassEntity (id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<TeacherClassEntity>(TeacherClasses)

    var teacher by TeacherEntity referencedOn TeacherClasses.teacher
    var clazz by ClassEntity referencedOn TeacherClasses.clazz
}