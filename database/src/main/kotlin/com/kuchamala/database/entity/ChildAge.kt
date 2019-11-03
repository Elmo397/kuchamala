package com.kuchamala.database.entity

import org.jetbrains.exposed.dao.EntityID
import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.IntIdTable

object ChildAge : IntIdTable() {
    val minAge = integer("min_age").nullable()
    val maxAge = integer("max_age").nullable()
}

class ChildAgeC(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<ChildAgeC>(ChildAge)

    var minAge by ChildAge.minAge
    var maxAge by ChildAge.maxAge
}