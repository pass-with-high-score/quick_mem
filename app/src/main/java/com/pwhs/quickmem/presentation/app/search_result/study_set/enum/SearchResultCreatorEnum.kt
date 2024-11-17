package com.pwhs.quickmem.presentation.app.search_result.study_set.enum

enum class SearchResultCreatorEnum(val title: String, val query: String) {
    ALL(title = "All", query = "all"),
    TEACHER(title = "Teacher", query = "teacher"),
    STUDENT(title = "Student", query = "student"),
}