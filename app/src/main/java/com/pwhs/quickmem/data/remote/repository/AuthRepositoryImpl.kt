package com.pwhs.quickmem.data.remote.repository

import android.content.Context
import androidx.credentials.CredentialManager
import androidx.credentials.GetCredentialRequest
import androidx.credentials.exceptions.GetCredentialException
import com.google.android.libraries.identity.googleid.GetGoogleIdOption
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential
import com.google.android.libraries.identity.googleid.GoogleIdTokenParsingException
import com.pwhs.quickmem.domain.model.UserModel
import com.pwhs.quickmem.core.data.UserRole
import com.pwhs.quickmem.core.utils.Resources
import com.pwhs.quickmem.domain.repository.AuthRepository
import io.github.jan.supabase.exceptions.BadRequestRestException
import io.github.jan.supabase.exceptions.RestException
import io.github.jan.supabase.exceptions.SupabaseEncodingException
import io.github.jan.supabase.exceptions.UnknownRestException
import io.github.jan.supabase.gotrue.Auth
import io.github.jan.supabase.gotrue.providers.Google
import io.github.jan.supabase.gotrue.providers.builtin.Email
import io.github.jan.supabase.gotrue.providers.builtin.IDToken
import io.github.jan.supabase.postgrest.Postgrest
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.serialization.json.buildJsonObject
import java.util.Date
import javax.inject.Inject
import kotlinx.serialization.json.put
import timber.log.Timber
import java.security.MessageDigest
import java.util.UUID
import kotlin.math.truncate

class AuthRepositoryImpl @Inject constructor(
    private val auth: Auth,
    private val postgrest: Postgrest
) : AuthRepository {
    override suspend fun login(email: String, password: String): Flow<Resources<UserModel>> {
        TODO("Not yet implemented")
    }

    override suspend fun signup(
        email: String,
        userName: String,
        password: String,
        fullName: String,
        birthDay: Date,
        role: UserRole
    ): Flow<Resources<Boolean>> {
        return flow {
            try {
                Timber.d("Registering user with email: $email")
                val user = auth.signUpWith(provider = Email) {
                    this.email = email
                    this.password = password
                    this.data = buildJsonObject {
                        put("full_name", fullName)
                        put("user_name", userName)
                        put("role", role.name)
                        put("birth_day", truncate(birthDay.time.toDouble() / 1000).toInt())
                    }
                }
                Timber.d("User registered: ${user?.userMetadata}")
                emit(Resources.Success(true))
            } catch (e: SupabaseEncodingException) {
                Timber.e(e.toString())
                emit(Resources.Error(e.message ?: "Error"))
            } catch (e: UnknownRestException) {
                Timber.e(e.toString())
                emit(Resources.Error(e.message ?: "Error"))
            } catch (e: BadRequestRestException) {
                Timber.e(e.toString())
                emit(Resources.Error(e.message ?: "Error"))
            }
        }
    }


    override suspend fun signupWithGoogle(context: Context): Flow<Resources<Boolean>> {
        return flow {
            try {
                val credentialManager = CredentialManager.create(context)

                // Generate a nonce and hash it with sha-256
                val rawNonce = UUID.randomUUID().toString()
                val bytes = rawNonce.toByteArray()
                val md = MessageDigest.getInstance("SHA-256")
                val digest = md.digest(bytes)
                val hashedNonce = digest.fold("") { str, it -> str + "%02x".format(it) }

                val googleIdOption: GetGoogleIdOption = GetGoogleIdOption.Builder()
                    .setFilterByAuthorizedAccounts(false)
                    .setServerClientId("636851183857-ei4f1rop9p6lo3j8khtsvgae6eq8ejd9.apps.googleusercontent.com")
                    .setNonce(hashedNonce)
                    .build()

                val request: GetCredentialRequest = GetCredentialRequest.Builder()
                    .addCredentialOption(googleIdOption)
                    .build()

                val result = credentialManager.getCredential(
                    request = request,
                    context = context,
                )

                val googleIdTokenCredential = GoogleIdTokenCredential
                    .createFrom(result.credential.data)

                val googleIdToken = googleIdTokenCredential.idToken

                auth.signInWith(IDToken) {
                    idToken = googleIdToken
                    provider = Google
                    nonce = rawNonce
                }

                emit(Resources.Success(true))
            } catch (e: GetCredentialException) {
                Timber.e(e, "GetCredentialException")
                emit(Resources.Error(e.message ?: "GetCredentialException"))
            } catch (e: GoogleIdTokenParsingException) {
                Timber.e(e, "GoogleIdTokenParsingException")
                emit(Resources.Error(e.message ?: "GoogleIdTokenParsingException"))
            } catch (e: RestException) {
                Timber.e(e, "RestException")
                emit(Resources.Error(e.message ?: "RestException"))
            } catch (e: Exception) {
                Timber.e(e, "Exception")
                emit(Resources.Error(e.message ?: "Unknown error"))
            }
        }
    }
}