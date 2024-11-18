package com.pwhs.quickmem.domain.model.study_set

data class GetMakeACopyResponseModel(
    val id: String,
    val title: String,
    val description: String?,
    val isPublic: Boolean,
    val isAIGenerated: Boolean,
    val createdAt: String,
    val updatedAt: String,
    val linkShareCode: String?,
    val subject: Subject,
    val owner: Owner,
    val color: Color,
    val flashcardCount: Int,
    val flashcards: List<Flashcard>
) {
    data class Subject(
        val id: Int,
        val name: String
    )

    data class Owner(
        val id: String,
        val username: String,
        val avatarUrl: String,
        val role: String
    )

    data class Color(
        val id: Int,
        val name: String,
        val hexValue: String
    )

    data class Flashcard(
        val id: String,
        val term: String,
        val definition: String,
        val definitionImageUrl: String?,
        val hint: String?,
        val explanation: String?
    )
}
