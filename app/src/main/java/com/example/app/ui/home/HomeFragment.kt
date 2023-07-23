package com.example.app.ui.home

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.example.app.R
import com.example.app.data.Item
import com.example.app.databinding.FragmentHomeBinding
import com.example.app.ui.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding>(FragmentHomeBinding::inflate),
    ItemListListener {

    private val viewModel: HomeViewModel by viewModels()
    private val itemListAdapter = ItemListAdapter(this)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.rvItemList.adapter = itemListAdapter
        binding.fabAdd.setOnClickListener {
            findNavController().navigate(R.id.action_HomeFragment_to_EditItemActivity)
        }

        lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.items.collect {
                    itemListAdapter.submitList(it)
                }
            }
        }
    }

    override fun onItemPressed(item: Item) {
        findNavController().navigate(R.id.action_HomeFragment_to_DetailFragment)
    }
}