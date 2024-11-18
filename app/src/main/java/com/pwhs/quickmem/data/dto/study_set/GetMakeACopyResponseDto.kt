package com.pwhs.quickmem.data.dto.study_set

import com.google.gson.annotations.SerializedName

data class GetMakeACopyResponseDto(
    @SerializedName("id")
    val id: String,
    @SerializedName("title")
    val title: String,
    @SerializedName("description")
    val description: String?,
    @SerializedName("isPublic")
    val isPublic: Boolean,
    @SerializedName("isAIGenerated")
    val isAIGenerated: Boolean,
    @SerializedName("createdAt")
    val createdAt: String,
    @SerializedName("updatedAt")
    val updatedAt: String,
    @SerializedName("linkShareCode")
    val linkShareCode: String?,
    @SerializedName("subject")
    val subject: SubjectDto,
    @SerializedName("owner")
    val owner: OwnerDto,
    @SerializedName("color")
    val color: ColorDto,
    @SerializedName("flashcardCount")
    val flashcardCount: Int,
    @SerializedName("flashcards")
    val flashcards: List<FlashcardDto>
) {
    data class SubjectDto(
        @SerializedName("id")
        val id: Int,
        @SerializedName("name")
        val name: String
    )

    data class OwnerDto(
        @SerializedName("id")
        val id: String,
        @SerializedName("username")
        val username: String,
        @SerializedName("avatarUrl")
        val avatarUrl: String,
        @SerializedName("role")
        val role: String
    )

    data class ColorDto(
        @SerializedName("id")
        val id: Int,
        @SerializedName("name")
        val name: String,
        @SerializedName("hexValue")
        val hexValue: String
    )

    data class FlashcardDto(
        @SerializedName("id")
        val id: String,
        @SerializedName("term")
        val term: String,
        @SerializedName("definition")
        val definition: String,
        @SerializedName("definitionImageUrl")
        val definitionImageUrl: String?,
        @SerializedName("hint")
        val hint: String?,
        @SerializedName("explanation")
        val explanation: String?
    )
}
