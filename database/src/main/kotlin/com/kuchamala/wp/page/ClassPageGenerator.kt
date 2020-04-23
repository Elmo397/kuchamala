package com.kuchamala.wp.page

import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport
import com.google.api.client.http.javanet.NetHttpTransport
import com.kuchamala.sheets.GoogleSheetsReader
import com.kuchamala.sheets.GoogleAuthorization

fun generatePage(classTitle: String): String {
    val httpTransport: NetHttpTransport = GoogleNetHttpTransport.newTrustedTransport()
    val credential = GoogleAuthorization().getCredentials(httpTransport)
    val data = GoogleSheetsReader(httpTransport, credential).readClassData(classTitle)

    val timetable = createTimetable(data.timetable)
    val timetableAndPrice = """${data.price}
                                |<h4>Группы:</h4>
                                |$timetable
                                |<h4> </h4>
                                |""".trimMargin()
    val headerRow = createHeaderRow(data.title, timetableAndPrice, data.image)

    var isLeft = true
    var descriptionRow = ""
    data.descriptions.forEach { description ->
        descriptionRow += createDescriptionRow(description, isLeft)
        isLeft = !isLeft
    }

    var teacherRow = ""
    data.teachers.forEach { teacher ->
        teacherRow += createTeacherRow(teacher, isLeft)
        isLeft = !isLeft
    }

    val diplomasRow = if(data.diplomas.diplomasId.isNotEmpty()) createDiplomasRow(data.diplomas, isLeft) else ""
    val formRow = createFormRow(data.form, !isLeft)

    return "$headerRow$descriptionRow$teacherRow$diplomasRow$formRow".replace("\"", "\\\"")
}

private fun createHeaderRow(title: String, textHtml: String, image: Image): String {
    val buttons: String =
        """<span style="display: inline !important; float: none; background-color: #ffffff; color: #333333; cursor: text; font-family: Georgia,'Times New Roman','Bitstream Charter',Times,serif; font-size: 16px; font-style: normal; font-variant: normal; font-weight: 400; letter-spacing: normal; orphans: 2; text-align: left; text-decoration: none; text-indent: 0px; text-transform: none; -webkit-text-stroke-width: 0px; white-space: normal; word-spacing: 0px;">[sc name="join-button"][sc name="more-button"]</span>"""
            .trimIndent()
            .trim()

    return createRow(
        bg_color = "f5f5f5",
        forColumns = true,
        separator = true,
        sectionId = null
    ) {
        """[vc_column width="1/2" tablet_sm_width="1"][fildisi_title heading_tag="h1"]${title}[/fildisi_title][vc_column_text css=".vc_custom_1552807612682{margin-bottom: 20px !important;}"]$textHtml[/vc_column_text]$buttons[/vc_column][vc_column width="1/2" tablet_sm_width="1"]${createImageColumn(
            image
        )}[/vc_column]"""
            .trimIndent()
            .trim()
    }
}

private fun createDescriptionRow(description: Description, isLeft: Boolean): String {
    val textString =
        """[vc_column_text css=".vc_custom_1552745539215{margin-bottom: 30px !important;}"]${description.text}[/vc_column_text]"""
    val titleString =
        if (description.title != null) """[fildisi_title heading_tag="h2"]${description.title}[/fildisi_title]"""
        else ""

    val vcColumnOpen = """[vc_column width="1/2" tablet_sm_width="1" el_wrapper_class="column-height"]"""
    val vcColumnClose = "[/vc_column]"

    val imageColumn = "$vcColumnOpen${createImageColumn(description.image)}$vcColumnClose"
    val textColumn = "$vcColumnOpen$titleString$textString$vcColumnClose"

    return createRow(
        bg_color = if (isLeft) "ffffff" else "f5f5f5",
        forColumns = true,
        separator = false,
        sectionId = "description"
    ) {
        if (isLeft) "$imageColumn$textColumn" else "$textColumn$imageColumn"
    }
}

private fun createTeacherRow(teacher: Teacher, isLeft: Boolean): String {
    val vcColumnOpen = """[vc_column width="1/2" el_wrapper_class="column-height"]"""
    val vcColumnClose = "[/vc_column]"

    val titleString = "[fildisi_title]Педагог: ${teacher.name}[/fildisi_title]"
    val textString =
        """[vc_column_text css=".vc_custom_1573293625443{margin-bottom: 30px !important;}"]${teacher.about}[/vc_column_text]"""

    val imageColumn = "$vcColumnOpen${createImageColumn(teacher.image)}$vcColumnClose"
    val teacherColumn =
        """$vcColumnOpen$titleString$textString[fildisi_empty_space][fildisi_empty_space height_multiplier="3x"]$vcColumnClose"""

    return createRow(
        bg_color = if (isLeft) "ffffff" else "f5f5f5",
        forColumns = true,
        separator = false,
        sectionId = null
    ) {
        if (isLeft) "$imageColumn$teacherColumn" else "$teacherColumn$imageColumn"
    }
}

private fun createDiplomasRow(diplomas: Diplomas, isLeft: Boolean): String {
    val titleRow = createRow(
        bg_color = if (isLeft) "ffffff" else "f5f5f5",
        forColumns = false,
        separator = false,
        sectionId = null
    ) {
        """[vc_column width="1/2"][fildisi_title]${diplomas.title}[/fildisi_title][vc_column width="1/2"][/vc_column]"""
    }

    var diplomasColumns = ""
    diplomas.diplomasId.forEach { diploma ->
        diplomasColumns += """[vc_column width="1/3"][fildisi_single_image image_type="image-popup" image="${diploma.id}"][/vc_column]"""
    }

    val diplomasRow = createRow(
        bg_color = if (isLeft) "ffffff" else "f5f5f5",
        forColumns = false,
        separator = false,
        sectionId = null
    ) {
        diplomasColumns
    }

    return "$titleRow$diplomasRow"
}

private fun createFormRow(form: Form, isLeft: Boolean) = createRow(
    bg_color = if (isLeft) "ffffff" else "f5f5f5",
    forColumns = false,
    separator = false,
    sectionId = "form"
) {
    """[vc_column width="1/4"][/vc_column][vc_column width="1/2"][contact-form-7 id="${form.id}"][/vc_column][vc_column width="1/4"][/vc_column]"""
        .trimIndent()
}

private fun createRow(
    bg_color: String,
    forColumns: Boolean,
    separator: Boolean,
    sectionId: String?,
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

    return """[vc_row bg_type="color"${columns}${separatorTop}bg_color="#${bg_color}"${sectionIdString}]${data()}[/vc_row]"""
        .trimIndent()
        .trim()
}

private fun createImageColumn(image: Image) =
    """[fildisi_single_image image_mode="large" image="${image.id}" image_full_column="yes"]"""
        .trimIndent()
        .trim()
