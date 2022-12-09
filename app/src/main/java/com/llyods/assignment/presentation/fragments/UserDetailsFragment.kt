package com.llyods.assignment.presentation.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.llyods.assignment.databinding.FragmentUserDetailsBinding
import com.llyods.assignment.extensions.loadImage
import com.llyods.assignment.utils.AppConstants
import com.llyods.assignment.presentation.viewmodel.UserDetailViewModel
import com.llyods.assignment.presentation.viewmodel.ViewState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class UserDetailsFragment : Fragment() {

    private lateinit var binding: FragmentUserDetailsBinding

    private val viewModel: UserDetailViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentUserDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        arguments?.getString(AppConstants.USER_NAME)?.let {
            viewModel.getUserDetail(it)
        }

        initObserver()
    }

    private fun initObserver() {
        lifecycleScope.launch {
            viewModel.userDetailStateflow.collect {
                when(it) {
                    is ViewState.Loading -> {

                    }
                    is ViewState.Success -> {
                        loadImage(binding.image,arguments?.getString(AppConstants.USER_IMAGE))
                        binding.name.text = it.output?.name
                        binding.location.text = it.output?.location

                    }
                    is ViewState.Failure -> {

                    }
                }
            }
        }
    }


}