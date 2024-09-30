package com.pwhs.quickmem.domain.model.study_set

import com.pwhs.quickmem.domain.model.color.ColorModel
import com.pwhs.quickmem.domain.model.subject.SubjectModel

data class GetStudySetResponseModel(
    val id: String,
    val title: String,
    val description: String,
    val isPublic: Boolean,
    val createdAt: String,
    val updatedAt: String,
    val ownerId: String,
    val subject: SubjectModel? = null,
    val color: ColorModel? = null
)
