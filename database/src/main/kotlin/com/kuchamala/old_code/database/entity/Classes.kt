package com.kuchamala.old_code.database.entity

import org.jetbrains.exposed.dao.EntityID
import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.IntIdTable

object Classes : IntIdTable() {
    val title = varchar("title", 256)
    val description = text("description").nullable()
    val duration = integer("duration")
    val subscriptionPrice = integer("subscription_price").nullable()
    val singleClassPrice = integer("single_class_price")
    val freeFirstClass = bool("free_first_class")
    val age = reference("age", ChildAges)
}

class ClassEntity(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<ClassEntity>(Classes)

    var title by Classes.title
    var description by Classes.description
    var duration by Classes.duration
    var subscriptionPrice by Classes.subscriptionPrice
    var singleClassPrice by Classes.singleClassPrice
    var freeFirstClass by Classes.freeFirstClass
    var age by ChildAgeEntity referencedOn Classes.age
}