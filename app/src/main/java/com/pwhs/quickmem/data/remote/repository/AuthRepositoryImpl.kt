package com.pwhs.quickmem.data.remote.repository

import com.pwhs.quickmem.domain.model.UserRole
import com.pwhs.quickmem.domain.repository.AuthRepository
import io.github.jan.supabase.exceptions.SupabaseEncodingException
import io.github.jan.supabase.exceptions.UnknownRestException
import io.github.jan.supabase.gotrue.Auth
import io.github.jan.supabase.gotrue.providers.builtin.Email
import io.github.jan.supabase.postgrest.Postgrest
import kotlinx.serialization.json.buildJsonObject
import java.util.Date
import javax.inject.Inject
import kotlinx.serialization.json.put

class AuthRepositoryImpl @Inject constructor(
    private val auth: Auth,
    private val postgrest: Postgrest
) : AuthRepository {
    override suspend fun login(email: String, password: String): Boolean {
        TODO("Not yet implemented")
    }

    override suspend fun register(
        email: String,
        userName: String,
        password: String,
        fullName: String,
        birthDay: Date,
        role: UserRole
    ): Boolean {
        return try {
            val response = auth.signUpWith(Email) {
                this.email = email
                this.password = password
                data = buildJsonObject {
                    put("user_name", userName)
                    put("full_name", fullName)
                    put("birth_day", birthDay.toString())
                    put("role", role.role)
                }
            }
            true
        } catch (e: SupabaseEncodingException) {
            e.message?.let { println(it) }
            e.cause?.let { println(it) }
            false
        } catch (e: UnknownRestException) {
            e.message?.let { println(it) }
            false
        }
    }
}