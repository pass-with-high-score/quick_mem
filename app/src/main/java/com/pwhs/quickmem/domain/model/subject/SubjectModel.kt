package com.pwhs.quickmem.domain.model.subject

import androidx.annotation.DrawableRes
import androidx.compose.ui.graphics.Color
import com.pwhs.quickmem.R

data class SubjectModel(
    val id: Int,
    val name: String,
    @DrawableRes val iconRes: Int? = null,
    val color: Color? = null,
    val description: String? = null,
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
                description = "Agriculture is the study of farming and cultivation of land."
            ),
            SubjectModel(
                2,
                "Anthropology",
                iconRes = R.drawable.ic_anthropology,
                color = Color(0xFFedb527),
                description = "Anthropology is the study of human societies and cultures."
            ),
            SubjectModel(
                3,
                "Architecture and design",
                iconRes = R.drawable.ic_architecture_and_design,
                color = Color(0xFF7f60f9),
                description = "Architecture and design is the study of buildings, structures, and spaces."
            ),
            SubjectModel(
                4,
                "Arts",
                iconRes = R.drawable.ic_arts,
                color = Color(0xFF7f60f9),
                description = "Arts is the study of creative expressions in various forms like painting, music, and sculpture."
            ),
            SubjectModel(
                5,
                "Biology",
                iconRes = R.drawable.ic_biology,
                color = Color(0xFF15b39b),
                description = "Biology is the study of living organisms and life processes."
            ),
            SubjectModel(
                6,
                "Business",
                iconRes = R.drawable.ic_business,
                color = Color(0xFF7f60f9),
                description = "Business is the study of commerce, trade, and organizational management."
            ),
            SubjectModel(
                7,
                "Chemistry",
                iconRes = R.drawable.ic_chemistry,
                color = Color(0xFF15b39b),
                description = "Chemistry is the study of substances, their properties, and reactions."
            ),
            SubjectModel(
                8,
                "Computer sciences",
                iconRes = R.drawable.ic_computer_sciences,
                color = Color(0xFF3678fd),
                description = "Computer Science is the study of computational systems, algorithms, and programming."
            ),
            SubjectModel(
                9,
                "Earth sciences",
                iconRes = R.drawable.ic_earth_sciences,
                color = Color(0xFF15b39b),
                description = "Earth Science is the study of the Earth, its composition, and natural phenomena."
            ),
            SubjectModel(
                10,
                "Economics",
                iconRes = R.drawable.ic_economics,
                color = Color(0xFFeaaa08),
                description = "Economics is the study of the production, distribution, and consumption of goods and services."
            ),
            SubjectModel(
                11,
                "Engineering and technology",
                iconRes = R.drawable.ic_engineering_and_technology,
                color = Color(0xFF2970ff),
                description = "Engineering and technology involves applying scientific knowledge to develop solutions for practical problems."
            ),
            SubjectModel(
                12,
                "Environmental studies and forestry",
                iconRes = R.drawable.ic_environmental_studies_and_forestry,
                color = Color(0xFF7a5af8),
                description = "Environmental studies and forestry focus on the protection and management of natural resources and ecosystems."
            ),
            SubjectModel(
                13,
                "Family and consumer science",
                iconRes = R.drawable.ic_family_and_consumer_science,
                color = Color(0xFFeaaa09),
                description = "Family and consumer science is the study of family dynamics, nutrition, and consumer behavior."
            ),
            SubjectModel(
                14,
                "Geography",
                iconRes = R.drawable.ic_geography,
                color = Color(0xFFeaaa09),
                description = "Geography is the study of places, landscapes, and the human-environment relationship."
            ),
            SubjectModel(
                15,
                "History",
                iconRes = R.drawable.ic_history,
                color = Color(0xFFf63d68),
                description = "History is the study of past events, civilizations, and their impact on the present."
            ),
            SubjectModel(
                16,
                "Human physical performance and recreation",
                iconRes = R.drawable.ic_human_physical_performance_and_recreation,
                color = Color(0xFF7a5af8),
                description = "Human physical performance and recreation focuses on physical activities, sports, and overall well-being."
            ),
            SubjectModel(
                17,
                "Interdisciplinary studies",
                iconRes = R.drawable.ic_interdisciplinary_studies,
                color = Color(0xFF9076f8),
                description = "Interdisciplinary studies involve integrating knowledge from multiple academic disciplines."
            ),
            SubjectModel(
                18,
                "Journalism, media studies and communication",
                iconRes = R.drawable.ic_journalism__media_studies_and_communication,
                color = Color(0xFF7a5af8),
                description = "Journalism and media studies explore communication, reporting, and media's role in society."
            ),
            SubjectModel(
                19,
                "Languages and cultures",
                iconRes = R.drawable.ic_languages_and_cultures,
                color = Color(0xFFd444f1),
                description = "Languages and cultures focus on linguistics, cultural practices, and communication across societies."
            ),
            SubjectModel(
                20,
                "Law",
                iconRes = R.drawable.ic_law,
                color = Color(0xFF7a5af8),
                description = "Law is the study of legal systems, regulations, and justice."
            ),
            SubjectModel(
                21,
                "Library and museum studies",
                iconRes = R.drawable.ic_library_and_museum_studies,
                color = Color(0xFFf63d68),
                description = "Library and museum studies focus on the preservation and organization of information and artifacts."
            ),
            SubjectModel(
                22,
                "Literature",
                iconRes = R.drawable.ic_literature,
                color = Color(0xFFf63d68),
                description = "Literature is the study of written works, such as novels, poetry, and plays."
            ),
            SubjectModel(
                23,
                "Logic",
                iconRes = R.drawable.ic_logic,
                color = Color(0xFF4180fe),
                description = "Logic is the study of reasoning, critical thinking, and problem-solving methods."
            ),
            SubjectModel(
                24,
                "Mathematics",
                iconRes = R.drawable.ic_mathematics,
                color = Color(0xFF2970ff),
                description = "Mathematics is the study of numbers, quantities, shapes, and patterns."
            ),
            SubjectModel(
                25,
                "Medicine",
                iconRes = R.drawable.ic_medicine,
                color = Color(0xFF7a5af8),
                description = "Medicine is the study of health, diseases, and medical practices."
            ),
            SubjectModel(
                26,
                "Military sciences",
                iconRes = R.drawable.ic_military_sciences,
                color = Color(0xFF7a5af8),
                description = "Military sciences study military tactics, defense strategies, and security systems."
            ),
            SubjectModel(
                27,
                "Other",
                iconRes = R.drawable.ic_other,
                color = Color(0xFF66c61c),
                description = "Other fields of study that do not fit into traditional academic categories."
            ),
            SubjectModel(
                28,
                "Philosophy",
                iconRes = R.drawable.ic_philosophy,
                color = Color(0xFFf63d68),
                description = "Philosophy is the study of existence, knowledge, and ethics."
            ),
            SubjectModel(
                29,
                "Physics",
                iconRes = R.drawable.ic_physics,
                color = Color(0xFF17b89f),
                description = "Physics is the study of matter, energy, and the laws governing the universe."
            ),
            SubjectModel(
                30,
                "Political science",
                iconRes = R.drawable.ic_political_science,
                color = Color(0xFFeaaa08),
                description = "Political science is the study of government systems, policies, and political behavior."
            ),
            SubjectModel(
                31,
                "Psychology",
                iconRes = R.drawable.ic_psychology,
                color = Color(0xFFeaaa08),
                description = "Psychology is the study of the human mind, behavior, and mental processes."
            ),
            SubjectModel(
                32,
                "Public administration",
                iconRes = R.drawable.ic_public_administration,
                color = Color(0xFFeaaa08),
                description = "Public administration involves the management of government policies and programs."
            ),
            SubjectModel(
                33,
                "Religion and divinity",
                iconRes = R.drawable.ic_religion_and_divinity,
                color = Color(0xFFf63d68),
                description = "Religion and divinity focus on the study of faith, spirituality, and religious beliefs."
            ),
            SubjectModel(
                34,
                "Social work",
                iconRes = R.drawable.ic_social_work,
                color = Color(0xFFeaaa09),
                description = "Social work is the study of helping individuals, families, and communities improve their well-being."
            ),
            SubjectModel(
                35,
                "Sociology",
                iconRes = R.drawable.ic_sociology,
                color = Color(0xFFeaaa09),
                description = "Sociology is the study of human societies, social behavior, and social structures."
            ),
            SubjectModel(
                36,
                "Space sciences",
                iconRes = R.drawable.ic_space_sciences,
                color = Color(0xFF1ab8a0),
                description = "Space sciences explore the study of the universe, celestial bodies, and cosmic phenomena."
            ),
            SubjectModel(
                37,
                "Systems science",
                iconRes = R.drawable.ic_systems_science,
                color = Color(0xFFf64b73),
                description = "Systems science is the study of complex systems, such as ecosystems, organizations, and technologies."
            ),
            SubjectModel(
                38,
                "Transportation",
                iconRes = R.drawable.ic_transportation,
                color = Color(0xFF8d72f8),
                description = "Transportation is the study of the movement of people and goods and the infrastructure that supports it."
            )
        )
    }
}
