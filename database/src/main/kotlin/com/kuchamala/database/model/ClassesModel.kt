package com.kuchamala.database.model

import com.kuchamala.database.entity.ClassEntity
import com.kuchamala.database.entity.Classes
import com.kuchamala.database.transactions.addClass
import com.kuchamala.database.transactions.findClass
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq

class ClassesModel {
    fun addData(
        title: String,
        description: String?,
        duration: Int,
        subscriptionPrice: Int?,
        singleClassPrice: Int,
        freeFirstClassVal: String,
        minAge: Int?,
        maxAge: Int?
    ): ClassEntity? {
        val freeFirstClass = freeFirstClassVal == "+"
        val age = ChildAgesModel().addData(minAge, maxAge)
        val op = Classes.title eq title
        val found = findClass(op)

        return if (found != null) {
            found
        } else {
            require(age != null)
            addClass(title, description, duration, subscriptionPrice, singleClassPrice, freeFirstClass, age)
        }
    }

    fun readData(title: String) = findClass(Classes.title eq title)
}