package com.kuchamala.wp.page

import java.sql.Connection
import java.sql.DriverManager
import java.util.*

fun insertPost(
    pageContent: String,
    postTitle: String,
    postExcerpt: String,
    postStatus: String,
    commentStatus: String,
    pingStatus: String,
    toPing: String,
    pinged: String,
    postContentFiltered: String,
    postName: String,
    postType: String
) {
    try {
        val connection = connectToDatabase()
        val query = "INSERT INTO " +
                "wp_posts(post_content, post_title, post_excerpt, post_status, comment_status, ping_status, post_name, to_ping, pinged, post_content_filtered, post_type) " +
                "VALUES(\"$pageContent\", '$postTitle', '$postExcerpt', '$postStatus', '$commentStatus', '$pingStatus', '$postName','$toPing', '$pinged', '$postContentFiltered', '$postType')"

        val statement = connection!!.createStatement()
        statement.executeUpdate(query)
        statement.close()
    } catch (e: Exception) {
        e.printStackTrace()
    }
}

private fun connectToDatabase(): Connection? {
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