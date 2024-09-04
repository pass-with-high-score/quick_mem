package com.pwhs.quickmem.data.remote.repository

import com.pwhs.quickmem.domain.model.UserModel
import com.pwhs.quickmem.core.data.UserRole
import com.pwhs.quickmem.core.utils.Resources
import com.pwhs.quickmem.domain.repository.AuthRepository
import io.github.jan.supabase.exceptions.BadRequestRestException
import io.github.jan.supabase.exceptions.SupabaseEncodingException
import io.github.jan.supabase.exceptions.UnknownRestException
import io.github.jan.supabase.gotrue.Auth
import io.github.jan.supabase.gotrue.providers.builtin.Email
import io.github.jan.supabase.postgrest.Postgrest
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.serialization.json.buildJsonObject
import java.util.Date
import javax.inject.Inject
import kotlinx.serialization.json.put
import timber.log.Timber
import kotlin.math.truncate

class AuthRepositoryImpl @Inject constructor(
    private val auth: Auth,
    private val postgrest: Postgrest
) : AuthRepository {
    override suspend fun login(email: String, password: String): Flow<Resources<UserModel>> {
        TODO("Not yet implemented")
    }

    override suspend fun register(
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
}