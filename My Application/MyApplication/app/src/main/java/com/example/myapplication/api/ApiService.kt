package com.example.myapplication.api

import retrofit2.http.Body
import retrofit2.http.POST

data class TokenResponse(val token: String, val user: User)
data class User(val id: Long, val name: String, val email: String)

interface ApiService {
    @POST("auth/login")
    suspend fun login(@Body body: Map<String, String>): TokenResponse
}
