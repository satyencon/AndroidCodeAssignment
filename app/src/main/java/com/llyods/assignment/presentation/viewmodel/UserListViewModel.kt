package com.llyods.assignment.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.llyods.assignment.domain.datamodel.BaseModelResult
import com.llyods.assignment.domain.datamodel.UserModel
import com.llyods.assignment.domain.usecase.GetUserListUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserListViewModel @Inject constructor(
    private val getUserListUseCase: GetUserListUseCase
) : ViewModel() {

    private val _userListFlow =
        MutableStateFlow<ApiState<List<UserModel>>>(ApiState.Loading(true))
    val userListFlow get() = _userListFlow

    fun getALlListData() {
        viewModelScope.launch {
                getUserListUseCase().map {
                    when (it) {
                        is BaseModelResult.OnSuccess -> ApiState.Success(it.data)
                        is BaseModelResult.OnFailure -> ApiState.Failure(it.throwable)
                    }
                }.collect {
                when (it) {
                    is ApiState.Loading -> _userListFlow.value = ApiState.Loading(false)
                    is ApiState.Failure -> _userListFlow.value = it
                    is ApiState.Success -> {
                        it.output.let { artists ->
                            _userListFlow.value = ApiState.Success(artists)
                        }
                    }
                }
            }

        }
    }


}