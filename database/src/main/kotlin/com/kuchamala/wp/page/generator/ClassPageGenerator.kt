package com.kuchamala.wp.page.generator

import com.kuchamala.database.model.ClassesModel
import com.kuchamala.database.model.TeacherClassesModel

fun main() {
    println(ClassPageGenerator().demoPage())
}

class ClassPageGenerator {

    fun row(
        bg_color: String, forColumns: Boolean, separator: Boolean, sectionId: String?, columnHeight: Boolean,
        data: () -> String
    ): String {
        val columns: String =
            if (forColumns) """ equal_column_height="middle-content" mobile_equal_column_height="false" """
            else ""
        val separatorTop: String =
            if (separator) """ separator_top_size="30px" """
            else ""
        val sectionIdString: String =
            if (sectionId != null) """ section_id="$sectionId" """
            else ""
        val columnHeightString: String =
            if (columnHeight) """ el_wrapper_class="column-height" """
            else ""
        return """
            [vc_row bg_type="color"${columns}${separatorTop}bg_color="#${bg_color}"${sectionIdString}${columnHeightString}]${data()}[/vc_row]
        """.trimIndent().trim()
    }

    fun formRow(formId: Int, isLeft: Boolean): String {
        return row(if (isLeft) "ffffff" else "f5f5f5", false, false, "form", false) {
            """
            [vc_column width="1/4"][/vc_column][vc_column width="1/2"][contact-form-7 id="$formId"][/vc_column][vc_column width="1/4"][/vc_column]
        """.trimIndent()
        }
    }

    fun teacherRow(teacherHtml: String, teacherName: String, imageId: Int, isLeft: Boolean): String {
        val imageColumn = """[vc_column width="1/2" el_wrapper_class="column-height"]${image(imageId)}[/vc_column]"""
        val teacherColumn = """[vc_column width="1/2" el_wrapper_class="column-height"][fildisi_title]Педагог: ${teacherName}[/fildisi_title][vc_column_text css=".vc_custom_1573293625443{margin-bottom: 30px !important;}"]$teacherHtml[/vc_column_text][fildisi_empty_space][fildisi_empty_space height_multiplier="3x"][/vc_column]"""
        val columns: String  =
            if (isLeft) "$imageColumn$teacherColumn"
            else "$teacherColumn$imageColumn"
        return row(if (isLeft) "ffffff" else "f5f5f5", true, false, null, false) {
            columns
        }
    }

    fun descriptionRow(descriptionText: String, title: String?, isLeft: Boolean, imageId: Int): String {
        val titleString =
            if (title != null) """[fildisi_title heading_tag="h2"]$title[/fildisi_title]"""
            else ""
        val imageColumn = """[vc_column width="1/2" tablet_sm_width="1" el_wrapper_class="column-height"]${image(imageId)}[/vc_column]"""
        val textColumn = """[vc_column width="1/2" tablet_sm_width="1" el_wrapper_class="column-height"]$titleString[vc_column_text css=".vc_custom_1552745539215{margin-bottom: 30px !important;}"]$descriptionText[/vc_column_text][/vc_column]"""

        val columns =
            if (isLeft)
                """
                    $imageColumn$textColumn
                """.trimIndent().trim()
            else
                """
                    $textColumn$imageColumn
                """.trimIndent().trim()

        return row(if (isLeft) "ffffff" else "f5f5f5", true, false, "description", false) {
            columns
        }
    }

    fun headerRow(title: String, textHtml: String, imageId: Int): String {
        val buttons: String =
            """
                <span style="display: inline !important; float: none; background-color: #ffffff; color: #333333; cursor: text; font-family: Georgia,'Times New Roman','Bitstream Charter',Times,serif; font-size: 16px; font-style: normal; font-variant: normal; font-weight: 400; letter-spacing: normal; orphans: 2; text-align: left; text-decoration: none; text-indent: 0px; text-transform: none; -webkit-text-stroke-width: 0px; white-space: normal; word-spacing: 0px;">[sc name="join-button"][sc name="more-button"]</span>
            """.trimIndent().trim()

        return row("f5f5f5", true, true, null, false) {
            """
            [vc_column width="1/2" tablet_sm_width="1"][fildisi_title heading_tag="h1"]${title}[/fildisi_title][vc_column_text css=".vc_custom_1552807612682{margin-bottom: 20px !important;}"]$textHtml[/vc_column_text]$buttons[/vc_column][vc_column width="1/2" tablet_sm_width="1"]${image(imageId)}[/vc_column]
        """.trimIndent().trim()
        }
    }

