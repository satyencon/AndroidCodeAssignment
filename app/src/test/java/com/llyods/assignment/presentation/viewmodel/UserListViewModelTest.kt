package com.llyods.assignment.presentation.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.llyods.assignment.TestCoroutineRule
import com.llyods.assignment.domain.datamodel.BaseModelResult
import com.llyods.assignment.domain.datamodel.UserModel
import com.llyods.assignment.domain.usecase.GetUserListUseCase
import com.llyods.assignment.presentation.viewmodel.ViewState.*
import io.mockk.every
import io.mockk.mockk
import io.mockk.coEvery
import io.mockk.MockKAnnotations
import io.mockk.verifyOrder
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class UserListViewModelTest {

    // Set the main coroutines dispatcher for unit testing.
    @get:Rule
    var mainCoroutineRule = TestCoroutineRule()

    // Executes each task synchronously using Architecture Components.
    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()


    private val mockUserList: List<UserModel> = mockk()

    private val viewStateObserver: Observer<ViewState<List<UserModel>>> = mockk()

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
        every { mockException.message } returns "Exception!!"
        viewModel = UserListViewModel(mockUseCase)
    }


    @Test
    fun `WHEN getALlListData called THEN succes should called in sequence`() {
        runBlockingTest {
            val topArtistList: ArrayList<UserModel> = mockk()
            coEvery { mockUseCase() } returns fakeSuccessFlow
            viewModel.getALlListData()

            verifyOrder {
                with(viewStateObserver) {
                  onChanged(Loading(true))
                  onChanged(Success(topArtistList))
                  onChanged(Loading(false))
                }
            }
        }

    }

    @Test
    fun `WHEN network failure called THEN failure should called`(){

        runBlockingTest {
            coEvery { mockUseCase() } returns fakeFailureFlow
            viewModel.getALlListData()

            verifyOrder {
                viewStateObserver.onChanged(Loading(true))
                viewStateObserver.onChanged(Failure(mockException))
                viewStateObserver.onChanged(Loading(false))
            }
        }
    }

}