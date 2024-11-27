package com.pwhs.quickmem.data.mapper.study_time

import com.pwhs.quickmem.data.dto.study_time.GetStudyTimeByUserResponseDto
import com.pwhs.quickmem.domain.model.study_time.GetStudyTimeByUserResponseModel
import com.pwhs.quickmem.domain.model.study_time.StudyTimeModel

fun GetStudyTimeByUserResponseModel.toDto(): GetStudyTimeByUserResponseDto {
    return GetStudyTimeByUserResponseDto(
        flip = flip,
        quiz = quiz,
        userId = userId,
        total = total,
        trueFalse = trueFalse,
        write = write
    )
}

fun GetStudyTimeByUserResponseDto.toModel(): GetStudyTimeByUserResponseModel {
    return GetStudyTimeByUserResponseModel(
        flip = flip,
        quiz = quiz,
        userId = userId,
        total = total,
        trueFalse = trueFalse,
        write = write
    )
}

fun GetStudyTimeByUserResponseModel.toStudyTimeModel(): StudyTimeModel {
    return StudyTimeModel(
        flip = flip,
        quiz = quiz,
        total = total,
        trueFalse = trueFalse,
        write = write
    )
}