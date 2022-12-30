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
import androidx.navigation.fragment.findNavController
import com.llyods.assignment.R
import com.llyods.assignment.databinding.FragmentUserListBinding
import com.llyods.assignment.extensions.gone
import com.llyods.assignment.extensions.snackBar
import com.llyods.assignment.presentation.adapter.UserListAdapter
import com.llyods.assignment.presentation.viewmodel.UserListViewModel
import com.llyods.assignment.presentation.viewmodel.ViewState
import com.llyods.assignment.utils.AppConstants
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class UserListFragment : Fragment() {

    private lateinit var binding: FragmentUserListBinding

    @Inject
     lateinit var mAdapter: UserListAdapter

    private val viewModel: UserListViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentUserListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getALlListData()
        setupUI()
        initObservers()
    }

    private fun setupUI() {

        binding.recyclerUserList.apply {
            adapter = mAdapter
        }

        mAdapter.onItemClick {
            val data = Bundle().apply {
                putSerializable(AppConstants.USER_NAME, it.name)
                putSerializable(AppConstants.IMAGE_URL, it.imageUrl)
            }
            findNavController().navigate(R.id.action_user_list_frag_to_user_details_frag, data)
        }
    }

    private fun initObservers() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.userListFlow.collect {
                    when (it) {
                        is ViewState.Loading -> {
                            binding.shimmer.startShimmer()
                        }
                        is ViewState.Success -> {
                            with(binding.shimmer) {
                                stopShimmer()
                                gone()
                            }
                            it.let { it1 ->
                                mAdapter.updateData(it1.output)
                            }
                        }
                        is ViewState.Failure -> {
                            with(binding.shimmer) {
                                stopShimmer()
                                gone()
                            }
                            snackBar(resources.getString(R.string.network_failure))
                        }
                    }
                }
            }
        }
    }
}