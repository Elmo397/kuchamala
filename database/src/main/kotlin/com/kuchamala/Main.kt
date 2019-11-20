package com.kuchamala

import com.kuchamala.database.transactions.connectToDb
import com.kuchamala.database.transactions.createTables
import com.kuchamala.sheets.GoogleSheetsReader

fun main() {
    connectToDb()
    createTables()
    GoogleSheetsReader().read()
}
