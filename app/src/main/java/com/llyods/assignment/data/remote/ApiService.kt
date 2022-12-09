package com.llyods.assignment.data.remote

import com.llyods.assignment.data.remote.responses.UserDetailResponse
import com.llyods.assignment.data.remote.responses.UserListResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {
    @GET("repos/square/retrofit/stargazers")
    suspend fun getUserList(): UserListResponse

    @GET("/users/{user}")
    suspend fun getUserDetails(@Path("user") user: String): UserDetailResponse

}