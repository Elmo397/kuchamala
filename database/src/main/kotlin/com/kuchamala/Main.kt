package com.kuchamala

import com.kuchamala.database.transactions.connectToDb
import com.kuchamala.wp.page.generator.ClassPageGenerator

fun main() {
    connectToDb()
//    createTables()
//    GoogleSheetsObserver().run()
    ClassPageGenerator().run("Some title", "draft")
}
