package com.pwhs.quickmem.presentation.app.study_set.create_study_set

import com.pwhs.quickmem.domain.model.color.ColorModel
import com.pwhs.quickmem.domain.model.subject.SubjectModel

data class CreateStudySetUiState(
    val name: String = "",
    val subjectModel: SubjectModel? = SubjectModel.defaultSubjects.first(),
    val colorModel: ColorModel? = ColorModel.defaultColors.first(),
    val isPublic: Boolean = false,
)