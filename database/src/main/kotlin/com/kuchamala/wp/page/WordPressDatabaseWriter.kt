package com.kuchamala.wp.page

import java.sql.Connection
import java.sql.DriverManager
import java.sql.Statement
import java.util.*

fun writeToDatabase(
    postContent: String,
    postTitle: String,
    postExcerpt: String = "",
    postStatus: String,
    commentStatus: String = "closed",
    pingStatus: String = "closed",
    postName: String,
    toPing: String = "",
    pinged: String = "",
    postContentFiltered: String = "",
    postType: String
) {
    try {
        val connection = connectToDatabase()
        val statement = connection!!.createStatement()
        val found = findPage(statement, postTitle, postStatus)

        if (found) {
            updatePage(statement, postContent, postTitle, postStatus)
        } else {
            insertPage(
                statement,
                postContent,
                postTitle,
                postExcerpt,
                postStatus,
                commentStatus,
                pingStatus,
                postName,
                toPing,
                pinged,
                postContentFiltered,
                postType
            )
        }

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

private fun insertPage(
    statement: Statement,
    postContent: String,
    postTitle: String,
    postExcerpt: String,
    postStatus: String,
    commentStatus: String,
    pingStatus: String,
    postName: String,
    toPing: String,
    pinged: String,
    postContentFiltered: String,
    postType: String
) {
    val postQuery = "INSERT INTO " +
            "wp_posts(post_content, post_title, post_excerpt, post_status, comment_status, ping_status, post_name, to_ping, pinged, post_content_filtered, post_type) " +
            "VALUES(\"$postContent\", '$postTitle', '$postExcerpt', '$postStatus', '$commentStatus', '$pingStatus', '$postName','$toPing', '$pinged', '$postContentFiltered', '$postType')"
    statement.executeUpdate(postQuery)

    val selectQuery = "select * from wp_posts ORDER BY id DESC LIMIT 1;"
    val result = statement.executeQuery(selectQuery)
    if (result.next()) {
        val postId = result.getInt("ID")
        val metaQuery =
            """INSERT INTO wp_postmeta(post_id, meta_key, meta_value) VALUES($postId, '_fildisi_eutf_disable_title', 'yes')"""

        statement.executeUpdate(metaQuery)
    }
}

private fun updatePage(statement: Statement, postContent: String, postTitle: String, postStatus: String) {
    val query = "update wp_posts " +
            "set post_content='$postContent' " +
            "where post_title='$postTitle' and post_status='$postStatus';"
    statement.executeUpdate(query)
}

private fun findPage(statement: Statement, postTitle: String, postStatus: String): Boolean {
    val query = "select * from wp_posts where post_title='$postTitle' and post_status='$postStatus';"
    return statement.executeQuery(query).next()
}