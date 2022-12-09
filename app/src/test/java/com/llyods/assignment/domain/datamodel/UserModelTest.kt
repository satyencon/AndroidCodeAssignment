package com.llyods.assignment.domain.datamodel

import com.llyods.assignment.MockFileReader
import com.llyods.assignment.data.remote.responses.UserListResponse
import com.llyods.assignment.data.remote.responses.UserListResponseItem
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import org.junit.Assert
import org.junit.Test


class UserModelTest {

    @Test
    fun createUserListFromJson() {
        val fileName = "/UserList.json"
        val json = MockFileReader().getResponseFromJson(fileName)
        val moshi = Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()

        val listType = Types.newParameterizedType(List::class.java, UserListResponseItem::class.java)
        val adapter: JsonAdapter<List<UserListResponseItem>> = moshi.adapter(listType)
        val userListResponse = json?.let { adapter.fromJson(it) }


        Assert.assertEquals(30, userListResponse?.size)

        val user = userListResponse?.get(0)
        Assert.assertEquals("ruckus", user?.login)
        Assert.assertEquals("https://avatars.githubusercontent.com/u/320?v=4", user?.avatar_url)
        Assert.assertEquals("User", user?.type)
    }
}