    fun image(id: Int): String {
        return """
            [fildisi_single_image image_mode="large" image="$id" image_full_column="yes"]
        """.trimIndent().trim()
    }

    fun page(): String {
        val timetableAndPrice: String =
            """
                Первое занятие — бесплатное. Абонемент — 700 руб/зан. Занятия проходят <a href="http://www.kuchamala.ru/contact/">недалеко</a> от ст. м. Озерки.
            <h4>Расписание:</h4>
            4-5 лет: вт. с 17:00 до 18:00
            5-7 лет: вт. с 18:00 до 19:00
            7-8 лет: вт. с 15:00 до 16:00/ чт. с 16:00 до 17:00
            """.trimIndent().trim()

        val title = "Математика для детей"

        val descriptionTitle = "Математические занятия для подготовки ребенка"

        val description1Text: String =
            """
                Приглашаем детей от четырех до десяти лет на занятия математикой.

            <strong>Обучение детей математике в нашем центре</strong> обличено в пока еще наиболее понятную для ребенка форму - игры.

            <strong>Авторская методика подготовки детей </strong>нашего педагога, Чивиксиной Наталии Валерьевны, разработана таким образом, что основной упор делается на развитие у детей логики, пространственного мышления и умения работать в команде. Ведь именно это – три кита, на которых базируется не только успешное освоение математики, но и всей школьной программы в целом.
            """.trimIndent().trim()

        val description2Text: String =
            """
                Мы работаем с логическими и настольными играми, головоломками, много внимания уделяется развитию пространственного мышления. Это не математика в привычном виде (арифметика), а скорее обучение на грани с другими предметами, занятия на развитие логики в широком смысле.
            <h3><style type="text/css"><!--td {border: 1px solid #ccc;}br {mso-data-placement:same-cell;}--></style></h3>
            <strong>Индивидуальные математические занятия  </strong>

            Нам важна обратная связь с родителями, в конце каждого занятия вы можете пообщаться с педагогом. Наш приоритет - <a href="http://www.kuchamala.ru/classes-softschool/">общий комфорт и результат ребенка</a>. Мы очень любим индивидуальный подход к каждому и уделяем этому время и внимание. Детям будет сложно, но очень интересно!

            Занятия проводятся по авторской методике нашего педагога <a href="https://vk.com/play_learn">Play &amp; Learn</a>.
            """.trimIndent().trim()

        val teacherHtml: String =
            """
                Меня зовут Варвара. Я закончила математико-механический факультет СПбГУ, являюсь кандидатом физико-математических наук.Имею опыт преподавания у студентов, занималась репетиторством со школьниками.
            Считаю, что развивать свои способности нужно с детства, но обязательно в интересной для ребёнка форме. Будучи мамой двух любознательных мальчишек я знаю, как заинтересовать и направить детскую энергию в нужное русло.
            """.trimIndent().trim()

        val teacherName: String = "Варвара Феоктистова"

        return headerRow(title, timetableAndPrice, 509) +
                descriptionRow(description1Text, descriptionTitle, true,491) +
                descriptionRow(description2Text, null, false, 2633) +
                teacherRow(teacherHtml, teacherName, 3135, true) +
                formRow(266, false)
    }

    fun demoPage(): String {
        val timetableAndPrice: String =
            """
                Первое занятие — бесплатное. Абонемент — TBD руб/зан. Занятия проходят <a href="http://www.kuchamala.ru/contact/">недалеко</a> от ст. м. Озерки.
            <h4>Расписание:</h4>
            TBD
            """.trimIndent().trim()

        return headerRow("Театральное мастерство", timetableAndPrice, 3433) +
                descriptionRow("TBD", "Театр для детей", true,3433) +
                teacherRow("TBD", "TBD", 3433, false) +
                formRow(266, true)
    }

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