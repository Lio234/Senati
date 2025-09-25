package com.example.myapplication.api

import retrofit2.http.*

data class TokenResponse(val token: String, val user: User)
data class User(val id: Long, val name: String, val email: String, val rol: String?)

interface ApiService {
    @Headers("Accept: application/json", "Content-Type: application/json")
    @POST("auth/login")
    suspend fun login(@Body body: Map<String, String>): TokenResponse

    @Headers("Accept: application/json", "Content-Type: application/json")
    @POST("auth/register")
    suspend fun register(@Body body: Map<String, String>): TokenResponse
}
