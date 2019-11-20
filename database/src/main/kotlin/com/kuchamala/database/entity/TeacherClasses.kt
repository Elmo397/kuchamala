package com.kuchamala.database.entity

import org.jetbrains.exposed.dao.EntityID
import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.IntIdTable

object TeacherClasses  : IntIdTable() {
    val teacher = reference("teacher", Teachers)
    val clazz = reference("class", Classes)
}

class TeacherClass (id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<TeacherClass>(TeacherClasses)

    var teacher by Teacher referencedOn TeacherClasses.teacher
    var clazz by Class referencedOn TeacherClasses.clazz
}