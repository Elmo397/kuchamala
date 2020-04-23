package com.kuchamala.wp.database

fun getPostName(postTitle: String): String {
    val connection = connectToDatabase()
    val statement = connection!!.createStatement()

    val query = "select * from wp_posts where post_title='$postTitle' and post_type='page';"
    val result = statement.executeQuery(query)

    return if (result.next()) {
        result.getString("post_name")
    } else {
        ""
    }
}