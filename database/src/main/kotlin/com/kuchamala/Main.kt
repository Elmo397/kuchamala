package com.kuchamala

import com.kuchamala.database.transactions.connectToDb
import com.kuchamala.database.transactions.createTables
import com.kuchamala.sheets.GoogleSheetsObserver
import com.kuchamala.wp.page.generator.PageGenerator

fun main() {
    connectToDb()
//    createTables()
//    GoogleSheetsObserver().run()
    PageGenerator().generate()
}
