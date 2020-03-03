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

class ClassPageGenerator {
    private var rowNumberFlag = 0 // flag for background color in rows

    // Some required information to add a post to the Wordpress database
    private val postExcerpt = "''"
    private val commentStatus = "'close'"
    private val pingStatus = "'close'"
    private val toPing = "''"
    private val pinged = "''"
    private val postContentFiltered = "''"
    private val postType = "'page'"

    fun run(postTitle: String, postStatus: String) {
        val classPage = generateClassPage(postTitle)

        WordPressDatabaseWriter().run(
            addQuotationToStringData(classPage),
            addQuotationToStringData(postTitle),
            postExcerpt,
            addQuotationToStringData(postStatus),
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
        val contactFormId = 1235
        val titleImageId = 0
        val descriptionImagesId = listOf(0)
        val teacherPhotoId = listOf(0)
        //TODO: ----------------------------------------------------------

        require(classData != null)
        page += classTitleRow(classData.title, titleImageId)

        if (classData.description != null) {
            page += classDescriptionRow(classData.description!!, descriptionImagesId)
        }

        val teacherClass = TeacherClassesModel().readTeacherClass(classData.id)
        val teacher = "" //TODO: How can I take a teacher from reference on TeacherEntity
//        page += classTeacherRow(teacher.name, teacher.surname, teacher.aboutTeacher, teacher.patronymic, teacherPhotoId)

        page += addContactForm(contactFormId)

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

    private fun addContactForm(formId: Int) = "'[vc_row bg_type=\\\"color\\\" section_id=\\\"form\\\" bg_color=\\\"#f5f5f5\\\"]" +
            "[vc_column width=\\\"1/4\\\"]" +
            "[/vc_column]" +
            "[vc_column width=\\\"1/2\\\"]" +
            "[contact-form-7 id=\\\"$formId\\\"]" +
            "[/vc_column]" +
            "[vc_column width=\\\"1/4\\\"]" +
            "[/vc_column]" +
            "[/vc_row]'"

    private fun addQuotationToStringData(data: String) = "'$data'"
}