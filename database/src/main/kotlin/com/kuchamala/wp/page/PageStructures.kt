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
    val className: String,
    val image: Image
)

data class ClassData(
    val title: String,
    val price: String,
    val timetable: List<ClassGroup>,
    val image: Image,
    val descriptions: List<Description>,
    val teachers: List<Teacher>,
    val diplomas: Diplomas,
    val form: Form
)

data class ClassGroup(
    val minAge: Int,
    val maxAge: Int?,
    val timetable: List<GroupTimetable>,
    val comment: String?
)

data class GroupTimetable(val weekDay: String, val timeStart: String)

data class Description(val title: String?, val text: String, val image: Image)

data class Teacher(val name: String, val about: String, val image: Image)

data class Diplomas(val title: String, val diplomasId: List<Image>)

data class Image(val id: Int)

data class Form(val id: Int)