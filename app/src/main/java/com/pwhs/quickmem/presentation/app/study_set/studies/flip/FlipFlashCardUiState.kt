package com.pwhs.quickmem.presentation.app.study_set.studies.flip

import com.pwhs.quickmem.domain.model.color.ColorModel
import com.pwhs.quickmem.domain.model.flashcard.FlashCardResponseModel
import com.pwhs.quickmem.domain.model.subject.SubjectModel

data class FlipFlashCardUiState(
    val isLoading: Boolean = false,
    val studySetId: String = "",
    val studySetTitle: String = "",
    val studySetDescription: String = "",
    val studySetCardCount: Int = 0,
    val studySetColor: ColorModel = ColorModel(),
    val studySetSubject: SubjectModel = SubjectModel(),
    val flashCardList: List<FlashCardResponseModel> = emptyList(),
    val currentCardIndex: Int = 0,
    val learningTime: Long = 0,
    val startTime: Long = 0,
    val isSwipingLeft: Boolean = false,
    val isEndOfList: Boolean = false,
    val isSwipingRight: Boolean = false,
    val countKnown: Int = 0,
    val countStillLearning: Int = 0,
)