package com.kuchamala.wp.page.generator

import com.kuchamala.database.entity.EventEntity
import com.kuchamala.database.entity.TeacherClasses
import com.kuchamala.database.entity.Teachers
import com.kuchamala.database.model.ClassesModel
import com.kuchamala.database.model.TeacherClassesModel
import com.kuchamala.database.transactions.findTeacher
import com.kuchamala.database.transactions.findTeacherClass
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.transactions.transaction

class PageGenerator {
    private var rowNumberFlag = 0 // flag for background color in rows

    fun generate() {
        var classPage = ""

        //TODO: put away this info from this method somewhere.
        val title = "'Театр'"
        val postExcerpt = "''"
        val postStatus = "'draft'"
        val commentStatus = "'close'"
        val pingStatus = "'close'"
        val toPing = "''"
        val pinged = "''"
        val postContentFiltered = "''"
        val postType = "'page'"
        //TODO: -----------------------------------------------

        classPage = generateClassPage(title)
        WordPressDatabaseWriter().run(
            classPage,
            title,
            postExcerpt,
            postStatus,
            commentStatus,
            pingStatus,
            toPing,
            pinged,
            postContentFiltered,
            postType
        )
    }

    private fun generateClassPage(classTitle: String): String {
        val classData = ClassesModel().readData(classTitle)
        var page = ""

        //TODO: put away the image identifiers from this method somewhere.
        val titleImageId = 0
        val descriptionImagesId = listOf(0)
        val teacherPhotoId = listOf(0)
        //TODO: ----------------------------------------------------------

//        require(classData != null)
//        page += classTitleRow(classData.title, titleImageId)
//
//        if (classData.description != null) {
//            page += classDescriptionRow(classData.description!!, descriptionImagesId)
//        }
//
//        val teacherClass = TeacherClassesModel().readTeacherClass(classData.id)
//        val teacher = "" //TODO: How can I take a teacher from reference on TeacherEntity
//        page += classTeacherRow(teacher.name, teacher.surname, teacher.aboutTeacher, teacher.patronymic, teacherPhotoId)

        page += addContactForm()

        return page
    }

    private fun classTitleRow(title: String, imageId: Int): String {
        val backgroundColor = ""
        TODO()
    }

    private fun classDescriptionRow(description: String, images: List<Int>): String {
        val backgroundColor = ""
        TODO()
    }

    private fun classTeacherRow(
        teacherName: String,
        teacherSurname: String,
        teacherPatronymic: String?,
        aboutTeacher: String?,
        photos: List<Int>
    ): String {
        val backgroundColor = ""
        TODO()
    }

    private fun addContactForm() = "'[vc_row bg_type=\\\"color\\\" section_id=\\\"form\\\" bg_color=\\\"#f5f5f5\\\"]" +
            "[vc_column width=\\\"1/4\\\"]" +
            "[/vc_column]" +
            "[vc_column width=\\\"1/2\\\"]" +
            "[contact-form-7 id=\\\"1235\\\"]" +
            "[/vc_column]" +
            "[vc_column width=\\\"1/4\\\"]" +
            "[/vc_column]" +
            "[/vc_row]'"
}