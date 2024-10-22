package com.pwhs.quickmem.domain.model.status

import androidx.annotation.DrawableRes
import androidx.compose.ui.graphics.Color
import com.pwhs.quickmem.R

data class StatusModel(
    val id: Int = 0,
    val name: String = "",
    @DrawableRes val iconRes: Int? = null,
    val color: Color? = null,
    val description: String? = null,
    val createdAt: String? = null,
    val updatedAt: String? = null
) {
    companion object {
        val defaultStatuses = listOf(
            StatusModel(
                1,
                "Student(college / university)",
                iconRes = R.drawable.ic_school,
                color = Color(0xFF000000),
                description = "Student(college / university)"
            ),
            StatusModel(
                2,
                "Student(high / secondary school)",
                iconRes = R.drawable.ic_secondary_school,
                color = Color(0xFF000000),
                description = "Student(high / secondary school)"
            ),
            StatusModel(
                3,
                "Trainee",
                iconRes = R.drawable.ic_trip,
                color = Color(0xFF000000),
                description = "Trainee"
            ),
            StatusModel(
                4,
                "Language learner",
                iconRes = R.drawable.ic_language_leaner,
                color = Color(0xFF000000),
                description = "Language learner"
            ),
            StatusModel(
                5,
                "Teacher",
                iconRes = R.drawable.ic_teacher,
                color = Color(0xFF000000),
                description = "Language learner"
            ),
            StatusModel(
                6,
                "Adult education",
                iconRes = R.drawable.ic_education,
                color = Color(0xFF000000),
                description = "Adult education"
            )
        )
    }
}
