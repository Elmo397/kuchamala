package com.kuchamala.wp.page

class ClassPageGenerator {
    fun run(
        classTitle: String,
        timetableAndPrice: String,
        descriptionTitle: String,
        descriptionParts: List<String>,
        teachers: Map<String, String>,
        images: List<Int>,
        formId: Int
    ): String {
        var imageIdIndex = 0
        var page = createHeaderRow(classTitle, timetableAndPrice, images[imageIdIndex])

        var isLeft = true
        var title: String? = descriptionTitle

        for (description in descriptionParts) {
            imageIdIndex++
            page += createDescriptionRow(description, title, isLeft, images[imageIdIndex])

            title = null
            isLeft = !isLeft
        }

        for (teacher in teachers) {
            val name = teacher.key
            val text = teacher.value

            imageIdIndex++
            page += createTeacherRow(text, name, images[imageIdIndex], isLeft)

            isLeft = !isLeft
        }

        page += createFormRow(formId, isLeft)

        return page
    }

    private fun createHeaderRow(title: String, textHtml: String, imageId: Int): String {
        val buttons: String =
            """<span style="display: inline !important; float: none; background-color: #ffffff; color: #333333; cursor: text; font-family: Georgia,'Times New Roman','Bitstream Charter',Times,serif; font-size: 16px; font-style: normal; font-variant: normal; font-weight: 400; letter-spacing: normal; orphans: 2; text-align: left; text-decoration: none; text-indent: 0px; text-transform: none; -webkit-text-stroke-width: 0px; white-space: normal; word-spacing: 0px;">[sc name="join-button"][sc name="more-button"]</span>"""
                .trimIndent()
                .trim()

        return createRow(
            bg_color = "f5f5f5",
            forColumns = true,
            separator = true,
            sectionId = null,
            columnHeight = false
        ) {
            """[vc_column width="1/2" tablet_sm_width="1"][fildisi_title heading_tag="h1"]${title}[/fildisi_title][vc_column_text css=".vc_custom_1552807612682{margin-bottom: 20px !important;}"]$textHtml[/vc_column_text]$buttons[/vc_column][vc_column width="1/2" tablet_sm_width="1"]${createImageColumn(
                imageId
            )}[/vc_column]"""
                .trimIndent()
                .trim()
        }
    }

    private fun createDescriptionRow(descriptionText: String, title: String?, isLeft: Boolean, imageId: Int): String {
        val titleString =
            if (title != null) """[fildisi_title heading_tag="h2"]$title[/fildisi_title]"""
            else ""

        val imageColumn =
            """[vc_column width="1/2" tablet_sm_width="1" el_wrapper_class="column-height"]${createImageColumn(imageId)}[/vc_column]"""
        val textColumn =
            """[vc_column width="1/2" tablet_sm_width="1" el_wrapper_class="column-height"]$titleString[vc_column_text css=".vc_custom_1552745539215{margin-bottom: 30px !important;}"]$descriptionText[/vc_column_text][/vc_column]"""

        val columns =
            if (isLeft) "$imageColumn$textColumn".trimIndent().trim()
            else "$textColumn$imageColumn".trimIndent().trim()

        return createRow(
            bg_color = if (isLeft) "ffffff" else "f5f5f5",
            forColumns = true,
            separator = false,
            sectionId = "description",
            columnHeight = false
        ) {
            columns
        }
    }

    private fun createTeacherRow(teacherHtml: String, teacherName: String, imageId: Int, isLeft: Boolean): String {
        val imageColumn =
            """[vc_column width="1/2" el_wrapper_class="column-height"]${createImageColumn(imageId)}[/vc_column]"""

        val teacherColumn =
            """[vc_column width="1/2" el_wrapper_class="column-height"][fildisi_title]Педагог: ${teacherName}[/fildisi_title][vc_column_text css=".vc_custom_1573293625443{margin-bottom: 30px !important;}"]$teacherHtml[/vc_column_text][fildisi_empty_space][fildisi_empty_space height_multiplier="3x"][/vc_column]"""

        val columns: String = if (isLeft) "$imageColumn$teacherColumn" else "$teacherColumn$imageColumn"

        return createRow(
            bg_color = if (isLeft) "ffffff" else "f5f5f5",
            forColumns = true,
            separator = false,
            sectionId = null,
            columnHeight = false
        ) {
            columns
        }
    }

    private fun createFormRow(formId: Int, isLeft: Boolean) = createRow(
        bg_color = if (isLeft) "ffffff" else "f5f5f5",
        forColumns = false,
        separator = false,
        sectionId = "form",
        columnHeight = false
    ) {
        """[vc_column width="1/4"][/vc_column][vc_column width="1/2"][contact-form-7 id="$formId"][/vc_column][vc_column width="1/4"][/vc_column]"""
            .trimIndent()
    }

    private fun createRow(
        bg_color: String,
        forColumns: Boolean,
        separator: Boolean,
        sectionId: String?,
        columnHeight: Boolean,
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

        return """[vc_row bg_type="color"${columns}${separatorTop}bg_color="#${bg_color}"${sectionIdString}${columnHeightString}]${data()}[/vc_row]"""
            .trimIndent()
            .trim()
    }

    private fun createImageColumn(id: Int) =
        """[fildisi_single_image image_mode="large" image="$id" image_full_column="yes"]"""
            .trimIndent()
            .trim()

}