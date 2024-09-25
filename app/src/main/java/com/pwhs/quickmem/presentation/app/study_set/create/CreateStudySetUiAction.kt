package com.pwhs.quickmem.presentation.app.study_set.create

import com.pwhs.quickmem.domain.model.color.ColorModel
import com.pwhs.quickmem.domain.model.subject.SubjectModel

sealed class CreateStudySetUiAction {
    data class NameChanged(val name: String) : CreateStudySetUiAction()
    data class SubjectChanged(val subjectModel: SubjectModel) : CreateStudySetUiAction()
    data class ColorChanged(val colorModel: ColorModel) : CreateStudySetUiAction()
    data class PublicChanged(val isPublic: Boolean) : CreateStudySetUiAction()
    data object SaveClicked : CreateStudySetUiAction()
}