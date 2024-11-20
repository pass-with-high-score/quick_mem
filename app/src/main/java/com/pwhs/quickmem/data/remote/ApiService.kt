package com.pwhs.quickmem.data.remote

import com.pwhs.quickmem.data.dto.auth.AuthResponseDto
import com.pwhs.quickmem.data.dto.auth.ChangePasswordRequestDto
import com.pwhs.quickmem.data.dto.auth.ChangePasswordResponseDto
import com.pwhs.quickmem.data.dto.auth.LoginRequestDto
import com.pwhs.quickmem.data.dto.auth.OtpResponseDto
import com.pwhs.quickmem.data.dto.auth.ResendEmailRequestDto
import com.pwhs.quickmem.data.dto.auth.ResetPasswordRequestDto
import com.pwhs.quickmem.data.dto.auth.ResetPasswordResponseDto
import com.pwhs.quickmem.data.dto.auth.SendResetPasswordRequestDto
import com.pwhs.quickmem.data.dto.auth.SendResetPasswordResponseDto
import com.pwhs.quickmem.data.dto.auth.SignupRequestDto
import com.pwhs.quickmem.data.dto.auth.SignupResponseDto
import com.pwhs.quickmem.data.dto.auth.UpdateAvatarRequestDto
import com.pwhs.quickmem.data.dto.auth.UpdateAvatarResponseDto
import com.pwhs.quickmem.data.dto.auth.UpdateEmailRequestDto
import com.pwhs.quickmem.data.dto.auth.UpdateEmailResponseDto
import com.pwhs.quickmem.data.dto.auth.UpdateFullNameRequestDto
import com.pwhs.quickmem.data.dto.auth.UpdateFullNameResponseDto
import com.pwhs.quickmem.data.dto.auth.UpdateUsernameRequestDto
import com.pwhs.quickmem.data.dto.auth.UpdateUsernameResponseDto
import com.pwhs.quickmem.data.dto.auth.VerifyEmailRequestDto
import com.pwhs.quickmem.data.dto.auth.VerifyPasswordRequestDto
import com.pwhs.quickmem.data.dto.auth.VerifyPasswordResponseDto
import com.pwhs.quickmem.data.dto.classes.AddStudySetToClassesRequestDto
import com.pwhs.quickmem.data.dto.classes.CreateClassRequestDto
import com.pwhs.quickmem.data.dto.classes.CreateClassResponseDto
import com.pwhs.quickmem.data.dto.classes.ExitClassRequestDto
import com.pwhs.quickmem.data.dto.classes.GetClassByOwnerResponseDto
import com.pwhs.quickmem.data.dto.classes.GetClassDetailResponseDto
import com.pwhs.quickmem.data.dto.classes.JoinClassRequestDto
import com.pwhs.quickmem.data.dto.classes.RemoveMembersRequestDto
import com.pwhs.quickmem.data.dto.classes.UpdateClassRequestDto
import com.pwhs.quickmem.data.dto.classes.UpdateClassResponseDto
import com.pwhs.quickmem.data.dto.flashcard.CreateFlashCardDto
import com.pwhs.quickmem.data.dto.flashcard.EditFlashCardDto
import com.pwhs.quickmem.data.dto.flashcard.FlashCardResponseDto
import com.pwhs.quickmem.data.dto.flashcard.FlipFlashCardDto
import com.pwhs.quickmem.data.dto.flashcard.RatingFlashCardDto
import com.pwhs.quickmem.data.dto.flashcard.ToggleStarredFlashCardDto
import com.pwhs.quickmem.data.dto.flashcard.UpdateFlashCardResponseDto
import com.pwhs.quickmem.data.dto.folder.AddFolderToClassRequestDto
import com.pwhs.quickmem.data.dto.folder.CreateFolderRequestDto
import com.pwhs.quickmem.data.dto.folder.CreateFolderResponseDto
import com.pwhs.quickmem.data.dto.folder.GetFolderResponseDto
import com.pwhs.quickmem.data.dto.folder.UpdateFolderRequestDto
import com.pwhs.quickmem.data.dto.folder.UpdateFolderResponseDto
import com.pwhs.quickmem.data.dto.notification.GetNotificationResponseDto
import com.pwhs.quickmem.data.dto.notification.MarkNotificationReadRequestDto
import com.pwhs.quickmem.data.dto.notification.TokenRequestDto
import com.pwhs.quickmem.data.dto.auth.GetUserProfileResponseDto
import com.pwhs.quickmem.data.dto.streak.GetStreakDto
import com.pwhs.quickmem.data.dto.streak.IncreaseStreakDto
import com.pwhs.quickmem.data.dto.streak.StreakDto
import com.pwhs.quickmem.data.dto.study_set.MakeACopyStudySetRequestDto
import com.pwhs.quickmem.data.dto.study_set.AddStudySetToClassRequestDto
import com.pwhs.quickmem.data.dto.study_set.AddStudySetToFolderRequestDto
import com.pwhs.quickmem.data.dto.study_set.AddStudySetToFoldersRequestDto
import com.pwhs.quickmem.data.dto.study_set.CreateStudySetRequestDto
import com.pwhs.quickmem.data.dto.study_set.CreateStudySetResponseDto
import com.pwhs.quickmem.data.dto.study_set.GetStudySetResponseDto
import com.pwhs.quickmem.data.dto.study_set.UpdateStudySetRequestDto
import com.pwhs.quickmem.data.dto.study_set.UpdateStudySetResponseDto
import com.pwhs.quickmem.data.dto.upload.DeleteImageDto
import com.pwhs.quickmem.data.dto.upload.UploadImageResponseDto
import com.pwhs.quickmem.data.dto.user.SearchUserResponseDto
import com.pwhs.quickmem.data.dto.user.UserDetailResponseDto
import okhttp3.MultipartBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Multipart
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Part
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    // Auth
    @POST("auth/signup")
    suspend fun signUp(@Body signupRequestDto: SignupRequestDto): SignupResponseDto

    @POST("auth/login")
    suspend fun login(@Body loginRequestDto: LoginRequestDto): AuthResponseDto

    @POST("auth/verify-otp")
    suspend fun verifyEmail(@Body verifyEmailRequestDto: VerifyEmailRequestDto): AuthResponseDto

    @POST("auth/resend-verification-email")
    suspend fun resendVerificationEmail(@Body resendEmailRequestDto: ResendEmailRequestDto): OtpResponseDto

    @PATCH("auth/user/fullname")
    suspend fun updateFullName(
        @Header("Authorization") token: String,
        @Body updateFullNameRequestDto: UpdateFullNameRequestDto
    ): UpdateFullNameResponseDto

    @PATCH("auth/user/username")
    suspend fun updateUsername(
        @Header("Authorization") token: String,
        @Body updateUsernameRequestDto: UpdateUsernameRequestDto
    ): UpdateUsernameResponseDto

    @PATCH("auth/user/email")
    suspend fun updateEmail(
        @Header("Authorization") token: String,
        @Body updateEmailRequestDto: UpdateEmailRequestDto
    ): UpdateEmailResponseDto

    @PATCH("/auth/user/password")
    suspend fun changePassword(
        @Header("Authorization") token: String,
        @Body changePasswordRequestDto: ChangePasswordRequestDto
    ): ChangePasswordResponseDto

    @POST("auth/send-reset-password")
    suspend fun sendResetPassword(
        @Body sendResetPasswordRequestDto: SendResetPasswordRequestDto
    ): SendResetPasswordResponseDto

    @POST("auth/reset-password")
    suspend fun resetPassword(
        @Body resetPasswordRequestDto: ResetPasswordRequestDto
    ): ResetPasswordResponseDto

    @POST("auth/verify-password")
    suspend fun verifyPassword(
        @Header("Authorization") token: String,
        @Body verifyPasswordRequestDto: VerifyPasswordRequestDto
    ): VerifyPasswordResponseDto

    @GET("auth/me/{id}")
    suspend fun getUserDetail(
        @Header("Authorization") token: String,
        @Path("id") userId: String,
        @Query("isOwner") isOwner: Boolean
    ): UserDetailResponseDto

    //Update Avatar
    @PATCH("auth/user/avatar/{id}")
    suspend fun updateAvatar(
        @Header("Authorization") authorization: String,
        @Path("id") userId: String,
        @Body updateAvatarRequestDto: UpdateAvatarRequestDto
    ): UpdateAvatarResponseDto

    @GET("auth/user/search")
    suspend fun searchUser(
        @Header("Authorization") token: String,
        @Query("username") username: String,
        @Query("page") page: Int?
    ): List<SearchUserResponseDto>

    // Upload
    @Multipart
    @POST("upload")
    suspend fun uploadImage(
        @Header("Authorization") authToken: String,
        @Part flashcard: MultipartBody.Part
    ): UploadImageResponseDto

    @POST("upload/delete")
    suspend fun deleteImage(
        @Header("Authorization") authToken: String,
        @Body deleteImageDto: DeleteImageDto
    )

    // Study Set
    @POST("study-set")
    suspend fun createStudySet(
        @Header("Authorization") token: String,
        @Body createStudySetRequestDto: CreateStudySetRequestDto
    ): CreateStudySetResponseDto

    @GET("study-set/{id}")
    suspend fun getStudySetById(
        @Header("Authorization") token: String,
        @Path("id") id: String
    ): GetStudySetResponseDto

    @GET("study-set/owner/{ownerId}")
    suspend fun getStudySetsByOwnerId(
        @Header("Authorization") token: String,
        @Path("ownerId") ownerId: String,
        @Query("classId") classId: String? = null,
        @Query("folderId") folderId: String? = null
    ): List<GetStudySetResponseDto>

    @PATCH("study-set/{id}")
    suspend fun updateStudySet(
        @Header("Authorization") token: String,
        @Path("id") id: String,
        @Body updateStudySetRequestDto: UpdateStudySetRequestDto
    ): UpdateStudySetResponseDto

    @PATCH("study-set/{id}/reset-progress")
    suspend fun resetProgress(
        @Header("Authorization") token: String,
        @Path("id") id: String,
        @Query("resetType") resetType: String
    )

    @DELETE("study-set/{id}")
    suspend fun deleteStudySet(
        @Header("Authorization") token: String,
        @Path("id") id: String
    )

    @POST("study-set/folders")
    suspend fun addStudySetToFolders(
        @Header("Authorization") token: String,
        @Body addStudySetToFoldersRequestDto: AddStudySetToFoldersRequestDto
    )

    @POST("study-set/classes")
    suspend fun addStudySetToClasses(
        @Header("Authorization") token: String,
        @Body addStudySetToClassesRequestDto: AddStudySetToClassesRequestDto
    )

    @GET("study-set/search")
    suspend fun searchStudySet(
        @Header("Authorization") token: String,
        @Query("title") title: String,
        @Query("size") size: String,
        @Query("creatorType") creatorType: String?,
        @Query("page") page: Int,
        @Query("colorId") colorId: Int?,
        @Query("subjectId") subjectId: Int?,
        @Query("isAIGenerated") isAIGenerated: Boolean?
    ): List<GetStudySetResponseDto>

    @GET("study-set/link/{code}")
    suspend fun getStudySetByLinkCode(
        @Header("Authorization") token: String,
        @Path("code") code: String
    ): GetStudySetResponseDto

    // Flashcard
    @POST("study-set/duplicate")
    suspend fun duplicateStudySet(
        @Header("Authorization") token: String,
        @Body request: MakeACopyStudySetRequestDto
    ): CreateStudySetResponseDto

    // Flash Card
    @GET("/flashcard/study-set/{id}")
    suspend fun getFlashCardsByStudySetId(
        @Header("Authorization") token: String,
        @Path("id") id: String,
        @Query("learnMode") learnMode: String
    ): List<FlashCardResponseDto>

    @POST("flashcard")
    suspend fun createFlashCard(
        @Header("Authorization") token: String,
        @Body createFlashCardDto: CreateFlashCardDto
    ): FlashCardResponseDto

    @PUT("flashcard/{id}")
    suspend fun updateFlashCard(
        @Header("Authorization") token: String,
        @Path("id") id: String,
        @Body editFlashCardDto: EditFlashCardDto
    ): FlashCardResponseDto

    @DELETE("flashcard/{id}")
    suspend fun deleteFlashCard(
        @Header("Authorization") token: String,
        @Path("id") id: String
    )

    @PATCH("flashcard/{id}/starred")
    suspend fun toggleStarredFlashCard(
        @Header("Authorization") token: String,
        @Path("id") id: String,
        @Body toggleStarredFlashCardDto: ToggleStarredFlashCardDto
    ): UpdateFlashCardResponseDto

    @PATCH("flashcard/{id}/flip-status")
    suspend fun updateFlipFlashCard(
        @Header("Authorization") token: String,
        @Path("id") id: String,
        @Body flipFlashCardDto: FlipFlashCardDto
    ): UpdateFlashCardResponseDto

    @PATCH("flashcard/{id}/rating")
    suspend fun updateRatingFlashCard(
        @Header("Authorization") token: String,
        @Path("id") id: String,
        @Body ratingFlashCardDto: RatingFlashCardDto
    ): UpdateFlashCardResponseDto

    // Folder
    @POST("folder")
    suspend fun createFolder(
        @Header("Authorization") token: String,
        @Body createFolderRequestDto: CreateFolderRequestDto
    ): CreateFolderResponseDto

    @GET("folder/{id}")
    suspend fun getFolderById(
        @Header("Authorization") token: String,
        @Path("id") id: String
    ): GetFolderResponseDto

    @PUT("folder/{id}")
    suspend fun updateFolder(
        @Header("Authorization") token: String,
        @Path("id") id: String,
        @Body updateFolderRequestDto: UpdateFolderRequestDto
    ): UpdateFolderResponseDto

    @GET("folder/owner/{ownerId}")
    suspend fun getFoldersByOwnerId(
        @Header("Authorization") token: String,
        @Path("ownerId") ownerId: String,
        @Query("classId") classId: String? = null,
        @Query("studySetId") studySetId: String? = null
    ): List<GetFolderResponseDto>

    @DELETE("folder/{id}")
    suspend fun deleteFolder(
        @Header("Authorization") token: String,
        @Path("id") id: String
    )

    @POST("folder/study-sets")
    suspend fun addStudySetToFolder(
        @Header("Authorization") token: String,
        @Body addStudySetToFolderRequestDto: AddStudySetToFolderRequestDto
    )

    @GET("folder/search")
    suspend fun searchFolder(
        @Header("Authorization") token: String,
        @Query("title") title: String,
        @Query("page") page: Int?,
    ): List<GetFolderResponseDto>

    @GET("folder/link/{code}")
    suspend fun getFolderByLinkCode(
        @Header("Authorization") token: String,
        @Path("code") code: String
    ): CreateFolderResponseDto

    // Class
    @POST("class")
    suspend fun createClass(
        @Header("Authorization") token: String,
        @Body createClassRequestDto: CreateClassRequestDto
    ): CreateClassResponseDto

    @GET("class/{id}")
    suspend fun getClassByID(
        @Header("Authorization") token: String,
        @Path("id") id: String
    ): GetClassDetailResponseDto

    @GET("class/user/{userId}")
    suspend fun getClassByOwnerID(
        @Header("Authorization") token: String,
        @Path("userId") userId: String,
        @Query("folderId") folderId: String?,
        @Query("studySetId") studySetId: String?
    ): List<GetClassByOwnerResponseDto>

    @DELETE("class/{id}")
    suspend fun deleteClass(
        @Header("Authorization") token: String,
        @Path("id") id: String
    )

    @PUT("class/{id}")
    suspend fun updateClass(
        @Header("Authorization") token: String,
        @Path("id") id: String,
        @Body updateClassRequestDto: UpdateClassRequestDto
    ): UpdateClassResponseDto

    @POST("class/study-sets")
    suspend fun addStudySetToClass(
        @Header("Authorization") token: String,
        @Body addStudySetToClassRequestDto: AddStudySetToClassRequestDto
    )

    @POST("class/folders")
    suspend fun addFolderToClass(
        @Header("Authorization") token: String,
        @Body addFolderToClassRequestDto: AddFolderToClassRequestDto
    )

    @GET("class/search")
    suspend fun searchClass(
        @Header("Authorization") token: String,
        @Query("title") title: String,
        @Query("page") page: Int?,
    ): List<GetClassByOwnerResponseDto>

    @GET("class/token/{joinToken}")
    suspend fun getClassByJoinToken(
        @Header("Authorization") token: String,
        @Path("joinToken") joinToken: String,
        @Query("userId") userId: String
    ): GetClassDetailResponseDto

    @POST("class/join")
    suspend fun joinClass(
        @Header("Authorization") token: String,
        @Body joinClassRequestDto: JoinClassRequestDto
    )

    @POST("class/exit")
    suspend fun exitClass(
        @Header("Authorization") token: String,
        @Body exitClassRequestDto: ExitClassRequestDto
    )

    @POST("class/members")
    suspend fun removeMembers(
        @Header("Authorization") token: String,
        @Body removeMembersRequestDto: RemoveMembersRequestDto
    )

    // Streak
    @GET("streak/{userId}")
    suspend fun getStreaksByUserId(
        @Header("Authorization") token: String,
        @Path("userId") userId: String
    ): GetStreakDto

    @POST("streak")
    suspend fun updateStreak(
        @Header("Authorization") token: String,
        @Body increaseStreakDto: IncreaseStreakDto
    ): StreakDto

    // Notification
    @POST("notifications/register")
    suspend fun sendDeviceToken(
        @Header("Authorization") authorization: String,
        @Body tokenRequest: TokenRequestDto
    ): Response<Unit>

    @GET("notifications/user/{id}")
    suspend fun getNotificationsByUserId(
        @Header("Authorization") token: String,
        @Path("id") userId: String
    ): List<GetNotificationResponseDto>

    @PATCH("notifications/{id}/read")
    suspend fun markNotificationAsRead(
        @Header("Authorization") token: String,
        @Path("id") notificationId: String,
        @Body requestDto: MarkNotificationReadRequestDto
    )

    @DELETE("notifications/{id}")
    suspend fun deleteNotification(
        @Header("Authorization") token: String,
        @Path("id") notificationId: String
    )

    //Profile
    @GET("auth/profile/{id}")
    suspend fun getUserProfile(
        @Header("Authorization") token: String,
        @Path("id") userId: String
    ): GetUserProfileResponseDto
}