package com.kuchamala.wp.database

import java.sql.Connection
import java.sql.DriverManager
import java.util.*

fun connectToDatabase(): Connection? {
    try {
        val property = Properties()
        property.setProperty("useSSL", "false")
        property.setProperty("serverTimezone", "Europe/Moscow")

        val driver = "com.mysql.cj.jdbc.Driver"
        val user = "host1452272_adm"
        val password = "XeHuEfDY"
        val url = "jdbc:mysql://$user:$password@kuchamala.ru:3306/host1452272_4955"

        Class.forName(driver)

        return DriverManager.getConnection(url, property)
    } catch (e: Exception) {
        e.printStackTrace()
        return null
    }
}