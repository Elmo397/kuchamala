package com.kuchamala.wp.page

fun generateAllClassesPage(pageData: AllClassesPage): String {
    val header = generateHeader(pageData.title, pageData.text, pageData.image)
    val classes = generateContent(pageData.classesPreview)

    return "$header$classes"
}

private fun generateHeader(title: String, textHtml: String, image: Image): String {
    val dropDownList =
        """[fildisi_empty_space height_multiplier="custom" height="0px"][fildisi_empty_space height_multiplier="2x"][vc_raw_html]JTNDc2VsZWN0JTIwbmFtZSUzRCUyMiVEMCU5MiVEMCVCRSVEMCVCNyVEMSU4MCVEMCVCMCVEMSU4MSVEMSU4MiUyMCVEMSU4MCVEMCVCNSVEMCVCMSVEMSU5MSVEMCVCRCVEMCVCQSVEMCVCMCUyMiUyMGlkJTNEJTIya2lkLWFnZSUyMiUzRSUwQSUyMCUyMCUzQ29wdGlvbiUyMHZhbHVlJTNEJTIyJTIyJTNFJUQwJTkyJUQwJUJFJUQwJUI3JUQxJTgwJUQwJUIwJUQxJTgxJUQxJTgyJTIwJUQxJTgwJUQwJUI1JUQwJUIxJUQxJTkxJUQwJUJEJUQwJUJBJUQwJUIwJTNDJTJGb3B0aW9uJTNFJTBBJTIwJTIwJTNDb3B0aW9uJTIwdmFsdWUlM0QlMjIxJTIyJTNFMSUzQyUyRm9wdGlvbiUzRSUwQSUyMCUyMCUzQ29wdGlvbiUyMHZhbHVlJTNEJTIyMiUyMiUzRTIlM0MlMkZvcHRpb24lM0UlMEElMjAlMjAlM0NvcHRpb24lMjB2YWx1ZSUzRCUyMjMlMjIlM0UzJTNDJTJGb3B0aW9uJTNFJTBBJTIwJTIwJTNDb3B0aW9uJTIwdmFsdWUlM0QlMjI0JTIyJTNFNCUzQyUyRm9wdGlvbiUzRSUwQSUyMCUyMCUzQ29wdGlvbiUyMHZhbHVlJTNEJTIyNSUyMiUzRTUlM0MlMkZvcHRpb24lM0UlMEElMjAlMjAlM0NvcHRpb24lMjB2YWx1ZSUzRCUyMjYlMjIlM0U2JTNDJTJGb3B0aW9uJTNFJTBBJTIwJTIwJTNDb3B0aW9uJTIwdmFsdWUlM0QlMjI3JTIyJTNFNyUzQyUyRm9wdGlvbiUzRSUwQSUyMCUyMCUzQ29wdGlvbiUyMHZhbHVlJTNEJTIyOCUyMiUzRTglM0MlMkZvcHRpb24lM0UlMEElMjAlMjAlM0NvcHRpb24lMjB2YWx1ZSUzRCUyMjklMjIlM0U5JTNDJTJGb3B0aW9uJTNFJTBBJTIwJTIwJTNDb3B0aW9uJTIwdmFsdWUlM0QlMjIxMCUyMiUzRTEwJTNDJTJGb3B0aW9uJTNFJTBBJTIwJTIwJTNDb3B0aW9uJTIwdmFsdWUlM0QlMjIxMSUyMiUzRTExJTNDJTJGb3B0aW9uJTNFJTBBJTIwJTIwJTNDb3B0aW9uJTIwdmFsdWUlM0QlMjIxMiUyMiUzRTEyJTNDJTJGb3B0aW9uJTNFJTBBJTNDJTJGc2VsZWN0JTNF[/vc_raw_html][vc_raw_js]  JTNDc2NyaXB0JTIwdHlwZSUzRCUyMnRleHQlMkZqYXZhc2NyaXB0JTIyJTNFJTBBJTBBalF1ZXJ5JTI4ZG9jdW1lbnQlMjkucmVhZHklMjhmdW5jdGlvbiUyOCUyOSUyMCU3QiUwQSUyMCUyMGpRdWVyeSUyOCUyMiUyM2tpZC1hZ2UlMjIlMjkub24lMjglMjdjaGFuZ2UlMjBrZXl1cCUyNyUyQyUyMGZ1bmN0aW9uJTIwJTI4JTI5JTIwJTdCJTBBJTIwJTIwJTIwJTIwalF1ZXJ5JTI4JTIyLmNsYXNzJTIyJTIwJTI5LnBhcmVudCUyOCUyOS5wYXJlbnQlMjglMjkuY3NzJTI4JTI3ZGlzcGxheSUyNyUyQyUyMCUyN25vbmUlMjclMjklM0IlMEElMjAlMjAlMjAlMjB2YXIlMjBhZ2UlMjAlM0QlMjBqUXVlcnklMjglMjIlMjNraWQtYWdlJTIyJTI5JTVCMCU1RC52YWx1ZSUzQiUwQSUyMCUyMCUyMCUyMGlmJTIwJTI4YWdlJTI5JTIwJTdCJTBBJTIwJTIwJTIwJTIwJTIwJTIwalF1ZXJ5JTI4JTIyLmNsYXNzLSUyMiUyMCUyQiUyMGFnZSUyOS5wYXJlbnQlMjglMjkucGFyZW50JTI4JTI5LmNzcyUyOCUyN2Rpc3BsYXklMjclMkMlMjAlMjdibG9jayUyNyUyOSUzQiUwQSUyMCUyMCUyMCUyMCU3RCUyMGVsc2UlMjAlN0IlMEElMjAlMjAlMjAlMjAlMjAlMjBqUXVlcnklMjglMjIuY2xhc3MlMjIlMjAlMjkucGFyZW50JTI4JTI5LnBhcmVudCUyOCUyOS5jc3MlMjglMjdkaXNwbGF5JTI3JTJDJTIwJTI3YmxvY2slMjclMjklM0IlMEElMjAlMjAlMjAlMjAlN0QlM0IlMEElMjAlMjAlN0QlMjklMEElN0QlMjklMEElMEElM0MlMkZzY3JpcHQlM0U=[/vc_raw_js]"""
    val textContent = "${createTextContent(title, textHtml)}$dropDownList"
    val imageContent = createImageContent(image)

    val textColumn = generateColumn(textContent)
    val imageColumn = generateColumn(imageContent)

    return generateRow("#f5f5f5", "$textColumn$imageColumn")
}

