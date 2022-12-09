package com.llyods.assignment.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.llyods.assignment.domain.datamodel.BaseModelResult
import com.llyods.assignment.domain.datamodel.UserModel
import com.llyods.assignment.domain.usecase.GetUserListUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserListViewModel @Inject constructor(
    private val getUserListUseCase: GetUserListUseCase
): ViewModel() {

    private val _userListFlow = MutableStateFlow<ViewState<List<UserModel>>>(ViewState.Loading(true))
    val userListFlow get() = _userListFlow

    fun getALlListData() {
        viewModelScope.launch {
            getViewStateFlowForNetworkCall {
                getUserListUseCase()
            }.collect {
                when (it) {
                    is ViewState.Loading -> _userListFlow.value = it
                    is ViewState.Failure -> _userListFlow.value = it
                    is ViewState.Success -> {
                        it.output.let { artists ->
                            _userListFlow.value = ViewState.Success(artists)
                        }
                    }
                }
            }

        }
    }

    suspend fun <T : Any> getViewStateFlowForNetworkCall(ioOperation: suspend () -> Flow<BaseModelResult<T>>) =
        flow {
            emit(ViewState.Loading(true))
            ioOperation().map {
                when (it) {
                    is BaseModelResult.OnSuccess -> ViewState.Success(it.data)
                    is BaseModelResult.OnFailure -> ViewState.Failure(it.throwable)
                }
            }.collect {
                emit(it)
            }
            emit(ViewState.Loading(false))
        }.flowOn(Dispatchers.IO)



}