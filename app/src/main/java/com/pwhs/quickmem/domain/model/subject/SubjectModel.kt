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
            ),
            SubjectModel(
                10,
                "Economics",
                iconRes = R.drawable.ic_economics,
                color = Color(0xFFeaaa08),
                description = "Economics is the study of the Earth."
            ),
            SubjectModel(
                11,
                "Engineering and technology",
                iconRes = R.drawable.ic_engineering_and_technology,
                color = Color(0xFF2970ff),
                description = "Engineering and technology is the study of the Earth."
            ),
            SubjectModel(
                12,
                "Environmental studies and forestry",
                iconRes = R.drawable.ic_environmental_studies_and_forestry,
                color = Color(0xFF7a5af8),
                description = "Environmental studies and forestry is the study of the Earth."
            ),
            SubjectModel(
                13,
                "Family and consumer science",
                iconRes = R.drawable.ic_family_and_consumer_science,
                color = Color(0xFFeaaa09),
                description = "Family and consumer science is the study of the Earth."
            ),
            SubjectModel(
                14,
                "Geography",
                iconRes = R.drawable.ic_geography,
                color = Color(0xFFeaaa09),
                description = "Geography is the study of the Earth."
            ),
            SubjectModel(
                15,
                "History",
                iconRes = R.drawable.ic_history,
                color = Color(0xFFf63d68),
                description = "History is the study of the Earth."
            ),
            SubjectModel(
                16,
                "Human physical performance and recreation",
                iconRes = R.drawable.ic_human_physical_performance_and_recreation,
                color = Color(0xFF7a5af8),
                description = "Human physical performance and recreation is the study of the Earth."
            ),
            SubjectModel(
                17,
                "Interdisciplinary studies",
                iconRes = R.drawable.ic_interdisciplinary_studies,
                color = Color(0xFF9076f8),
                description = "Interdisciplinary studies is the study of the Earth."
            ),
            SubjectModel(
                18,
                "Journalism, media studies and communication",
                iconRes = R.drawable.ic_journalism__media_studies_and_communication,
                color = Color(0xFF7a5af8),
                description = "Journalism, media studies and communication is the study of the Earth."
            ),
            SubjectModel(
                19,
                "Languages and cultures",
                iconRes = R.drawable.ic_languages_and_cultures,
                color = Color(0xFFd444f1),
                description = "Languages and cultures is the study of the Earth."
            ),
            SubjectModel(
                20,
                "Law",
                iconRes = R.drawable.ic_languages_and_cultures,
                color = Color(0xFF7a5af8),
                description = "Law is the study of the Earth."
            ),
            SubjectModel(
                21,
                "Library and museum studies",
                iconRes = R.drawable.ic_library_and_museum_studies,
                color = Color(0xFFf63d68),
                description = "Library and museum studies is the study of the Earth."
            ),
            SubjectModel(
                22,
                "Literature",
                iconRes = R.drawable.ic_literature,
                color = Color(0xFFf63d68),
                description = "Literature is the study of the Earth."
            ),
            SubjectModel(
                23,
                "Logic",
                iconRes = R.drawable.ic_logic,
                color = Color(0xFF4180fe),
                description = "Logic is the study of the Earth."
            ),
            SubjectModel(
                24,
                "Mathematics",
                iconRes = R.drawable.ic_mathematics,
                color = Color(0xFF2970ff),
                description = "Mathematics is the study of the Earth."
            ),
            SubjectModel(
                25,
                "Medicine",
                iconRes = R.drawable.ic_medicine,
                color = Color(0xFF7a5af8),
                description = "Medicine is the study of the Earth."
            ),
            SubjectModel(
                26,
                "Military sciences",
                iconRes = R.drawable.ic_military_sciences,
                color = Color(0xFF7a5af8),
                description = "Military sciences is the study of the Earth."
            ),
            SubjectModel(
                27,
                "Other",
                iconRes = R.drawable.ic_other,
                color = Color(0xFF66c61c),
                description = "Other is the study of the Earth."
            ),
            SubjectModel(
                28,
                "Philosophy",
                iconRes = R.drawable.ic_philosophy,
                color = Color(0xFFf63d68),
                description = "Philosophy is the study of the Earth."
            ),
            SubjectModel(
                29,
                "Physics",
                iconRes = R.drawable.ic_physics,
                color = Color(0xFF17b89f),
                description = "Physics is the study of the Earth."
            ),
            SubjectModel(
                30,
                "Political science",
                iconRes = R.drawable.ic_political_science,
                color = Color(0xFFeaaa08),
                description = "Political science is the study of the Earth."
            ),
            SubjectModel(
                31,
                "Psychology",
                iconRes = R.drawable.ic_psychology,
                color = Color(0xFFeaaa08),
                description = "Psychology is the study of the Earth."
            ),
            SubjectModel(
                32,
                "Public administration",
                iconRes = R.drawable.ic_public_administration,
                color = Color(0xFFeaaa08),
                description = "Public administration is the study of the Earth."
            ),
            SubjectModel(
                33,
                "Religion and divinity",
                iconRes = R.drawable.ic_religion_and_divinity,
                color = Color(0xFFf63d68),
                description = "Religion and divinity is the study of the Earth."
            ),
            SubjectModel(
                34,
                "Social work",
                iconRes = R.drawable.ic_social_work,
                color = Color(0xFFeaaa09),
                description = "Social work is the study of the Earth."
            ),
            SubjectModel(
                35,
                "Sociology",
                iconRes = R.drawable.ic_sociology,
                color = Color(0xFFeaaa09),
                description = "Sociology is the study of the Earth."
            ),
            SubjectModel(
                36,
                "Space sciences",
                iconRes = R.drawable.ic_space_sciences,
                color = Color(0xFF1ab8a0),
                description = "Space sciences is the study of the Earth."
            ),
            SubjectModel(
                37,
                "Systems science",
                iconRes = R.drawable.ic_systems_science,
                color = Color(0xFFf64b73),
                description = "Systems science is the study of the Earth."
            ),
            SubjectModel(
                38,
                "Transportation",
                iconRes = R.drawable.ic_transportation,
                color = Color(0xFF8d72f8),
                description = "Transportation is the study of the Earth."
            )





        )
    }
}