private fun generateContent(classesInfo: List<ClassPreview>): String {
    var content = ""
    var isLeft = true

    for (info in classesInfo) {
        val durationCol = createDurationCol(info.duration)
        val moreBtn = createMoreBnt(info.className)
        val ageCol = createAgeCol(info.minAge, info.maxAge, if (info.maxAge > 4) "лет" else "года")

        val imageContent = createImageContent(info.image)
        val textContent =
            "${createTextContent(info.title, info.description)}[vc_row_inner]$durationCol$ageCol$moreBtn[/vc_row_inner]"

        val textColumn = generateColumn(textContent)
        val imageColumn = generateColumn(imageContent)

        var classes = ""
        for (age in info.minAge..info.maxAge) {
            classes += " class-$age"
        }

        val elWrapper = """el_wrapper_class="class$classes"""
        content += if (isLeft) generateRow("#ffffff", "$imageColumn$textColumn", elWrapper)
        else generateRow("#f5f5f5", "$textColumn$imageColumn", elWrapper)

        isLeft = !isLeft
    }

    return content
}

private fun createDurationCol(duration: Int) =
    """[vc_column_inner width="1/4"][fildisi_pie_chart pie_chart_val="$duration" pie_chart_suffix=" мин" pie_chart_size="small" pie_chart_line_size="2" pie_line_style="round" title="длительность" heading_tag="h5" heading="h5" pie_chart_val_color="#171b1d" pie_active_color="#e75015" pie_chart_color="#e5e5e5"][/vc_column_inner]"""

private fun createAgeCol(minAge: Int, maxAge: Int, suffix: String) =
    """[vc_column_inner width="1/4"][fildisi_pie_chart pie_chart_val="$maxAge" pie_chart_prefix="$minAge-" pie_chart_suffix=" $suffix" pie_chart_size="small" pie_chart_line_size="2" pie_line_style="round" title="возраст" heading_tag="h5" heading="h5" pie_chart_val_color="#171b1d" pie_active_color="#e75015" pie_chart_color="#e5e5e5"][/vc_column_inner]""".trimMargin()

private fun createMoreBnt(className: String) =
    """[vc_column_inner width="1/2" css=".vc_custom_1510329721929{padding-top: 30px !important;}"][fildisi_button btn_fluid="custom" btn_custom_width="100%" align="center" button_text="Подробнее" button_color="primary-2" button_size="small" button_link="url:http%3A%2F%2Fwww.kuchamala.ru%2F$className%2F|||"][/vc_column_inner]"""

private fun createTextContent(title: String, text: String) =
    """[fildisi_title heading_tag="h1"]$title[/fildisi_title][vc_column_text css=".vc_custom_1537426350179{margin-bottom: 30px !important;}"]$text[/vc_column_text]"""

private fun createImageContent(image: Image) =
    """[fildisi_single_image image_mode="large" image="${image.id}" image_full_column="yes"]"""

private fun generateColumn(content: String) =
    """[vc_column width="1/2" tablet_sm_width="1" el_wrapper_class="column-height"]$content[/vc_column]"""

private fun generateRow(bgColors: String, columns: String, elWrapper: String = "") =
    """[vc_row bg_type="color" equal_column_height="middle-content" mobile_equal_column_height="false" bg_color="$bgColors $elWrapper"]$columns[/vc_row]"""
