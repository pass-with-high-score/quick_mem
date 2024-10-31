package com.pwhs.quickmem.data.remote

import com.pwhs.quickmem.data.dto.auth.AuthResponseDto
import com.pwhs.quickmem.data.dto.auth.LoginRequestDto
import com.pwhs.quickmem.data.dto.auth.OtpResponseDto
import com.pwhs.quickmem.data.dto.auth.ResendEmailRequestDto
import com.pwhs.quickmem.data.dto.auth.ResetPasswordRequestDto
import com.pwhs.quickmem.data.dto.auth.SendResetPasswordRequestDto
import com.pwhs.quickmem.data.dto.auth.SignupRequestDto
import com.pwhs.quickmem.data.dto.auth.SignupResponseDto
import com.pwhs.quickmem.data.dto.auth.UpdateFullNameRequestDto
import com.pwhs.quickmem.data.dto.auth.UpdateFullNameResponseDto
import com.pwhs.quickmem.data.dto.auth.VerifyEmailRequestDto
import com.pwhs.quickmem.data.dto.classes.CreateClassRequestDto
import com.pwhs.quickmem.data.dto.classes.CreateClassResponseDto
import com.pwhs.quickmem.data.dto.flashcard.CreateFlashCardDto
import com.pwhs.quickmem.data.dto.flashcard.EditFlashCardDto
import com.pwhs.quickmem.data.dto.flashcard.FlashCardResponseDto
import com.pwhs.quickmem.data.dto.flashcard.FlipFlashCardDto
import com.pwhs.quickmem.data.dto.flashcard.RatingFlashCardDto
import com.pwhs.quickmem.data.dto.flashcard.ToggleStarredFlashCardDto
import com.pwhs.quickmem.data.dto.flashcard.UpdateFlashCardResponseDto
import com.pwhs.quickmem.data.dto.folder.CreateFolderRequestDto
import com.pwhs.quickmem.data.dto.folder.CreateFolderResponseDto
import com.pwhs.quickmem.data.dto.study_set.CreateStudySetRequestDto
import com.pwhs.quickmem.data.dto.study_set.CreateStudySetResponseDto
import com.pwhs.quickmem.data.dto.study_set.GetStudySetResponseDto
import com.pwhs.quickmem.data.dto.study_set.UpdateStudySetRequestDto
import com.pwhs.quickmem.data.dto.study_set.UpdateStudySetResponseDto
import com.pwhs.quickmem.data.dto.upload.DeleteImageDto
import com.pwhs.quickmem.data.dto.upload.UploadImageResponseDto
import com.pwhs.quickmem.domain.model.auth.ResetPasswordResponseModel
import com.pwhs.quickmem.domain.model.auth.SendResetPasswordResponseModel
import okhttp3.MultipartBody
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

    @POST("auth/send-reset-password")
    suspend fun sendResetPassword(
        @Body sendResetPasswordRequestDto: SendResetPasswordRequestDto
    ): SendResetPasswordResponseModel

    @POST("auth/reset-password")
    suspend fun resetPassword(
        @Body resetPasswordRequestDto: ResetPasswordRequestDto
    ): ResetPasswordResponseModel

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
        @Path("ownerId") ownerId: String
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

    @POST("folder")
    suspend fun createFolder(
        @Header("Authorization") token: String,
        @Body createFolderRequestDto: CreateFolderRequestDto
    ): CreateFolderResponseDto

    @PATCH("flashcard/{id}/rating")
    suspend fun updateRatingFlashCard(
        @Header("Authorization") token: String,
        @Path("id") id: String,
        @Body ratingFlashCardDto: RatingFlashCardDto
    ): UpdateFlashCardResponseDto

    @POST("class")
    suspend fun createClass(
        @Header("Authorization") token: String,
        @Body createClassRequestDto: CreateClassRequestDto
    ): CreateClassResponseDto
}