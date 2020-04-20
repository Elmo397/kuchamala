package com.kuchamala

import com.kuchamala.wp.page.*

fun main() {
    createClassPage("EmptyClass", "classes-empty")
//    createAllClassesPage()
}

private fun createClassesPages() {
    createClassPage("Фокусы Онлайн", "classes-online-focuses")
    createClassPage("Математика Онлайн", "classes-online-math")
    createClassPage("Танцы Онлайн", "classes-online-dance")
    Thread.sleep(5000)
    createClassPage("Театр Онлайн", "classes-online-theatre")
    createClassPage("Английский Онлайн", "classes-online-english")
    createClassPage("Олимпиадная Математика Онлайн", "classes-online-olympiad-math")
    createClassPage("ШЭИ Онлайн", "classes-online-psychology")
}
