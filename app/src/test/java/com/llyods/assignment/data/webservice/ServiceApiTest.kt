package com.llyods.assignment.data.webservice

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.llyods.assignment.MockFileReader
import com.llyods.assignment.TestCoroutineRule
import com.llyods.assignment.data.remote.ApiService
import com.llyods.assignment.data.remote.responses.UserListResponseItem
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import kotlinx.coroutines.runBlocking
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.*
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.io.IOException

class ServiceApiTest {

    lateinit var mockWebServer: MockWebServer

    @get:Rule
    var mainCoroutineRule = TestCoroutineRule()

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    private lateinit var webClient: ApiService

    @Before
    fun initService(){
        mockWebServer = MockWebServer()
        mockWebServer.start()
        val moshi =  Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()
        webClient = Retrofit.Builder()
            .baseUrl(mockWebServer.url("/"))
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()
            .create(ApiService::class.java)
    }

    @Throws(IOException::class)
    fun mockResponseFromJson(fileName: String) {
        val mockResponse = MockResponse()
        MockFileReader().getResponseFromJson(fileName)?.let {
            mockResponse.setBody(
                it
            )
        }?.let {
            mockWebServer.enqueue(
                it
            )
        }
    }

    @Test
    fun testUserListFromServer() {
        runBlocking {
            val json =  MockFileReader().getResponseFromJson("/UserList.json")
            val moshi = Moshi.Builder()
                .add(KotlinJsonAdapterFactory())
                .build()

            val listType = Types.newParameterizedType(List::class.java, UserListResponseItem::class.java)
            val adapter: JsonAdapter<List<UserListResponseItem>> = moshi.adapter(listType)
            val userListResponse = json.let { adapter.fromJson(it) }
            val userListItemResponse = userListResponse?.first()
            Assert.assertEquals(userListItemResponse?.login, "ruckus")
            Assert.assertEquals(userListResponse?.size, 30)
        }
    }

    @After
    fun tearDown(){
        mockWebServer.shutdown()
    }
}