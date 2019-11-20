package com.kuchamala.database.model

import com.kuchamala.database.entity.Class
import com.kuchamala.database.entity.Classes
import com.kuchamala.database.transactions.addClass
import com.kuchamala.database.transactions.findClass
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq

class Classes {
    fun addData(
        title: String,
        description: String?,
        duration: Int,
        subscriptionPrice: Int?,
        singleClassPrice: Int,
        freeFirstClassVal: String,
        minAge: Int?,
        maxAge: Int?
    ): Class? {
        val freeFirstClass = freeFirstClassVal == "+"
        val age = ChildAges().addData(minAge, maxAge)
        val op = Classes.title eq title
        val found = findClass(op)

        return if (found != null) {
            found
        } else {
            require(age != null)
            addClass(title, description, duration, subscriptionPrice, singleClassPrice, freeFirstClass, age)
        }
    }
}