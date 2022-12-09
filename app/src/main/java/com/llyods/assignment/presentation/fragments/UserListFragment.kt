package com.llyods.assignment.presentation.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.llyods.assignment.R
import com.llyods.assignment.presentation.adapter.UserListAdapter
import com.llyods.assignment.databinding.FragmentUserListBinding
import com.llyods.assignment.extensions.click
import com.llyods.assignment.extensions.gone
import com.llyods.assignment.utils.AppConstants
import com.llyods.assignment.presentation.viewmodel.UserListViewModel
import com.llyods.assignment.presentation.viewmodel.ViewState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class UserListFragment : Fragment() {

    private lateinit var binding: FragmentUserListBinding
    private lateinit var mAdapter: UserListAdapter

    private val viewModel: UserListViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        mAdapter = UserListAdapter(requireContext())
        binding =  FragmentUserListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getALlListData()
        setupUI()
        initObservers()
    }

    private fun setupUI() {
        binding.btnDarkMode.click {
            val mode = AppCompatDelegate.getDefaultNightMode()
            if(mode == AppCompatDelegate.MODE_NIGHT_YES) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            }
        }


        binding.recyclerUserList.apply {
            adapter = mAdapter
        }

        // Adapter Click Listener
        mAdapter.onItemClick {
            val data = Bundle()
            data.putSerializable(AppConstants.USER_NAME, it.login)
            data.putSerializable(AppConstants.USER_IMAGE, it.avatar_url)
            findNavController().navigate(R.id.action_quotes_list_frag_to_quotes_details_frag, data)
        }
    }

    private fun initObservers() {
        lifecycleScope.launch {
            viewModel.userListFlow.collect {
                when(it) {
//                    ViewState.Loading -> {
//                        binding.shimmer.stopShimmer()
//                        snackBar("No Users Found...")
//                    }
                   is ViewState.Loading -> {
                        binding.shimmer.startShimmer()
                    }
                    is ViewState.Success -> {
                        binding.shimmer.stopShimmer()
                        binding.shimmer.gone()
                        it?.let { it1 ->
                            mAdapter.updateData(it1.output)
                        }
                    }
                    is ViewState.Failure -> {
                        binding.shimmer.stopShimmer()
                        binding.shimmer.gone()
//                        snackBar(it.message.toString())
                    }
                }
            }
        }
    }
}