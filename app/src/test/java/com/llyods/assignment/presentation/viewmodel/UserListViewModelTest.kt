package com.llyods.assignment.presentation.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.llyods.assignment.TestCoroutineRule
import com.llyods.assignment.domain.datamodel.BaseModelResult
import com.llyods.assignment.domain.datamodel.UserModel
import com.llyods.assignment.domain.usecase.GetUserListUseCase
import com.llyods.assignment.presentation.viewmodel.viewstate.ApiState
import com.llyods.assignment.presentation.viewmodel.viewstate.ApiState.*
import io.mockk.every
import io.mockk.mockk
import io.mockk.coEvery
import io.mockk.MockKAnnotations
import io.mockk.verifyOrder
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class UserListViewModelTest {

    @get:Rule
    var mainCoroutineRule = TestCoroutineRule()

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()


    private val mockUserList: List<UserModel> = mockk()

    private val viewStateObserver: FlowCollector<ApiState<List<UserModel>>> = mockk()

    private val mockException: Exception = mockk()

    private val mockUseCase: GetUserListUseCase = mockk()

    private lateinit var viewModel: UserListViewModel

    private val fakeSuccessFlow = flow {
        emit(BaseModelResult.OnSuccess(mockUserList))
    }

    private val fakeFailureFlow = flow {
        emit(BaseModelResult.OnFailure(mockException))
    }


    @Before
    fun setUp(){
        MockKAnnotations.init(this)
        viewModel = UserListViewModel(mockUseCase)
    }


    @Test
    fun `WHEN getALlListData called THEN success should called in sequence`() {
        runTest {
            val topArtistList: ArrayList<UserModel> = mockk()
            coEvery { mockUseCase() } returns fakeSuccessFlow
            launch {  viewModel.userListFlow.collect(viewStateObserver) }
            viewModel.getALlListData()

            verifyOrder {
                with(viewStateObserver) {
                    runTest {
                        emit(Loading(true))
                        emit(Success(topArtistList))
                        emit(Loading(false))
                    }
                }
            }
        }

    }

    @Test
    fun `WHEN network failure called THEN failure should called`(){

        runTest {
            coEvery { mockUseCase() } returns fakeFailureFlow
            launch {  viewModel.userListFlow.collect(viewStateObserver) }
            viewModel.getALlListData()

            verifyOrder {
                with(viewStateObserver) {
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