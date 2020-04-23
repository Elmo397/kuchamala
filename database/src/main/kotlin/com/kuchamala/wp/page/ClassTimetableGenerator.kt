package com.kuchamala.wp.page

fun createTimetable(classGroups: List<ClassGroup>): String {
    var timetable = ""

    for (group in classGroups) {
        val comment = if (group.comment != null) " (${group.comment})" else ""
        val agePrefix = if (group.minAge == 18) "Для взрослых" else group.minAge
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
        timetableLine += "${time.weekDay} ${getTime(time.timeStart, time.timeEnd)}; "
    }

    return timetableLine
}

private fun getTime(startTime: List<String>, endTime: List<String>): String {
    var timeLine = "c ${startTime[0]} до ${endTime[0]}"

    if (startTime.size > 1 && endTime.size > 1) {
        for (index in 1 .. startTime.lastIndex) {
            timeLine += if (index == startTime.lastIndex) {
                " и c ${startTime[index].trim()} до ${endTime[index].trim()}"
            } else {
                ", c ${startTime[index].trim()} до ${endTime[index].trim()}"
            }
        }
    }

    return timeLine
}