package com.kuchamala.wp.page

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