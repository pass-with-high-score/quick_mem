package com.pwhs.quickmem.data.mapper.subject

import com.pwhs.quickmem.data.dto.subject.SubjectResponseDto
import com.pwhs.quickmem.domain.model.subject.SubjectModel

fun SubjectResponseDto.toSubjectModel() = SubjectModel(
    id = id,
    subjectName = SubjectModel.defaultSubjects.first { it.id == id }.subjectName
)

fun SubjectModel.toSubjectResponseDto() = SubjectResponseDto(
    id = id,
)