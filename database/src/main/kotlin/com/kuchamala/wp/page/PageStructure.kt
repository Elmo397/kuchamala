package com.kuchamala.wp.page

data class AllClassesPage(
    val title: String,
    val text: String,
    val classesPreview: List<ClassPreview>,
    val image: Image
)

data class ClassPreview(
    val title: String,
    val description: String,
    val duration: Int,
    val minAge: Int,
    val maxAge: Int,
    val url: String,
    val image: Image
)

data class ClassData(
    val title: String,
    val price: String,
    val timetable: String,
    val image: Image,
    val descriptions: List<Description>,
    val teachers: List<Teacher>,
    val form: Form
)

data class Description(val title: String?, val text: String, val image: Image)

data class Teacher(val name: String, val about: String, val image: Image)

data class Image(val id: Int)

data class Form(val id: Int)