package com.kuchamala

import com.kuchamala.wp.page.*

fun main() {
    createAllClassesPage()
}

private fun createClassesPages() {
    createWordpressPage("Фокусы Онлайн", "classes-online-focuses")
    createWordpressPage("Математика Онлайн", "classes-online-math")
    createWordpressPage("Танцы Онлайн", "classes-online-dance")
    Thread.sleep(5000)
    createWordpressPage("Театр Онлайн", "classes-online-theatre")
    createWordpressPage("Английский Онлайн", "classes-online-english")
    createWordpressPage("Олимпиадная Математика Онлайн", "classes-online-olympiad-math")
    createWordpressPage("ШЭИ Онлайн", "classes-online-psychology")
}

private fun createAllClassesPage() {
    val kolobokData = ClassPreview(
        title = "Колобок",
        description = "Развивающая студия “Колобок”. Пальчиковые игры. Музыка.",
        duration = 60,
        minAge = 1,
        maxAge = 3,
        url = "https://www.kuchamala.ru/classes-kolobok/",
        image = Image(1408)
    )

    val musicData = ClassPreview(
        title = "Семь весёлых нот",
        description = "Студия “Семь весёлых нот”. Музыка и пение.",
        duration = 60,
        minAge = 3,
        maxAge = 4,
        url = "https://www.kuchamala.ru/classes-music/",
        image = Image(1622)
    )

    val pageData = AllClassesPage (
        title = "Наши занятия",
        text = "TBD",
        classesPreview = listOf(kolobokData, musicData),
        image = Image(3433)
    )

    val page = generateAllClassesPage(pageData).replace("\"", "\\\"")
    insertPost(
        pageContent = page,
        postTitle = "Все занятия",
        postExcerpt = "",
        postStatus = "draft",
        commentStatus = "closed",
        pingStatus = "closed",
        toPing = "",
        pinged = "",
        postContentFiltered = "",
        postName = "classes",
        postType = "page"
    )
}