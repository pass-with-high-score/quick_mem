package com.pwhs.quickmem.presentation.report

enum class ReportTypeEnum(
    val title: String,
    val questionText: String,
    val options: List<String>
) {
    STUDY_SET(
        title = "Report this set",
        questionText = "Why are you reporting this set?",
        options = listOf(
            "It contains inaccurate information",
            "It is inappropriate",
            "It is being used to cheat",
            "It violates my intellectual property rights"
        )
    ),
    CLASS(
        title = "Report this class",
        questionText = "Why are you reporting this class?",
        options = listOf(
            "The class name is misleading",
            "It is inappropriate",
            "It is being used to cheat",
            "It violates my intellectual property rights",
            "I'm having trouble joining this class"
        )
    ),
    USER_DETAIL(
        title = "Report this user",
        questionText = "Why are you reporting this user?",
        options = listOf(
            "The user is harassing me",
            "The user posted inappropriate content"
        )
    );
}