package com.kuchamala

import com.kuchamala.database.createTables
import com.kuchamala.sheets.GoogleSheetsReader

fun main() {
    createTables()
    GoogleSheetsReader().read()
}
