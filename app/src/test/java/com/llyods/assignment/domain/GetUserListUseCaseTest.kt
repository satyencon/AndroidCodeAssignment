package com.llyods.assignment.domain

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.llyods.assignment.TestCoroutineRule
import com.llyods.assignment.data.mapper.UserDomainMapper
import com.llyods.assignment.data.remote.ApiService
import com.llyods.assignment.data.repository.AppRepositoryImpl
import com.llyods.assignment.domain.datamodel.BaseModelResult
import com.llyods.assignment.domain.datamodel.UserModel
import com.llyods.assignment.domain.usecase.GetUserListUseCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

class GetUserListUseCaseTest {

    @get:Rule
    val testInstantTaskExecutorRule: TestRule = InstantTaskExecutorRule()

    @get:Rule
    val testCoroutineRule = TestCoroutineRule()

    @Mock
    private lateinit var repository: AppRepositoryImpl

    private lateinit var useCase: GetUserListUseCase


    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun testFetchDataSuccess() = runBlocking {
        val flow = flow {
            emit(BaseModelResult.OnSuccess(emptyList<UserModel>()))
        }
        Mockito.`when`(repository.getUserList()).thenReturn(flow)
        useCase = GetUserListUseCase(repository)
        val response = useCase.invoke().first()
        Assert.assertTrue(response is BaseModelResult.OnSuccess<*>)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun testFetchDataFail() = runBlocking {
        val flow = flow {
            emit(BaseModelResult.OnFailure(Throwable("Error")))
        }
        Mockito.`when`(repository.getUserList()).thenReturn(flow)
        useCase = GetUserListUseCase(repository)
        val response = useCase.invoke().first()
        Assert.assertTrue( response is BaseModelResult.OnFailure)
    }


}