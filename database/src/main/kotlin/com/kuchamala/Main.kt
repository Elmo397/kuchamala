package com.kuchamala

import com.kuchamala.wp.page.*

fun main() {
//    println("Class pages...")
//    createClassesPages()
//    Thread.sleep(60000)
//    println("All classes page...")
    createAllClassesPage()
}

private fun createClassesPages() {
    createClassPage("Фокусы Онлайн", "classes-online-focuses")
    Thread.sleep(60000)
    createClassPage("Математика Онлайн", "classes-online-math")
    Thread.sleep(60000)
    createClassPage("Танцы Онлайн", "classes-online-dance")
    Thread.sleep(60000)
    createClassPage("Театр Онлайн", "classes-online-theatre")
    Thread.sleep(60000)
    createClassPage("Английский Онлайн", "classes-online-english")
    Thread.sleep(60000)
    createClassPage("Школа Эмоционального Интеллекта Онлайн", "classes-online-psychology")
    Thread.sleep(60000)
}
