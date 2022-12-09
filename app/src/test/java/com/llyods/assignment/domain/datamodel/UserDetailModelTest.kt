package com.llyods.assignment.domain.datamodel

import com.llyods.assignment.MockFileReader
import com.llyods.assignment.data.remote.responses.UserDetailResponse
import com.llyods.assignment.data.remote.responses.UserListResponseItem
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import org.junit.Assert
import org.junit.Test


class UserDetailModelTest {

    @Test
    fun createUserDetailFromJson() {
        val fileName = "/UserDetail.json"
        val json = MockFileReader().getResponseFromJson(fileName)
        val moshi = Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()

        val listType = Types.newParameterizedType(UserDetailResponse::class.java)
        val adapter: JsonAdapter<UserDetailResponse> = moshi.adapter(listType)
        val userDetail = json?.let { adapter.fromJson(it) }

        Assert.assertEquals("mcroydon", userDetail?.login)
        Assert.assertEquals("https://avatars.githubusercontent.com/u/1411?v=4", userDetail?.avatarUrl)
        Assert.assertEquals("User", userDetail?.type)
    }
}