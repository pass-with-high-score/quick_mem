package com.pwhs.quickmem.data.mapper.study_time

import com.pwhs.quickmem.data.dto.study_time.GetStudyTimeByStudySetResponseDto
import com.pwhs.quickmem.domain.model.study_time.GetStudyTimeByStudySetResponseModel
import com.pwhs.quickmem.domain.model.study_time.StudyTimeModel

fun GetStudyTimeByStudySetResponseModel.toDto(): GetStudyTimeByStudySetResponseDto {
    return GetStudyTimeByStudySetResponseDto(
        flip = flip,
        quiz = quiz,
        studySetId = studySetId,
        total = total,
        trueFalse = trueFalse,
        write = write
    )
}

fun GetStudyTimeByStudySetResponseDto.toModel(): GetStudyTimeByStudySetResponseModel {
    return GetStudyTimeByStudySetResponseModel(
        flip = flip,
        quiz = quiz,
        studySetId = studySetId,
        total = total,
        trueFalse = trueFalse,
        write = write
    )
}

fun GetStudyTimeByStudySetResponseModel.toStudyTimeModel(): StudyTimeModel {
    return StudyTimeModel(
        flip = flip,
        quiz = quiz,
        total = total,
        trueFalse = trueFalse,
        write = write
    )
}