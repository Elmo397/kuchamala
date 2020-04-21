package com.kuchamala.wp.page

fun createTimetable(classGroups: List<ClassGroup>): String {
    var timetable = "Группы:\n"

    for (group in classGroups) {
        val comment = if (group.comment != null) " (${group.comment})" else ""
        val agePrefix = group.minAge
        val agePostfix = createAgePostfix(group, comment)
        val groupTimetable = getGroupTimetableLine(group.timetable)

        timetable += "$agePrefix$agePostfix$groupTimetable\n"
    }

    return timetable
}

private fun createAgePostfix(group: ClassGroup, comment: String) = if (group.maxAge != null) {
    "-${group.maxAge} ${rightWordAfterAge(group.maxAge, comment)}"
} else {
    " ${rightWordAfterAge(group.minAge, comment)}"
}

private fun rightWordAfterAge(age: Int, comment: String) = if (age > 4) "лет$comment: " else "года$comment: "

private fun getGroupTimetableLine(timetable: List<GroupTimetable>): String {
    var timetableLine = ""

    for (time in timetable) {
        timetableLine += "${time.weekDay} в ${time.timeStart}; "
    }

    return timetableLine
}