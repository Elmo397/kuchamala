package com.kuchamala.database.entity

import org.jetbrains.exposed.dao.EntityID
import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.IntIdTable

object ChildAges : IntIdTable() {
    val minAge = integer("min_age").nullable()
    val maxAge = integer("max_age").nullable()
}

class ChildAgeEntity(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<ChildAgeEntity>(ChildAges)

    var minAge by ChildAges.minAge
    var maxAge by ChildAges.maxAge
}