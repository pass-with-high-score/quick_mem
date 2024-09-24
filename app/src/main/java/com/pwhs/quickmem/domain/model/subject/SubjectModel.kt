package com.pwhs.quickmem.domain.model.subject

import androidx.annotation.DrawableRes
import androidx.compose.ui.graphics.Color
import com.pwhs.quickmem.R

data class SubjectModel(
    val id: Int,
    val name: String,
    @DrawableRes val iconRes: Int? = null,
    val color: Color? = null,
    val description: String?,
    val createdAt: String? = null,
    val updatedAt: String? = null
) {
    companion object {
        val defaultSubjects = listOf(
            SubjectModel(
                1,
                "Agriculture",
                iconRes = R.drawable.ic_agriculture,
                color = Color(0xFF7f60f9),
                description = "Agriculture is the study of farming."
            ),
            SubjectModel(
                2,
                "Anthropology",
                iconRes = R.drawable.ic_anthropology,
                color = Color(0xFFedb527),
                description = "Anthropology is the study of human societies."
            ),
            SubjectModel(
                3,
                "Architecture and design",
                iconRes = R.drawable.ic_architecture_and_design,
                color = Color(0xFF7f60f9),
                description = "Architecture and design is the study of buildings and structures."
            ),
            SubjectModel(
                4,
                "Arts",
                iconRes = R.drawable.ic_arts,
                color = Color(0xFF7f60f9),
                description = "Biology is the study of living organisms."
            ),
            SubjectModel(
                5,
                "Biology",
                iconRes = R.drawable.ic_biology,
                color = Color(0xFF15b39b),
                description = "Biology is the study of living organisms."
            ),
            SubjectModel(
                6,
                "Business",
                iconRes = R.drawable.ic_business,
                color = Color(0xFF7f60f9),
                description = "Business is the study of commerce."
            ),
            SubjectModel(
                7,
                "Chemistry",
                iconRes = R.drawable.ic_chemistry,
                color = Color(0xFF15b39b),
                description = "Chemistry is the study of matter."
            ),
            SubjectModel(
                8,
                "Computer sciences",
                iconRes = R.drawable.ic_computer_sciences,
                color = Color(0xFF3678fd),
                description = "Computer Science is the study of computers."
            ),
            SubjectModel(
                9,
                "Earth sciences",
                iconRes = R.drawable.ic_earth_sciences,
                color = Color(0xFF15b39b),
                description = "Earth Science is the study of the Earth."
            )
        )
    }
}
