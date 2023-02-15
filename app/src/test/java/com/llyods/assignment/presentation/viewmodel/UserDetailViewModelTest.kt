package com.llyods.assignment.presentation.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.llyods.assignment.TestCoroutineRule
import com.llyods.assignment.domain.datamodel.BaseModelResult
import com.llyods.assignment.domain.datamodel.UserDetailModel
import com.llyods.assignment.domain.usecase.GetUserDetailUseCase
import com.llyods.assignment.presentation.viewmodel.viewstate.ApiState
import com.llyods.assignment.presentation.viewmodel.viewstate.ApiState.Loading
import com.llyods.assignment.presentation.viewmodel.viewstate.ApiState.Failure
import com.llyods.assignment.presentation.viewmodel.viewstate.ApiState.Success
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import io.mockk.verifyOrder
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class UserDetailViewModelTest {

    @get:Rule
    var mainCoroutineRule = TestCoroutineRule()

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()


    private val mockUserDetail: UserDetailModel = mockk()

    private val viewStateObserver: FlowCollector<ApiState<UserDetailModel>> = mockk()

    private val mockException: Exception = mockk()

    private val mockUseCase: GetUserDetailUseCase = mockk()

    private lateinit var viewModel: UserDetailViewModel

    private val fakeSuccessFlow = flow {
        emit(BaseModelResult.OnSuccess(mockUserDetail))
    }

    private val fakeFailureFlow = flow {
        emit(BaseModelResult.OnFailure(mockException))
    }


    @Before
    fun setUp(){
        MockKAnnotations.init(this)
        viewModel = UserDetailViewModel(mockUseCase)
    }


    @Test
    fun `WHEN getUserDetail called THEN succes should called in sequence`() {
        runTest {
            val user: UserDetailModel = mockk()
            coEvery { mockUseCase("mcroydon") } returns fakeSuccessFlow
            launch {  viewModel.userDetailStateflow.collect(viewStateObserver) }
            viewModel.getUserDetail("mcroydon")

            verifyOrder {
                with(viewStateObserver) {
                    runTest {
                        emit(Loading(true))
                        emit(Success(user))
                        emit(Loading(false))
                    }
                }
            }
        }

    }

    @Test
    fun `WHEN network failure called THEN failure should called`(){

        runTest {
            coEvery { mockUseCase("mcroydon") } returns fakeFailureFlow
            launch {  viewModel.userDetailStateflow.collect(viewStateObserver) }
            viewModel.getUserDetail("mcroydon")

            verifyOrder {
                with(viewStateObserver){
                    runTest {
                        emit(Loading(true))
                        emit(Failure(mockException))
                        emit(Loading(false))
                    }
                }

            }
        }
    }

}