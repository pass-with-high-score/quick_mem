package com.pwhs.quickmem.presentation.app.study_set.studies.true_false

import com.pwhs.quickmem.core.data.states.RandomAnswer
import com.pwhs.quickmem.core.data.states.WrongAnswer
import com.pwhs.quickmem.domain.model.color.ColorModel
import com.pwhs.quickmem.domain.model.flashcard.FlashCardResponseModel
import com.pwhs.quickmem.domain.model.subject.SubjectModel

data class LearnByTrueFalseUiState(
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
    val isEndOfList: Boolean = false,
    val wrongAnswerCount: Int = 0,
    val listWrongAnswer: List<WrongAnswer> = emptyList(),
    val randomAnswers: List<RandomAnswer> = emptyList(),
    val nextFlashCard: FlashCardResponseModel? = null,
    val currentFlashCard: FlashCardResponseModel? = null,
)