package com.pwhs.quickmem.presentation.app.study_set.study.quiz

import com.pwhs.quickmem.domain.model.color.ColorModel
import com.pwhs.quickmem.domain.model.flashcard.FlashCardResponseModel
import com.pwhs.quickmem.domain.model.subject.SubjectModel

data class LearnFlashCardUiState(
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
    val flashCardLearnRound: List<FlashCardResponseModel> = emptyList(),
    val flashCardLearnRoundIndex: Int = 0,
    val randomAnswers: List<RandomAnswer> = emptyList(),
)

data class RandomAnswer(
    val answer: String = "",
    val isCorrect: Boolean = false,
    val imageURL: String = "",
)