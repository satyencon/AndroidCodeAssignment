package com.llyods.assignment.presentation.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.llyods.assignment.R
import com.llyods.assignment.databinding.FragmentUserDetailsBinding
import com.llyods.assignment.extensions.loadImage
import com.llyods.assignment.extensions.snackBar
import com.llyods.assignment.presentation.viewmodel.UserDetailViewModel
import com.llyods.assignment.presentation.viewmodel.ViewState
import com.llyods.assignment.utils.AppConstants.USER_IMAGE
import com.llyods.assignment.utils.AppConstants.USER_NAME
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect

@AndroidEntryPoint
class UserDetailsFragment : BaseFragment() {

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

        arguments?.getString(USER_NAME)?.let {
            viewModel.getUserDetail(it)
        }

        initObserver()
    }

    private fun initObserver() {
        lifecycleScope.launchWhenCreated {
            viewModel.userDetailStateflow.collect {
                when(it) {
                    is ViewState.Loading -> {
                     showHideProgressBar(binding.progressBar, true)
                    }
                    is ViewState.Success -> {
                        loadImage(binding.image,arguments?.getString(USER_IMAGE))
                        binding.name.text = it.output.name
                        binding.location.text = it.output.location
                        showHideProgressBar(binding.progressBar, false)

                    }
                    is ViewState.Failure -> {
                        showHideProgressBar(binding.progressBar, false)
                        snackBar(resources.getString(R.string.network_failure))
                    }
                }
            }
        }
    }


}