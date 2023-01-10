package com.llyods.assignment.presentation.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.llyods.assignment.R
import com.llyods.assignment.databinding.FragmentUserDetailsBinding
import com.llyods.assignment.extensions.loadImage
import com.llyods.assignment.extensions.showHideProgressBar
import com.llyods.assignment.extensions.snackBar
import com.llyods.assignment.presentation.viewmodel.UserDetailViewModel
import com.llyods.assignment.presentation.viewmodel.viewstate.ApiState
import com.llyods.assignment.utils.AppConstants.IMAGE_URL
import com.llyods.assignment.utils.AppConstants.USER_NAME
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect

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

        arguments?.getString(USER_NAME)?.let {
            viewModel.getUserDetail(it)
        }

        initObserver()
    }

    private fun initObserver() {
        lifecycleScope.launchWhenCreated {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.userDetailStateflow.collect {
                    when (it) {
                        is ApiState.Loading -> {
                            binding.progressBar.showHideProgressBar(true)
                        }
                        is ApiState.Success -> {
                            binding.image.loadImage(arguments?.getString(IMAGE_URL))
                            binding.name.text = it.output.name
                            binding.location.text = it.output.location
                            binding.progressBar.showHideProgressBar(false)

                        }
                        is ApiState.Failure -> {
                            binding.progressBar.showHideProgressBar(false)
                            snackBar(resources.getString(R.string.network_failure))
                        }
                    }
                }
            }
        }
    }


}