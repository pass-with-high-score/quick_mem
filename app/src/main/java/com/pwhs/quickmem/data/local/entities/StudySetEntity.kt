package com.pwhs.quickmem.data.local.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "study_set",
    indices = [Index(value = ["id"], unique = true)]
)
data class StudySetEntity(
    @PrimaryKey
    val id: String,
    @ColumnInfo(name = "title") val title: String,
    @ColumnInfo(name = "description") val description: String,
    @ColumnInfo(name = "flashcard_count") val flashcardCount: Int,
    @ColumnInfo(name = "colorHex") val colorHex: String,
    @ColumnInfo(name = "subjectName") val subjectName: String,
    @ColumnInfo(name = "ownerId") val ownerId: String,
    @ColumnInfo(name = "ownerUsername") val ownerUsername: String,
    @ColumnInfo(name = "ownerAvatarUrl") val ownerAvatarUrl: String,
    @ColumnInfo(name = "isPublic") val isPublic: Boolean
)