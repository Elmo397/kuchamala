package com.kuchamala.database.entity

import org.jetbrains.exposed.dao.EntityID
import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.IntIdTable

object Locations : IntIdTable() {
    val place = varchar("place", 256)
    val metroStation = varchar("metro_station", 256).nullable()
    val street = varchar("street", 256).nullable()
    val houseNumber = integer("house_number").nullable()
    val building = integer("building").nullable()
}

class LocationEntity(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<LocationEntity>(Locations)

    var place by Locations.place
    var metroStation by Locations.metroStation
    var street by Locations.street
    var houseNumber by Locations.houseNumber
    var building by Locations.building
}