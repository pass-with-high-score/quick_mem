package com.pwhs.quickmem.presentation.app.study_set.studies.true_false

import com.pwhs.quickmem.domain.model.color.ColorModel
import com.pwhs.quickmem.domain.model.flashcard.FlashCardResponseModel
import com.pwhs.quickmem.domain.model.subject.SubjectModel

data class LearnByTrueFalseUiState(
    val isGetAll: Boolean = false,
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
    val listWrongAnswer: List<TrueFalseQuestion> = emptyList(),
    val randomQuestion: TrueFalseQuestion? = null,
    val nextFlashCard: FlashCardResponseModel? = null,
    val currentFlashCard: FlashCardResponseModel? = null,
)

data class TrueFalseQuestion(
    val id: String = "",
    val term: String = "",
    val definition: String = "",
    val definitionImageUrl: String = "",
    val originalDefinition: String = "",
    val originalDefinitionImageUrl: String = "",
    val isRandom: Boolean = false,
)