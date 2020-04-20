package com.kuchamala.wp.database

import java.sql.ResultSet
import java.sql.Statement

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

        if (found.next()) {
            insertRevision(statement, found, postContent)
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
    val guid = "https\\://www.kuchamala.ru/$postName/"
    val columns = "post_content, post_title, post_excerpt, post_status, comment_status, ping_status, post_name, to_ping, pinged, post_content_filtered, guid, post_type"
    val values = "\"$postContent\", '$postTitle', '$postExcerpt', '$postStatus', '$commentStatus', '$pingStatus', '$postName','$toPing', '$pinged', '$postContentFiltered', '$guid', '$postType'"

    val postQuery = "INSERT INTO wp_posts($columns) VALUES($values)"
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

private fun insertRevision(statement: Statement, post: ResultSet, newContent: String) {
    val postId = post.getInt("ID")
    val columns = "post_content, post_title, post_excerpt, post_status, comment_status, ping_status, post_name, to_ping, pinged, post_content_filtered, post_parent, guid, post_type"
    val values = "\"$newContent\", " +
            "'${post.getString("post_title")}', " +
            "'${post.getString("post_excerpt")}', " +
            "'inherit', " +
            "'${post.getString("comment_status")}', " +
            "'${post.getString("ping_status")}', " +
            "'$postId-revision-v1', " +
            "'${post.getString("to_ping")}', " +
            "'${post.getString("pinged")}', " +
            "'${post.getString("post_content_filtered")}', " +
            "'$postId', " +
            "'https\\://www.kuchamala.ru/$postId-revision-v1/', " +
            "'revision'"

    val query = "INSERT INTO wp_posts($columns) VALUES($values)"
    statement.executeUpdate(query)
}

private fun updatePage(statement: Statement, postContent: String, postTitle: String, postStatus: String) {
    val query = "update wp_posts " +
            "set post_content=\"$postContent\" " +
            "where post_title='$postTitle' and post_status='$postStatus';"
    statement.executeUpdate(query)
}

private fun findPage(statement: Statement, postTitle: String, postStatus: String): ResultSet {
    val query = "select * from wp_posts where post_title='$postTitle' and post_status='$postStatus';"
    return statement.executeQuery(query)
}