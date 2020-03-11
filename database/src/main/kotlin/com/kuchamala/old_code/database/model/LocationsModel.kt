package com.kuchamala.old_code.database.model

import com.kuchamala.old_code.database.entity.LocationEntity
import com.kuchamala.old_code.database.entity.Locations
import com.kuchamala.old_code.database.transactions.addLocation
import com.kuchamala.old_code.database.transactions.findLocation
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