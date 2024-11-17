package com.pwhs.quickmem.presentation.app.search_result.study_set.enum

enum class SearchResultSizeEnum(val title: String, val query: String) {
    ALL(title = "All", query = "all"),
    LESS_THAN_20(title = "Less than 20", query = "lessThan20"),
    BETWEEN_20_AND_49(title = "Between 20 and 49", query = "between20And49"),
    MORE_THAN_50(title = "More than 50", query = "moreThan50"),
}