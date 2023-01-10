package com.llyods.assignment.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.llyods.assignment.domain.datamodel.BaseModelResult
import com.llyods.assignment.domain.datamodel.UserDetailModel
import com.llyods.assignment.domain.usecase.GetUserDetailUseCase
import com.llyods.assignment.presentation.viewmodel.viewstate.ApiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserDetailViewModel @Inject constructor(
    private val getUserDetailUseCase: GetUserDetailUseCase
) : ViewModel() {

    private val _userDetail = MutableStateFlow<ApiState<UserDetailModel>>(ApiState.Loading(true))
    val userDetailStateflow get() = _userDetail

    fun getUserDetail(user: String) {
        viewModelScope.launch {
           getUserDetailUseCase(user)
           .map {
            when (it) {
                   is BaseModelResult.OnSuccess -> ApiState.Success(it.data)
                   is BaseModelResult.OnFailure -> ApiState.Failure(it.throwable)
               }
            }.collect {
                when (it) {
                    is ApiState.Loading -> _userDetail.value = ApiState.Loading(false)
                    is ApiState.Failure -> _userDetail.value = it
                    is ApiState.Success -> {
                        it.output.let { artists ->
                            _userDetail.value = ApiState.Success(artists)
                        }
                    }
                }
            }
        }
    }

}