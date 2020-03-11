package com.kuchamala.old_code.database.model

import com.kuchamala.old_code.database.entity.ClassEntity
import com.kuchamala.old_code.database.entity.Classes
import com.kuchamala.old_code.database.transactions.addClass
import com.kuchamala.old_code.database.transactions.findClass
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