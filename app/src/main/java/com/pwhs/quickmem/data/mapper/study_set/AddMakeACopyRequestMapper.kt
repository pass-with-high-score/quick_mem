package com.pwhs.quickmem.data.mapper.study_set

import com.pwhs.quickmem.data.dto.study_set.AddMakeACopyRequestDto
import com.pwhs.quickmem.domain.model.study_set.AddMakeACopyRequestModel

fun AddMakeACopyRequestModel.toAddMakeACopyRequestDto(): AddMakeACopyRequestDto {
    return AddMakeACopyRequestDto(
        studySetId = this.studySetId,
        newOwnerId = this.newOwnerId
    )
}

fun AddMakeACopyRequestDto.toAddMakeACopyRequestModel(): AddMakeACopyRequestModel {
    return AddMakeACopyRequestModel(
        studySetId = this.studySetId,
        newOwnerId = this.newOwnerId
    )
}
