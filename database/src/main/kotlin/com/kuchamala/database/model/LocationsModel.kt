package com.kuchamala.database.model

import com.kuchamala.database.entity.LocationEntity
import com.kuchamala.database.entity.Locations
import com.kuchamala.database.transactions.addLocation
import com.kuchamala.database.transactions.findLocation
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.and

class LocationsModel {
    fun addData(place: String, metroStation: String?, street: String?, houseNumber: Int?, building: Int?): LocationEntity? {
        val op = (Locations.place eq place) and
                (Locations.metroStation eq metroStation) and
                (Locations.street eq street) and
                (Locations.houseNumber eq houseNumber) and
                (Locations.building eq building)

        val found = findLocation(op)

        return found ?: addLocation(place, metroStation, street, houseNumber, building)
    }
}