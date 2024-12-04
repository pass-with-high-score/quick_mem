package com.pwhs.quickmem.presentation.app.study_set.studies.write

import com.pwhs.quickmem.domain.model.color.ColorModel
import com.pwhs.quickmem.domain.model.flashcard.FlashCardResponseModel
import com.pwhs.quickmem.domain.model.subject.SubjectModel

data class LearnByTrueFalseUiState(
    val isLoading: Boolean = false,
    val isGetAll: Boolean = false,
    val isPlaySound: Boolean = false,
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
    val listWrongAnswer: List<WriteQuestion> = emptyList(),
    val writeQuestion: WriteQuestion? = null,
    val nextFlashCard: FlashCardResponseModel? = null,
    val currentFlashCard: FlashCardResponseModel? = null,
)

data class WriteQuestion(
    val id: String = "",
    val term: String = "",
    val hint: String = "",
    val definition: String = "",
    val definitionImageUrl: String = "",
    val userAnswer: String = "",
)