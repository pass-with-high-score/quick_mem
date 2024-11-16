package com.pwhs.quickmem.presentation.app.search_result

import com.pwhs.quickmem.domain.model.classes.GetClassByOwnerResponseModel
import com.pwhs.quickmem.domain.model.color.ColorModel
import com.pwhs.quickmem.domain.model.folder.GetFolderResponseModel
import com.pwhs.quickmem.domain.model.study_set.GetStudySetResponseModel
import com.pwhs.quickmem.domain.model.subject.SubjectModel
import com.pwhs.quickmem.domain.model.users.SearchUserResponseModel
import com.pwhs.quickmem.domain.model.users.UserResponseModel
import com.pwhs.quickmem.presentation.app.search_result.study_set.enum.SearchResultCreatorEnum
import com.pwhs.quickmem.presentation.app.search_result.study_set.enum.SearchResultSizeEnum

data class SearchResultUiState(
    val query: String = "",
    val isLoading: Boolean = false,
    val title: String = "",
    val userAvatar: String = "",
    val username: String = "",
    val token: String = "",
    val userId: String = "",
    val studySets: List<GetStudySetResponseModel> = emptyList(),
    val classes: List<GetClassByOwnerResponseModel> = emptyList(),
    val folders: List<GetFolderResponseModel> = emptyList(),
    val users: List<SearchUserResponseModel> = emptyList(),
    val userResponseModel: UserResponseModel = UserResponseModel(),
    val subjectModel: SubjectModel = SubjectModel.defaultSubjects.first(),
    val colorModel: ColorModel = ColorModel.defaultColors.first(),
    val sizeStudySetModel: SearchResultSizeEnum = SearchResultSizeEnum.all,
    val sizeFolderModel : Int = 3,
    val sizeClassModel : Int = 3,
    val creatorTypeModel: SearchResultCreatorEnum = SearchResultCreatorEnum.all,
)
